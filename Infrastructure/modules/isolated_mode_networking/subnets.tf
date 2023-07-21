resource "aws_subnet" "main-subnets" {
  count             = length(var.subnets)
  cidr_block        = var.subnets[count.index].cidr_block
  vpc_id            = aws_vpc.main-vpc.id
  availability_zone = var.subnets[count.index].availability_zone
  map_public_ip_on_launch = true

  tags = {
    Name        = var.subnets[count.index].name
    Client      = var.client
    Environment = var.env
  }
}
