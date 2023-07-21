package com.futurealgos.admin.services.products;

import com.futurealgos.admin.dto.payload.variant.NewVariant;
import com.futurealgos.admin.dto.response.variant.VariantMinimal;
import com.futurealgos.admin.dto.response.variant.VariantSearch;
import com.futurealgos.admin.models.primary.Product;
import com.futurealgos.admin.models.secondary.product.Variant;
import com.futurealgos.admin.repos.secondary.VariantRepository;
import com.futurealgos.data.service.ServiceTemplate;
import com.futurealgos.specs.utils.PayloadConverter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VariantService extends ServiceTemplate<Variant, UUID, Void, VariantMinimal, VariantSearch, NewVariant, Void> {

    VariantRepository variantRepository;
    ProductService productService;

    public VariantService(VariantRepository variantRepository, ProductService productService) {
        super(Variant.class, variantRepository, variantRepository);
        this.variantRepository = variantRepository;
        this.productService = productService;
        super.setConverter(new VariantService.VariantConverter(productService));
    }

    public static class VariantConverter implements PayloadConverter<NewVariant, Variant> {
        ProductService productService;

        public VariantConverter(ProductService productService) {
            this.productService = productService;
        }

        @Override
        public Variant convert(NewVariant newVariant) {

            Product product = productService.fetch(newVariant.product());
            return Variant.builder()
                    .name(newVariant.name())
                    .description(newVariant.description())
                    .product(product)
                    .build();
        }

    }

}
