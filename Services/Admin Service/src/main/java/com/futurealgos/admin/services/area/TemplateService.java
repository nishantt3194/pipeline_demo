package com.futurealgos.admin.services.area;

import com.futurealgos.admin.dto.response.shifts.TemplateMinimal;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.Product;
import com.futurealgos.admin.models.secondary.entry.Formula;
import com.futurealgos.admin.models.secondary.entry.Template;
import com.futurealgos.admin.repos.secondary.TemplateRepository;
import com.futurealgos.admin.services.products.ProductService;
import com.futurealgos.data.service.ModifierTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TemplateService extends ModifierTemplate<Template, UUID, TemplateMinimal, Void, Void> {

    @Autowired
    ProductService productService;

    @Autowired
    AreaService areaService;

    TemplateRepository templateRepository;

    public TemplateService(TemplateRepository repository) {
        super(repository);
        this.templateRepository = repository;
    }

    public List<TemplateMinimal> list(String productId, String areaId, List<Template.Type> types) {
        Area area = areaService.fetch(areaId);
        Product product = productService.fetch(productId);
        List<Template> templates = new ArrayList<>();
        types.forEach(
                type -> {
                    templates.addAll(templateRepository.findByAreaAndType(area, type));
                }
        );

        return templates.stream()
                .map(template -> {
                    TemplateMinimal.TemplateMinimalBuilder builder = TemplateMinimal.builder()
                            .id(template.getId().toString())
                            .sign(template.getSign())
                            .description(template.getDescription())
                            .toConvert(template.formula != null)
                            .templateType(template.getType());

                    if(template.formula != null) {
                        builder = builder
                                .operation(template.getFormula().getOperation())
                                .inKg(template.getFormula().isInKg())
                                .operand(template.formula.getType().equals(Formula.Type.CONSTANT) ? template.getFormula().getOperand() : product.getWeight())
                                .type(template.getFormula().getType());
                    }

                    return builder.build();

                }).collect(Collectors.toList());
    }

    public List<Template> findTemplateByArea(Area area){
        return  templateRepository.findByArea(area);
    }
}
