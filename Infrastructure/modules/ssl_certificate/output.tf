output "ui_certificate" {
  value = aws_acm_certificate.ui-certificate
}

output "api_certificate" {
  value = aws_acm_certificate.api-certificate
}