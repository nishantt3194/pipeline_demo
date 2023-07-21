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


variable "ui_definitions" {
  description = "Definitions for User Interface to be built"
  type        = list(
    object({
      name             = string
      tag              = string
      comment          = string
      bucket_name      = string
      distribution_uri = string
    })
  )
}
