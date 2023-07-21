resource "aws_ecs_service" "foreground_services" {
  count   = length(aws_ecs_task_definition.foreground_task_definitions)
  name    = "${var.foreground_service_definitions[count.index].name}-service"
  cluster = aws_ecs_cluster.main-cluster.id

  task_definition = aws_ecs_task_definition.foreground_task_definitions[count.index].arn

  depends_on = [aws_ecs_capacity_provider.capacity_provider]
#  network_configuration {
#    subnets = var.subnets
#    security_groups = var.service_sg
#    assign_public_ip = false
#  }

  desired_count = 1

  lifecycle {
    ignore_changes = [desired_count, task_definition]
  }

  capacity_provider_strategy {
    base              = 0
    capacity_provider = "${var.client_abr}-cluster-provider"
    weight            = 1
  }

  deployment_controller {
    type = "ECS"
  }

  service_registries {
    registry_arn   = aws_service_discovery_service.api_maps[count.index].arn
    container_name = "${var.foreground_service_definitions[count.index].name}-container"
    container_port = var.foreground_service_definitions[count.index].port[0].hostPort
  }
}

resource "aws_ecs_service" "background_services" {
  count   = length(aws_ecs_task_definition.background_task_definitions)
  name    = "${var.background_service_definitions[count.index].name}-service"
  cluster = aws_ecs_cluster.main-cluster.id

  task_definition = aws_ecs_task_definition.background_task_definitions[count.index].arn

  depends_on = [aws_ecs_capacity_provider.capacity_provider]
  desired_count = 1

  lifecycle {
    ignore_changes = [desired_count, task_definition]
  }

  network_configuration {
    subnets = var.subnets
    security_groups = var.service_sg
    assign_public_ip = false
  }

  capacity_provider_strategy {
    base              = 0
    capacity_provider = "${var.client_abr}-cluster-provider"
    weight            = 1
  }

  deployment_controller {
    type = "ECS"
  }
}


resource "aws_ecs_service" "balanced_services" {
  count   = length(aws_ecs_task_definition.load_balanced_task_definitions)
  name    = "${var.load_balanced_service_definitions[count.index].name}-service"
  cluster = aws_ecs_cluster.main-cluster.id

  task_definition = aws_ecs_task_definition.load_balanced_task_definitions[count.index].arn

  depends_on = [aws_ecs_capacity_provider.capacity_provider]

  desired_count = 1

  lifecycle {
    ignore_changes = [desired_count, task_definition]
  }

  capacity_provider_strategy {
    base              = 0
    capacity_provider = "${var.client_abr}-cluster-provider"
    weight            = 1
  }

  deployment_controller {
    type = "ECS"
  }

  load_balancer {
    container_name   = "${var.load_balanced_service_definitions[count.index].name}-container"
    target_group_arn = aws_lb_target_group.target-groups[count.index].arn
    container_port   = var.load_balanced_service_definitions[count.index].port[0].containerPort
  }
}
