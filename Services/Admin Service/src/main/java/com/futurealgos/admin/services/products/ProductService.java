package com.futurealgos.admin.services.products;

import com.futurealgos.admin.dto.payload.products.NewProduct;
import com.futurealgos.admin.dto.payload.products.UpdateProduct;
import com.futurealgos.admin.dto.response.product.ProductInfo;
import com.futurealgos.admin.dto.response.product.ProductMinimal;
import com.futurealgos.admin.dto.response.product.ProductSearch;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.Product;
import com.futurealgos.admin.repos.primary.ProductRepository;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.data.service.ServiceTemplate;
import com.futurealgos.specs.utils.PayloadConverter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService extends ServiceTemplate<Product, UUID, ProductInfo, ProductMinimal, ProductSearch, NewProduct, UpdateProduct> {

    ProductRepository productRepository;

    AreaService areaService;

    public ProductService(ProductRepository productRepository, AreaService areaService) {
        super(Product.class, productRepository, productRepository);
        this.productRepository = productRepository;
        this.areaService = areaService;
        this.setConverter(new ProductConverter(areaService));
        this.setPopulator(UpdateProduct::populate);
    }


    public static class ProductConverter implements PayloadConverter<NewProduct, Product> {


        AreaService areaService;

        public ProductConverter(AreaService areaService) {
            this.areaService = areaService;
        }

        @Override
        public Product convert(NewProduct newProduct) {
            Area area = areaService.fetch(newProduct.area());
            return Product.builder()
                    .area(area)
                    .type(Product.Type.valueOf(newProduct.type()))
                    .description(newProduct.description())
                    .weight(newProduct.weight())
                    .catalogNum(newProduct.number())
                    .build();
        }

    }
}
