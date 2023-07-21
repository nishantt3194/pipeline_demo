terraform {
  backend "remote" {
    organization = "future-algorithms"
    workspaces {
      name = "bd_oee_panel"
    }
  }

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.55.0"
    }
  }
}

provider "aws" {
  region = "ap-south-1"
}
