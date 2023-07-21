resource "aws_ecs_cluster" "main-cluster" {
  name = "${var.client_abr}-api-cluster"

  setting {
    name  = "containerInsights"
    value = "disabled"
  }

  tags = {
    Name        = "${upper(var.client_abr)} API Cluster"
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_ecs_cluster_capacity_providers" "provider_group" {
  cluster_name       = aws_ecs_cluster.main-cluster.name
  capacity_providers = [aws_ecs_capacity_provider.capacity_provider.name]
  depends_on = [aws_ecs_capacity_provider.capacity_provider, aws_ecs_cluster.main-cluster]

  default_capacity_provider_strategy {
    capacity_provider = aws_ecs_capacity_provider.capacity_provider.name
  }
}

resource "aws_ecs_capacity_provider" "capacity_provider" {
  name = "${var.client_abr}-cluster-provider"
  auto_scaling_group_provider {
    auto_scaling_group_arn         = aws_autoscaling_group.ecs_asg.arn
    managed_termination_protection = "DISABLED"

    managed_scaling {
      maximum_scaling_step_size = 1000
      minimum_scaling_step_size = 1
      status                    = "ENABLED"
      target_capacity           = 1
    }
  }
}
