terraform {
  required_version = ">= 1.3.6"
}

resource "aws_default_vpc" "main_vpc" {
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name        = "${upper(var.client_abr)} VPC"
    Client      = var.client
    Environment = var.env
  }
}


data "aws_internet_gateway" "gw" {
  filter {
    name   = "attachment.vpc-id"
    values = [aws_default_vpc.main_vpc.id]
  }
}

resource "aws_eip" "eip" {
  vpc = true
}

#resource "aws_nat_gateway" "nat" {
#  subnet_id     = element(aws_default_subnet.public_subnets.*.id, 0)
#  allocation_id = aws_eip.eip.id
#
#  tags = {
#    Name        = "${upper(var.client_abr)} NAT Gateway"
#    Client      = var.client
#    Environment = var.env
#  }
#}


resource "aws_db_subnet_group" "default-subnet-group" {
  name       = "${var.client_abr}_db_main_sg"
  subnet_ids = [for subnet in aws_default_subnet.public_subnets : subnet.id]

  tags = {
    Name = "${upper(var.client_abr)} DB subnet group"
  }
}
