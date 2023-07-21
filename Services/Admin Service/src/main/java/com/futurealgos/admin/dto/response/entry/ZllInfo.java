package com.futurealgos.admin.dto.response.entry;

import com.futurealgos.admin.models.primary.Category;
import com.futurealgos.admin.models.primary.Entry;
import com.futurealgos.admin.models.secondary.product.Variant;
import com.futurealgos.admin.models.views.ZllReport;
import com.futurealgos.specs.objects.DefaultSearchResponse;
import liquibase.command.core.DeactivateChangelogCommandStep;
import lombok.Builder;

import java.io.Serializable;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Builder
public record ZllInfo(
        List<Map<String, Object>> data,
        List<DefaultSearchResponse> headers
) implements Serializable {

    public ZllInfo() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public static ZllInfo from(List<ZllReport> reports, List<Category> categories) {
        var headers = new ArrayList<DefaultSearchResponse>();
        headers.add(DefaultSearchResponse.builder().label("Machine").identifier("machine").build());
        headers.add(DefaultSearchResponse.builder().label("Shift").identifier("shift").build());
        headers.add(DefaultSearchResponse.builder().label("Area").identifier("area").build());
        headers.add(DefaultSearchResponse.builder().label("Shift Date").identifier("shift_date").build());
        headers.add(DefaultSearchResponse.builder().label("Total Rejection").identifier("totalRejection").build());
        headers.add(DefaultSearchResponse.builder().label("Total Production").identifier("totalProduction").build());
        headers.add(DefaultSearchResponse.builder().label("rejectionPercentage").identifier("rejectionPercentage").build());
        headers.add(DefaultSearchResponse.builder().label("expected_machine_speed").identifier("expected_machine_speed").build());
        headers.add(DefaultSearchResponse.builder().label("actualSpeed").identifier("actualSpeed").build());
        headers.add(DefaultSearchResponse.builder().label("valueOperatingTime").identifier("valueOperatingTime").build());
        headers.add(DefaultSearchResponse.builder().label("remark").identifier("remark").build());
        headers.add(DefaultSearchResponse.builder().label("goodProduction").identifier("goodProduction").build());
        headers.add(DefaultSearchResponse.builder().label("gauge").identifier("gauge").build());
        headers.addAll(getHeaders(categories));

        return ZllInfo.builder()
                .data(getData(reports))
                .headers(headers)
                .build();
    }

    public static List<Map<String, Object>> getData(List<ZllReport> reports) {
        List<Map<String, Object>> data = new ArrayList<>();
        var groupedData = reports.stream().filter(zllReport->zllReport.getEntry()!=null).collect(groupingBy(ZllReport::getEntry));
        groupedData.forEach((key, value) -> {
            Map<String, Object> map = new HashMap<>();
            ZllReport report = value.get(0);
            map.put("entry", report.getEntry().getId().toString());
            map.put("machine", report.getMachine().getName());
            map.put("shift", report.getShift().getName());
            map.put("area", report.getArea().getName());
            map.put("shift_date",report.getDate());
            map.put("totalProduction", report.getTotalProduction());
            map.put("totalRejection", report.getTotalRejection());
            map.put("rejectionPercentage",report.getEntry().getRejectionPercent());
            map.put("expected_machine_speed", report.getEntry().getExpectedMachineSpeed());
            map.put("actualSpeed",Math.round(report.getEntry().getActualSpeed()));
            map.put("valueOperatingTime", report.getEntry().valueOperatingTime());
            map.put("remark",report.getEntry().getRemarks());
            map.put("goodProduction",report.getEntry().getGoodProduction());
            map.put("gauge",report.getEntry().getVariant().stream().map(Variant::getName).toList());
            value.forEach(rp -> {
                String category = processCategory(rp.getCategory().getName());
                if(map.containsKey(category)) {
                    Double time = (Double) map.get(category);
                    map.put(category, time + rp.getTime());
                } else {
                    map.put(category, rp.getTime());
                }
            });
            data.add(map);
        });

        return data;
    }

    private static String processCategory(String category) {
        return category.replaceAll(" ", "_").toLowerCase();
    }

    public static List<DefaultSearchResponse> getHeaders(List<Category> categories) {
        List<DefaultSearchResponse> searchResponses = categories.stream().map(category -> {
            DefaultSearchResponse defaultSearchResponse = DefaultSearchResponse.builder()
                    .identifier(processCategory(category.getName()))
                    .label(category.getName())
                    .build();
            return defaultSearchResponse;
        }).toList();
        return searchResponses;
    }
}
