# Route Tables ---------------------------------------------------------------------------------------------------------
resource "aws_default_route_table" "rt" {
  default_route_table_id = aws_default_vpc.main_vpc.default_route_table_id

  tags = {
    Name        = "${upper(var.client_abr)} VPC Main Route Table"
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_route_table" "private_rt" {
  vpc_id = aws_default_vpc.main_vpc.id

  depends_on = []

  tags = {
    Name        = "${upper(var.client_abr)} VPC Private Route Table"
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_main_route_table_association" "main-rt-association" {
  route_table_id = aws_default_route_table.rt.id
  vpc_id         = aws_default_vpc.main_vpc.id
}

# Routes ---------------------------------------------------------------------------------------------------------------

resource "aws_route" "public_route" {
  route_table_id         = aws_default_route_table.rt.id
  gateway_id             = data.aws_internet_gateway.gw.id
  destination_cidr_block = "0.0.0.0/0"
}

#resource "aws_route" "atlas_peering" {
#  route_table_id            = aws_default_route_table.rt.id
#  vpc_peering_connection_id = "pcx-0d6f6c7c0b71d53c6"
#  destination_cidr_block    = "192.168.248.0/21"
#}

#resource "aws_route" "private_route" {
#  route_table_id = aws_route_table.private_rt.id
#  nat_gateway_id = aws_nat_gateway.nat.id
#  destination_cidr_block = "0.0.0.0/0"
#}

# Associations ---------------------------------------------------------------------------------------------------------


resource "aws_route_table_association" "public_rta" {
  count          = length(aws_default_subnet.public_subnets)
  subnet_id      = aws_default_subnet.public_subnets[count.index].id
  route_table_id = aws_default_route_table.rt.id
}

resource "aws_route_table_association" "private_rta" {
  count          = length(aws_subnet.private_subnets)
  subnet_id      = aws_subnet.private_subnets[count.index].id
  route_table_id = aws_route_table.private_rt.id
}
