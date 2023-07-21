terraform {
  required_version = ">= 1.3.6"
}

resource "aws_vpc" "main-vpc" {
  cidr_block           = var.vpc_cidr_block
  instance_tenancy     = "default"
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name        = "${upper(var.client_abr)} VPC"
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.main-vpc.id

  tags = {
    Name        = "${upper(var.client_abr)} Gateway"
    Client      = var.client
    Environment = var.env
  }
}
