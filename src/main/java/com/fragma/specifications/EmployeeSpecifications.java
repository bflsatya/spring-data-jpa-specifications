package com.fragma.specifications;

import com.fragma.models.Employee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EmployeeSpecifications {
    public static Specification<Employee> firstNameLikeSpec(String firstNamePartStr) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("emp_firstNm"), firstNamePartStr);
        };
    }

    public static Specification<Employee> lastNameLikeSpec(String lastNamePartStr) {
        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("emp_lastNm"), lastNamePartStr);
            }
        };
    }

    public static Specification<Employee> firstNameEqualsSpec(String firstName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("emp_firstNm"), firstName);
        };
    }

    public static Specification<Employee> lastNameEqualsSpec(String lastName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("emp_lastNm"), lastName);
        };
    }

}
