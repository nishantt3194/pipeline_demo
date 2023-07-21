variable "env" {
  type        = string
  description = "Environment"
}

variable "client" {
  type        = string
  description = "Client Name"
}

variable "client_abr" {
  type        = string
  description = "Client Abbreviation"
}


variable "vpc_cidr_block" {
  type = string
  description = "VPC IPv4 CIDR Block"
}


variable "subnets" {
  type = list(object({
    name              = string
    cidr_block        = string
    availability_zone = string
  }))
  description = "Subnets"
}
