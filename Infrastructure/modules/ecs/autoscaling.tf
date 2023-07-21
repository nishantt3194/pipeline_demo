resource "aws_launch_configuration" "ecs_launch_config" {
  name_prefix = "${var.client_abr}-lc-"
  image_id                    = "ami-0e5908f44ccb9cb78"
  iam_instance_profile        = aws_iam_instance_profile.ecs_agent.name
  security_groups             = var.service_sg
  associate_public_ip_address = true
  user_data                   = "#!/bin/bash\necho ECS_CLUSTER=${var.client_abr}-api-cluster >> /etc/ecs/ecs.config"
  instance_type               = "${var.base_instance}"
}

resource "aws_autoscaling_group" "ecs_asg" {
  name                      = "${var.client_abr}-api-asg"
  vpc_zone_identifier       = var.subnets
  launch_configuration      = aws_launch_configuration.ecs_launch_config.name
  protect_from_scale_in     = false
  desired_capacity          = 0
  min_size                  = 0
  max_size                  = length(var.foreground_service_definitions) + length(var.background_service_definitions) + length(var.load_balanced_service_definitions) + 1
  health_check_grace_period = 200
  health_check_type         = "EC2"

  depends_on = [aws_launch_configuration.ecs_launch_config]

  lifecycle {
    ignore_changes = [desired_capacity]
  }

  tag {
    key                 = "AmazonECSManaged"
    value               = true
    propagate_at_launch = true
  }
}
