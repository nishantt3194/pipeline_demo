package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.payload.products.NewProduct;
import com.futurealgos.admin.dto.payload.products.UpdateProduct;
import com.futurealgos.admin.dto.response.product.ProductInfo;
import com.futurealgos.admin.dto.response.product.ProductMinimal;
import com.futurealgos.admin.dto.response.product.ProductSearch;
import com.futurealgos.admin.dto.response.variant.VariantSearch;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.repos.secondary.ProductionRepository;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.products.ProductService;
import com.futurealgos.admin.services.products.VariantService;
import com.futurealgos.specs.utils.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product/")
public class ProductResource {


    @Autowired
    ProductService productService;

    VariantService variantService;
    @Autowired
    AreaService areaService;
    @Autowired
    private ProductionRepository productionRepository;

    @PostMapping("create")
    public String create(@RequestBody NewProduct payload) {
        productService.create(payload, "");
        return "Product Created Successfully";
    }

    @GetMapping("list")
    public Page<ProductMinimal> list(@RequestParam(required = false, defaultValue = "-1") Integer offset,
                                     @RequestParam(required = false, defaultValue = "-1") Integer size) {
        return productService.list(offset, size);
    }

    @GetMapping("info")
    public ProductInfo info(@RequestParam String id) {
        return productService.info(id);
    }

    @GetMapping("search")
    public List<ProductSearch> search(@RequestParam(name = "area", required = false) String areaId) {
        List<SearchFilter<?>> filters = new ArrayList<>();
        if (areaId != null) {
            Area area = areaService.fetch(UUID.fromString(areaId));
            filters.add(SearchFilter.is("area", area));
        }
        return productService.search(filters.toArray(SearchFilter[]::new));
    }

    public ProductInfo edit(@RequestParam String id, @RequestBody UpdateProduct updateProduct) {
        return productService.update(id, updateProduct, "");
    }

    @GetMapping("variant/search")
    public List<VariantSearch> searchVariant(@RequestParam String productId) {

        List<SearchFilter<?>> filters = new ArrayList<>();
        filters.add(SearchFilter.is("product", UUID.fromString(productId)));
        return variantService.search(filters.toArray(SearchFilter[]::new));
    }


}
