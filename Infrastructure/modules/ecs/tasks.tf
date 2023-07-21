resource "aws_ecs_task_definition" "foreground_task_definitions" {
  count                    = length(var.foreground_service_definitions)
  family                   = "${var.foreground_service_definitions[count.index].name}-def"
  network_mode             = "bridge"
  cpu                      = var.foreground_service_definitions[count.index].cpu
  memory                   = var.foreground_service_definitions[count.index].memory
  task_role_arn            = data.aws_iam_role.task_execution_role.arn
  execution_role_arn       = data.aws_iam_role.task_execution_role.arn
  requires_compatibilities = ["EC2"]

#  lifecycle {
#    ignore_changes = all
#  }

  container_definitions = jsonencode([
    {
      name            = "${var.foreground_service_definitions[count.index].name}-container"
      image           = var.foreground_service_definitions[count.index].image_uri
      essential       = true
      portMappings    = var.foreground_service_definitions[count.index].port
      logConfiguration = {
        logDriver = "awslogs"
        options   = {
          awslogs-group         = aws_cloudwatch_log_group.foreground_service_log_groups[count.index].name
          awslogs-region        = "ap-south-1"
          awslogs-stream-prefix = "ecs"
        }
      }
      environmentFiles = var.foreground_service_definitions[count.index].env
    },
  ])
}

resource "aws_ecs_task_definition" "background_task_definitions" {
  count                    = length(var.background_service_definitions)
  family                   = "${var.background_service_definitions[count.index].name}-def"
  network_mode             = "awsvpc"
  cpu                      = var.background_service_definitions[count.index].cpu
  memory                   = var.background_service_definitions[count.index].memory
  task_role_arn            = data.aws_iam_role.task_execution_role.arn
  execution_role_arn       = data.aws_iam_role.task_execution_role.arn
  requires_compatibilities = ["EC2"]

  lifecycle {
    ignore_changes = all
  }


  container_definitions = jsonencode([
    {
      name            = "${var.background_service_definitions[count.index].name}-container"
      image           = var.background_service_definitions[count.index].image_uri
      essential       = true
      portMappings    = var.background_service_definitions[count.index].port
      logConfiguration = {
        logDriver = "awslogs"
        options   = {
          awslogs-group         = aws_cloudwatch_log_group.background_service_log_groups[count.index].name
          awslogs-region        = "ap-south-1"
          awslogs-stream-prefix = "ecs"
        }
      }
      environmentFiles = var.background_service_definitions[count.index].env
    },
  ])
}

resource "aws_ecs_task_definition" "load_balanced_task_definitions" {
  count                    = length(var.load_balanced_service_definitions)
  family                   = "${var.load_balanced_service_definitions[count.index].name}-def"
  network_mode             = "bridge"
  cpu                      = var.load_balanced_service_definitions[count.index].cpu
  memory                   = var.load_balanced_service_definitions[count.index].memory
  task_role_arn            = data.aws_iam_role.task_execution_role.arn
  execution_role_arn       = data.aws_iam_role.task_execution_role.arn
  requires_compatibilities = ["EC2"]

  lifecycle {
    ignore_changes = all
  }

  container_definitions = jsonencode([
    {
      name            = "${var.load_balanced_service_definitions[count.index].name}-container"
      image           = var.load_balanced_service_definitions[count.index].image_uri
      essential       = true
      portMappings    = var.load_balanced_service_definitions[count.index].port
      logConfiguration = {
        logDriver = "awslogs"
        options   = {
          awslogs-group         = aws_cloudwatch_log_group.load_balanced_service_log_groups[count.index].name
          awslogs-region        = "ap-south-1"
          awslogs-stream-prefix = "ecs"
        }
      }
      environmentFiles = var.load_balanced_service_definitions[count.index].env
    },
  ])
}
