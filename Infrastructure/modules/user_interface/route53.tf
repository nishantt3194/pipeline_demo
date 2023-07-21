data "aws_route53_zone" "primary-hz" {
  name         = var.client_domain
  private_zone = false
}


# ============================================
# UI Records
resource "aws_route53_record" "ui_record" {
  count   = length(aws_cloudfront_distribution.app_s3_distributions)
  zone_id = data.aws_route53_zone.primary-hz.zone_id
  name    = "${var.ui_definitions[count.index].distribution_uri}"
  type    = "A"

  alias {
    name                   = aws_cloudfront_distribution.app_s3_distributions[count.index].domain_name
    zone_id                = aws_cloudfront_distribution.app_s3_distributions[count.index].hosted_zone_id
    evaluate_target_health = false
  }
}


# ============================================
# Certificate Records
resource "aws_route53_record" "certificate-records" {
  provider = aws.us-east
  for_each = {
  for dvo in aws_acm_certificate.certificate.domain_validation_options : dvo.domain_name => {
    name   = dvo.resource_record_name
    record = dvo.resource_record_value
    type   = dvo.resource_record_type
  }
  }

  depends_on = [aws_acm_certificate.certificate]

  allow_overwrite = true
  name            = each.value.name
  records         = [each.value.record]
  ttl             = 60
  type            = each.value.type
  zone_id         = data.aws_route53_zone.primary-hz.zone_id
}
