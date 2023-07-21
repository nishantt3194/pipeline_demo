resource "aws_service_discovery_private_dns_namespace" "api_namespace" {
  count       = length(var.foreground_service_definitions) > 0 ? 1 : 0
  name        = "subspace.api.${var.client_domain}"
  description = "Namespace for API Service"
  vpc         = var.vpc_id
}

resource "aws_service_discovery_service" "api_maps" {
  count       = length(aws_ecs_task_definition.foreground_task_definitions)
  name        = "${var.foreground_service_definitions[count.index].name}-service-subspace"
  description = "Subspace for Service ${title(var.foreground_service_definitions[count.index].name)}"


  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.api_namespace[0].id
    dns_records {
      ttl  = 60
      type = "SRV"
    }

    routing_policy = "MULTIVALUE"
  }

  #  health_check_config {
  #    type = "HTTP"
  #    resource_path = var.service_definitions[count.index].health_path
  #  }

  health_check_custom_config {
    failure_threshold = 4
  }
}

