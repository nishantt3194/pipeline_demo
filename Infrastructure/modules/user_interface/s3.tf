# Policy Documents =======================
data "aws_iam_policy_document" "ui-access-documents" {
  count     = length(var.ui_definitions)
  policy_id = "${var.client_abr}-${var.ui_definitions[count.index].tag}-ui-access-policy"
  statement {
    sid     = "PublicReadGetObject"
    actions = [
      "s3:GetObject",
    ]
    effect    = "Allow"
    resources = [
      "arn:aws:s3:::${var.ui_definitions[count.index].bucket_name}/*",
    ]
    principals {
      identifiers = ["*"]
      type        = "*"
    }
  }
}

# ============================================
# Buckets

resource "aws_s3_bucket" "ui_buckets" {
  count = length(var.ui_definitions)
  bucket = "${var.ui_definitions[count.index].bucket_name}"
}

# ============================================
# UI Bucket Configuration

resource "aws_s3_bucket_website_configuration" "ui_config" {
  count = length(aws_s3_bucket.ui_buckets)
  bucket = aws_s3_bucket.ui_buckets[count.index].bucket

  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "index.html"
  }
}

# ============================================
# ACL Configuration

resource "aws_s3_bucket_policy" "admin_allow_public_access" {
  count = length(aws_s3_bucket.ui_buckets)
  bucket = aws_s3_bucket.ui_buckets[count.index].bucket
  policy = data.aws_iam_policy_document.ui-access-documents[count.index].json
}

resource "aws_s3_bucket_acl" "admin_public_access_acl" {
  count = length(var.ui_definitions)
  bucket = aws_s3_bucket.ui_buckets[count.index].bucket
  acl    = "public-read"
}

# ============================================
# CORS Configuration

resource "aws_s3_bucket_cors_configuration" "admin_cors_config" {
  count = length(var.ui_definitions)
  bucket = aws_s3_bucket.ui_buckets[count.index].bucket

  cors_rule {
    allowed_headers = ["Authorization", "Content-Length"]
    allowed_methods = ["GET", "POST"]
    allowed_origins = ["https://${var.ui_definitions[count.index].distribution_uri}"]
    max_age_seconds = 3000
  }
}
