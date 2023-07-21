output "load_balancer" {
  value = aws_lb.load_balancer
}

output "primary_listener" {
  value = var.enable_https ? aws_lb_listener.lb_listener[0] : aws_lb_listener.default_lb_listener[0]
}
