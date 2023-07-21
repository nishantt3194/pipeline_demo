output "vpc" {
  value = aws_default_vpc.main_vpc
}

output "public_subnets" {
  value = aws_default_subnet.public_subnets
}

output "private_subnets" {
  value = aws_subnet.private_subnets
}

output "default_security_group" {
  value = aws_default_security_group.default_sg
}

output "public_security_group" {
  value = aws_security_group.public_sg
}

output "cloudmap_security_group" {
  value = aws_security_group.cloudmap_sg
}

output "db-subnet-group" {
  value = aws_db_subnet_group.default-subnet-group
}

output "i-gateway" {
  value = data.aws_internet_gateway.gw
}