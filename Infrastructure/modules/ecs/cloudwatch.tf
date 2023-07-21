resource "aws_cloudwatch_log_group" "background_service_log_groups" {
  count = length(var.background_service_definitions)
  name  = "ecs/${var.client_abr}/${var.background_service_definitions[count.index].name}"
  retention_in_days = 3

  tags = {
    Environment = var.env
    Service     = "${var.background_service_definitions[count.index].name}-service"
    Client      = var.client
  }
}

resource "aws_cloudwatch_log_group" "foreground_service_log_groups" {
  count = length(var.foreground_service_definitions)
  name  = "ecs/${var.client_abr}/${var.foreground_service_definitions[count.index].name}"
  retention_in_days = 3

  tags = {
    Environment = var.env
    Service     = "${var.foreground_service_definitions[count.index].name}-service"
    Client      = var.client
  }
}


resource "aws_cloudwatch_log_group" "load_balanced_service_log_groups" {
  count = length(var.load_balanced_service_definitions)
  name  = "ecs/${var.client_abr}/${var.load_balanced_service_definitions[count.index].name}"
  retention_in_days = 3

  tags = {
    Environment = var.env
    Service     = "${var.load_balanced_service_definitions[count.index].name}-service"
    Client      = var.client
  }
}
