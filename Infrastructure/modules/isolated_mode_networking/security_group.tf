resource "aws_security_group" "public_sg" {
  name        = "${var.client_abr}_public_sg"
  description = "Public Security Group for ${upper(var.client_abr)} VPC. Allows HTTPS Access from outside VPC"
  vpc_id      = aws_vpc.main-vpc.id


  ingress = [
    {
      description      = "HTTPS IPv4"
      from_port        = 443
      to_port          = 443
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
      self             = false

    },
    {
      description      = "HTTP IPv4"
      from_port        = 80
      to_port          = 80
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
      self             = false

    }
  ]

  egress = [
    {
      from_port        = 0
      to_port          = 0
      protocol         = "-1"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
      self             = false
      description      = "Allow all outbound traffic"
    }
  ]

  tags = {
    Name        = "${upper(var.client_abr)} Public Security Group"
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_default_security_group" "default_sg" {
  vpc_id = aws_vpc.main-vpc.id

  ingress = [
    {
      from_port        = "0"
      to_port          = "0"
      protocol         = "-1"
      self             = true
      description      = "Allow All Inbound Traffic "
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
    }
  ]

  egress = [
    {
      from_port        = 0
      to_port          = 0
      protocol         = "-1"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
      self             = false
      description      = "Allow all outbound traffic"
    }
  ]

  tags = {
    Name        = "${upper(var.client_abr)} Default Security Group"
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_security_group" "cloud_map_sg" {
  vpc_id      = aws_vpc.main-vpc.id
  name        = "cloud_map_sg"
  description = "Primary Security Group For CloudMap Resources"

  ingress = [
    {
      from_port        = 0
      to_port          = 0
      protocol         = -1
      self             = true
      description      = "Allow All Inbound Traffic Within Group"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
    },
    {
      from_port        = 0
      to_port          = 65535
      protocol         = "tcp"
      self             = false
      description      = "Allow All Inbound Traffic"
      cidr_blocks      = [aws_vpc.main-vpc.cidr_block]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
    }
  ]

  egress = [
    {
      from_port        = 0
      to_port          = 0
      protocol         = -1
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
      self             = false
      description      = "Allow all outbound traffic"
    }
  ]

  tags = {
    Name        = "${upper(var.client_abr)} CloudMap Security Group"
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_db_subnet_group" "default-subnet-group" {
  name       = "${var.client_abr}-db-main-sg"
  subnet_ids = [for subnet in aws_subnet.main-subnets : subnet.id]

  tags = {
    Name = "${upper(var.client_abr)} DB subnet group"
  }
}
