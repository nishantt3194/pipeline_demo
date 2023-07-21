output "vpc" {
  value = aws_vpc.main-vpc
}

output "subnets" {
  value = aws_subnet.main-subnets
}

output "default_security_group" {
  value = aws_default_security_group.default_sg
}

output "public_security_group" {
  value = aws_security_group.public_sg
}

output "cloud_map_security_group" {
  value = aws_security_group.cloud_map_sg
}

output "db-subnet-group" {
  value = aws_db_subnet_group.default-subnet-group
}

output "i-gateway" {
  value = aws_internet_gateway.gw
}
