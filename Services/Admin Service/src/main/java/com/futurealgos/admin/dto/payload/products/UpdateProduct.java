package com.futurealgos.admin.dto.payload.products;

import com.futurealgos.admin.models.primary.Product;

public record UpdateProduct(String id) {
    public Product populate(Product product) {
        return product;
    }
}
