#resource "aws_default_subnet" "default_subnet" {
#  availability_zone = var.default_subnet.availability_zone
#
#  tags = {
#    Name        = var.default_subnet.name
#    Client      = var.client
#    Environment = var.env
#  }
#}

resource "aws_default_subnet" "public_subnets" {
  count                   = length(var.public_subnets)
  availability_zone       = var.public_subnets[count.index].availability_zone
  depends_on              = [aws_default_vpc.main_vpc]
  map_public_ip_on_launch = true

  tags = {
    Name        = var.public_subnets[count.index].name
    Client      = var.client
    Environment = var.env
  }
}


resource "aws_subnet" "private_subnets" {
  count                   = length(var.private_subnets)
  vpc_id                  = aws_default_vpc.main_vpc.id
  cidr_block              = var.private_subnets[count.index].cidr_block
  availability_zone       = var.private_subnets[count.index].availability_zone
  map_public_ip_on_launch = false

  tags = {
    Name        = var.private_subnets[count.index].name
    Client      = var.client
    Environment = var.env
  }
}