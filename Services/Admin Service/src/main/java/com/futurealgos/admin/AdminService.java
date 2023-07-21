package com.futurealgos.admin;

import com.futurealgos.admin.exception.exceptions.NotFoundException;
import com.futurealgos.admin.models.secondary.entry.Production;
import com.futurealgos.admin.models.secondary.entry.Template;
import com.futurealgos.admin.repos.secondary.ProductionRepository;
import com.futurealgos.admin.repos.secondary.TemplateRepository;
import com.futurealgos.admin.services.entry.EntryProcessingService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(basePackages = {"com.futurealgos.data", "com.futurealgos.admin",  "com.baeldung.circulardependency"})
@EntityScan(basePackages = {"com.futurealgos.data.models", "com.futurealgos.admin.models"})
@EnableJpaRepositories(basePackages = {"com.futurealgos.admin.repos", "com.futurealgos.data.repos"})
public class AdminService {

    static long start;

    @Autowired
    ApplicationContext context;

    @Autowired
    EntryProcessingService entryProcessingService;
    @Autowired
    private ProductionRepository productionRepository;
    @Autowired
    private TemplateRepository templateRepository;

    public static void main(String[] args) {
        start = System.currentTimeMillis();
        SpringApplication.run(AdminService.class, args);

    }
    @EventListener(ApplicationReadyEvent.class)
    public void logStartup() {
        long total = System.currentTimeMillis() - start;
        System.out.println("Service has started successfully (took " + (total / 1000) + " s)");
    }
}
