resource "aws_lb_listener_rule" "router-rule" {
  count = length(var.load_balanced_service_definitions)
  listener_arn = "${var.lb_listener}"
  priority     = 100 - count.index

  lifecycle {
    ignore_changes = all
  }
  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.target-groups[count.index].arn
  }

  condition {
    path_pattern {
      values = var.load_balanced_service_definitions[count.index].condition.path_pattern
    }
  }
}


