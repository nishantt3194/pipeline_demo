# Primary Bucket ================================================================
#resource "aws_s3_bucket" "bucket" {
#  bucket = "fa-${var.client_abr}-bucket"
#
#  tags = {
#    Name        = "${upper(var.client_abr)} Bucket"
#    Client      = var.client
#    Environment = var.env
#  }
#}
#
#resource "aws_s3_bucket_acl" "private_acl" {
#  bucket = aws_s3_bucket.bucket.bucket
#  acl    = "private"
#}

# Networking =====================================================================
module "networking" {
  source         = "./modules/default_mode_networking"

  client          = var.client
  client_abr      = var.client_abr
  client_domain   = var.client_domain
  env             = var.env
  private_subnets = []
  public_subnets  = [
    {
      name = "BD Public Subnet 1",
      availability_zone = "ap-south-1a"
    },
    {
      name = "BD Public Subnet 2",
      availability_zone = "ap-south-1b"
    },
    {
      name = "BD Public Subnet 3",
      availability_zone = "ap-south-1c"
    }
  ]
}

# User Interface ================================================================

module "user_interface" {
  source = "./modules/user_interface"


  client         = var.client
  client_abr     = var.client_abr
  client_domain  = var.client_domain
  env            = var.env
  ui_definitions = [
    {
      name = "BD OEE Panel"
      tag = "bd"
      comment = "Primary Distribution for BD OEE Panel"
      bucket_name = "bd-app-bucket"
      distribution_uri = var.client_domain
    },
    {
      name = "BD QA OEE Panel"
      tag = "bd-qa"
      comment = "Primary Distribution for BD QA OEE Panel"
      bucket_name = "bd-app-qa-bucket"
      distribution_uri = "qa.${ var.client_domain }"
    }
  ]

}

# Load Balancer =================================================================

#module "load_balancer" {
#  source = "./modules/load_balancer"
#
#  client          = var.client
#  client_abr      = var.client_abr
#  env             = var.env
#  security_groups = [module.networking.public_security_group.id]
#  subnets         = [for subnet in module.networking.subnets : subnet.id]
#  vpc_id          = module.networking.vpc.id
#}

# Cluster Setup =================================================================

module "api-cluster" {
  source = "./modules/ecs"

  base_instance = "t3.micro"
  client        = "${var.client}"
  client_abr    = "${var.client_abr}"
  client_domain = var.client_domain
  env           = "${var.env}"
  lb_listener   = ""
  load_balancer = ""
  service_sg    = [module.networking.public_security_group.id]
  subnets       = [for subnet in module.networking.public_subnets : subnet.id]
  vpc_id        = module.networking.vpc.id

  background_service_definitions    = []
  foreground_service_definitions    = [
    {
        name         = "admin"
        image_uri    = "418388679229.dkr.ecr.ap-south-1.amazonaws.com/administration-repository"
        cpu          = 2048
        memory       = 950
        health_path  = "/"
        path_pattern = "/"
        condition    = {
            path_pattern = ["/*"]
        }
        port = [
            {
            containerPort = 8001
            hostPort      = 8001
            protocol      = "tcp"
            }
        ]

        env = []
    },
    {
      name         = "admin-qa"
      image_uri    = "418388679229.dkr.ecr.ap-south-1.amazonaws.com/administration-qa-repository"
      cpu          = 2048
      memory       = 950
      health_path  = "/"
      path_pattern = "/"
      condition    = {
        path_pattern = ["/*"]
      }
      port = [
        {
          containerPort = 8002
          hostPort      = 8002
          protocol      = "tcp"
        }
      ]

      env = []
    }
  ]
  load_balanced_service_definitions = []
}
