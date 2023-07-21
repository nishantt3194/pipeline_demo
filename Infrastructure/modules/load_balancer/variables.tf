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

variable "vpc_id" {
  type        = string
  description = "VPC ID"
}

variable "subnets" {
  type        = list(string)
  description = "Subnet IDs for Services"
}

variable "security_groups" {
  type        = list(string)
  description = "Security Group IDs for Services"
}

variable "primary_zone_id" {
  type = string
  default = ""
  description = "Zone ID to bind Load Balancer with"
}

variable "domain" {
  type = string
  default = ""
  description = "Domain to bind Load Balancer with"
}

variable "enable_https" {
  type = bool
  default = false
  description = "Enable HTTPS for Load Balancer"
}


variable "bind_domain" {
  type = bool
  default = false
  description = "Bind Load Balancer to a Domain"
}

variable "certificate_arn" {
  type = string
  default = ""
  description = "SSL Certificate ARN"
}
