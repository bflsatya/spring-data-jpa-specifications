package com.fragma.controllers;

import com.fragma.models.Employee;
import com.fragma.models.SearchCriteria;
import com.fragma.services.EmployeeSearchService;
import com.fragma.specifications.EmployeeSpecificationBuilder;
import com.fragma.specifications.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fragma.specifications.EmployeeSpecifications.*;

@RestController
public class EmployeeController {


    @Autowired
    EmployeeSearchService employeeSearchService;


    @PostMapping("/employee/filter")
    public List<Employee> filterEmployee(List<SearchCriteria> searchCriteriaList) {
        return employeeSearchService.getSearchResults(searchCriteriaList);
    }
}
