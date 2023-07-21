resource "aws_lb_target_group" "target-groups" {
  count       = length(var.load_balanced_service_definitions)
  name        = "${var.load_balanced_service_definitions[count.index].name}-group"
  port        = var.load_balanced_service_definitions[count.index].port[0].containerPort
  protocol    = "HTTP"
#  target_type = "ip"
  vpc_id      = var.vpc_id

  lifecycle {
    ignore_changes = all
  }

  health_check {
    path = var.load_balanced_service_definitions[count.index].health_path
  }
}
