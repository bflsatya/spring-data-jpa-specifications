package com.fragma.services;

import com.fragma.models.Employee;
import com.fragma.models.SearchCriteria;
import com.fragma.repos.EmployeeSearchRepo;
import com.fragma.specifications.EmployeeSpecificationBuilder;
import com.fragma.specifications.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fragma.specifications.EmployeeSpecifications.firstNameLikeSpec;

@Service
public class EmployeeSearchService {
    @Autowired
    EmployeeSearchRepo employeeSearchRepo;

    EmployeeSpecificationBuilder employeeSpecificationBuilder;

    public List<Employee> getEmployeeBySearchCriteria(SearchCriteria searchCriteria) {
        return employeeSearchRepo.findAll(EmployeeSpecifications.firstNameLikeSpec("").and(EmployeeSpecifications.lastNameLikeSpec("")));
    }

    public List<Employee> getSearchResults(List<SearchCriteria> searchCriteriaList) {
        Map<String, List<SearchCriteria>> searchCriteriaGroupedByColumnName = getSearchCriteriaGroupedByColumnName(searchCriteriaList);
        employeeSpecificationBuilder = new EmployeeSpecificationBuilder(firstNameLikeSpec(""));
        Stream<Object> objectStream = searchCriteriaGroupedByColumnName.entrySet().stream().flatMap(new Function<Map.Entry<String, List<SearchCriteria>>, Stream<?>>() {
            @Override
            public Stream<?> apply(Map.Entry<String, List<SearchCriteria>> stringListEntry) {
                return null;
            }
        });
        return  null;
    }

    private static Map<String, List<SearchCriteria>> getSearchCriteriaGroupedByColumnName(List<SearchCriteria> searchCriteriaList) {
        return searchCriteriaList.stream().collect(Collectors.groupingBy(searchCriteria -> searchCriteria.getUiColumnName()));
    }

    //private Specification<Employee> getSpecificationByColumnName(String columnName, String columnValue, )

}
