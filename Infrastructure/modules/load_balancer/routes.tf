resource "aws_route53_record" "api-record" {
  count = var.bind_domain ? 1 : 0
  zone_id    = var.primary_zone_id
  name       = var.domain
  type       = "A"
  depends_on = [aws_lb.load_balancer]

  alias {
    evaluate_target_health = true
    name                   = aws_lb.load_balancer.dns_name
    zone_id                = aws_lb.load_balancer.zone_id
  }
}
