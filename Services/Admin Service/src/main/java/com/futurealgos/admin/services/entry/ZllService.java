package com.futurealgos.admin.services.entry;

import com.futurealgos.admin.dto.response.entry.ZllInfo;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.Category;
import com.futurealgos.admin.models.views.ZllReport;
import com.futurealgos.admin.repos.views.ZllRepository;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.reasons.CategoryService;
import com.futurealgos.data.service.ReaderTemplate;
import com.futurealgos.specs.utils.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ZllService extends ReaderTemplate<ZllReport, UUID, Void, Void, Void> {

    @Autowired
    AreaService areaService;

    @Autowired
    CategoryService categoryService;

    ZllRepository zllReportRepository;

    public ZllService(ZllRepository reader) {
        super(ZllReport.class, reader, reader);
        zllReportRepository = reader;
    }


    public ZllInfo report(String areaId, LocalDate start, LocalDate end) {
        Area area = areaService.fetch(areaId);
        List<ZllReport> zlls = super.searchElements(
                SearchFilter.is("area", area),
                SearchFilter.isAfter("date", start),
                SearchFilter.isBefore("date", end)
        );
        List<SearchFilter<?>> filters = new ArrayList<>();
        filters.add(SearchFilter.isNotNull("parentCategory"));
        List<Category> categories = categoryService.searchElements(filters.toArray((SearchFilter[]::new)));

        return ZllInfo.from(zlls, categories);
    }
}
