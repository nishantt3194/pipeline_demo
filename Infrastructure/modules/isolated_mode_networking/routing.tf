# Route Tables ---------------------------------------------------------------------------------------------------------
resource "aws_route_table" "rt" {
  vpc_id     = aws_vpc.main-vpc.id
  depends_on = [aws_internet_gateway.gw]

  tags = {
    Name        = "${upper(var.client_abr)} VPC Main Route Table"
    Client      = var.client
    Environment = var.env
  }
}

resource "aws_main_route_table_association" "main-rt-association" {
  route_table_id = aws_route_table.rt.id
  vpc_id         = aws_vpc.main-vpc.id
}

# Routes ---------------------------------------------------------------------------------------------------------------
resource "aws_route" "public_route" {
  route_table_id         = aws_route_table.rt.id
  gateway_id             = aws_internet_gateway.gw.id
  destination_cidr_block = "0.0.0.0/0"
}

# Associations ---------------------------------------------------------------------------------------------------------


resource "aws_route_table_association" "main_rta" {
  count          = length(aws_subnet.main-subnets)
  subnet_id      = aws_subnet.main-subnets[count.index].id
  route_table_id = aws_route_table.rt.id
}
