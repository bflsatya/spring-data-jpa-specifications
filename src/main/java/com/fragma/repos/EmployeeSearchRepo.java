package com.fragma.repos;

import com.fragma.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeSearchRepo extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
}
