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

variable "vpc_id" {
  type        = string
  description = "VPC ID"
}

variable "subnets" {
  type        = list(string)
  description = "Subnet IDs for Services"
}

variable "service_sg" {
  type        = list(string)
  description = "Security Group IDs for Services"
}

variable "load_balancer" {
  type        = string
  description = "ARN of Load Balancer"
}

variable "lb_listener" {
  type        = string
  description = "ARN of LB Listener"
}

variable "base_instance" {
  description = "Base Instance Class"
    type        = string
}

variable "load_balanced_service_definitions" {
  description = "List of Load Balanced Service Definitions"
  type        = list(
    object({
      name         = string
      image_uri    = string
      cpu          = number
      memory       = number
      health_path  = string
      path_pattern = string

      port = list(
        object({
          containerPort = number
          protocol      = string
          hostPort      = number
        }))

      condition = object({
        path_pattern = optional(list(string))
        host_header = optional(list(string))
        http_header = optional(object({
          values = list(string)
          http_header_name = string
        }))
        http_request_method = optional(list(string))
        query_string = optional(string)
      })

      env = list(
        object({
          type  = string
          value = string
        }))
    }))
}

variable "foreground_service_definitions" {
  description = "List of Foreground Service Definitions"
  type        = list(
    object({
      name         = string
      image_uri    = string
      cpu          = number
      memory       = number
      health_path  = string
      path_pattern = string

      port = list(
        object({
          containerPort = number
          protocol      = string
          hostPort      = number
        }))

      env = list(
        object({
          type  = string
          value = string
        }))
    }))
}

variable "background_service_definitions" {
  description = "List of Background Service Definitions"
  type        = list(
    object({
      name         = string
      image_uri    = string
      cpu          = number
      memory       = number
      health_path  = string
      path_pattern = string

      port = list(
        object({
          containerPort = number
          protocol      = string
          hostPort      = number
        }))

      env = list(
        object({
          type  = string
          value = string
        }))
    }))
}


