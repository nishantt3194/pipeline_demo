terraform {
  required_version = ">= 1.3.6"
}

provider "aws" {
  alias  = "us-east"
  region = "us-east-1"
}

data "aws_route53_zone" "primary-hz" {
  name         = var.client_domain
  private_zone = false
}

resource "aws_acm_certificate" "ui-certificate" {
  domain_name               = var.client_domain
  subject_alternative_names = [
    "www.${var.client_domain}"
  ]
  validation_method = "DNS"

  provider = aws.us-east

  tags = {
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_acm_certificate" "api-certificate" {
  domain_name       = "api.${var.client_domain}"
  validation_method = "DNS"

  tags = {
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_route53_record" "ui-certificate-records" {
  provider = aws.us-east
  for_each = {
  for dvo in aws_acm_certificate.ui-certificate.domain_validation_options : dvo.domain_name => {
    name   = dvo.resource_record_name
    record = dvo.resource_record_value
    type   = dvo.resource_record_type
  }
  }

  allow_overwrite = true
  name            = each.value.name
  records         = [each.value.record]
  ttl             = 60
  type            = each.value.type
  zone_id         = data.aws_route53_zone.primary-hz.zone_id
}

resource "aws_route53_record" "api-certificate-records" {
  for_each = {
  for dvo in aws_acm_certificate.api-certificate.domain_validation_options : dvo.domain_name => {
    name   = dvo.resource_record_name
    record = dvo.resource_record_value
    type   = dvo.resource_record_type
  }
  }

  allow_overwrite = true
  name            = each.value.name
  records         = [each.value.record]
  ttl             = 60
  type            = each.value.type
  zone_id         = data.aws_route53_zone.primary-hz.zone_id
}

resource "aws_acm_certificate_validation" "ui-certificate-validation" {
  provider                = aws.us-east
  certificate_arn         = aws_acm_certificate.ui-certificate.arn
  validation_record_fqdns = [for record in aws_route53_record.ui-certificate-records : record.fqdn]
}

resource "aws_acm_certificate_validation" "api-certificate-validation" {
  certificate_arn         = aws_acm_certificate.api-certificate.arn
  validation_record_fqdns = [for record in aws_route53_record.api-certificate-records : record.fqdn]
}
