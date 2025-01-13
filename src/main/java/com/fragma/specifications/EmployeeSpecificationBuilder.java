package com.fragma.specifications;

import com.fragma.models.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecificationBuilder {

    Specification<Employee> employeeSpec;

    public EmployeeSpecificationBuilder(Specification<Employee> employeeSpec) {
        this.employeeSpec = employeeSpec;
    }

    public EmployeeSpecificationBuilder and(Specification<Employee> andEmployeeSpec) {
        this.employeeSpec.and(andEmployeeSpec);
        return this;
    }

    public EmployeeSpecificationBuilder or(Specification<Employee> orEmployeeSpec) {
        this.employeeSpec.or(orEmployeeSpec);
        return this;
    }

    public Specification<Employee> getFinalEmployeeSpec() {
        return this.employeeSpec;
    }

}
