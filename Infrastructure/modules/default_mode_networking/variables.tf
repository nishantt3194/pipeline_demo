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

variable "client_domain" {
  type        = string
  description = "Client Domain"
}

#variable "certificate_arn" {
#  type        = string
#  description = "ARN of SSL Certificate"
#}

#variable "bucket_arn" {
#  type        = string
#  description = "ARN of Bucket for Project"
#}
#
#variable "bucket_id" {
#  type        = string
#  description = "ID of Bucket for Project"
#}

#variable "bucket_name" {
#  type        = string
#  description = "Name of Bucket for Project"
#}

variable "public_subnets" {
  type = list(object({
    name              = string
    availability_zone = string
  }))
  description = "Subnets"
}

variable "private_subnets" {
  type = list(object({
    name              = string
    cidr_block        = string
    availability_zone = string
  }))
  description = "Subnets"
}

