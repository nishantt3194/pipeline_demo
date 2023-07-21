terraform {
  required_version = ">= 1.3.6"
}

resource "aws_lb" "load_balancer" {
  name                       = "${var.client_abr}-api-balancer"
  internal                   = false
  load_balancer_type         = "application"
  security_groups            = var.security_groups
  subnets                    = var.subnets
  enable_deletion_protection = false
}

resource "aws_lb_listener" "default_lb_listener" {
  count = var.enable_https ? 0: 1
  load_balancer_arn = "${aws_lb.load_balancer.arn}"
  port  = "80"
  protocol = "HTTP"

  depends_on = [aws_lb.load_balancer]

  default_action {
    type             = "fixed-response"
    fixed_response {
      content_type = "text/plain"
      message_body = "Resource disabled at this port"
      status_code  = "200"
    }
  }
}

resource "aws_lb_listener" "lb_listener" {
  count = var.enable_https ? 1: 0
  load_balancer_arn = "${aws_lb.load_balancer.arn}"
  port              = "443"
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = var.certificate_arn

  depends_on = [aws_lb.load_balancer]

  default_action {
    type             = "fixed-response"
    fixed_response {
      content_type = "text/plain"
      message_body = "URL not found"
      status_code  = "404"
    }
  }
}
