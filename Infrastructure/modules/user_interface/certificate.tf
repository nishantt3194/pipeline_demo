provider "aws" {
  alias  = "us-east"
  region = "us-east-1"
}

resource "aws_acm_certificate" "certificate" {
  domain_name               = var.client_domain
  subject_alternative_names = [for def in var.ui_definitions : "${def.distribution_uri}"]


  validation_method = "DNS"

  provider = aws.us-east

  tags = {
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_acm_certificate_validation" "ui-certificate-validation" {
  provider                = aws.us-east
  certificate_arn         = aws_acm_certificate.certificate.arn
  validation_record_fqdns = [for record in aws_route53_record.certificate-records : record.fqdn]
}
