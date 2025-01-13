package com.fragma;

import com.fragma.models.SearchOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaRepositories
public class JPASpecificationApp {
    public static void main(String[] args) {
        SpringApplication.run(JPASpecificationApp.class);
    }
}
