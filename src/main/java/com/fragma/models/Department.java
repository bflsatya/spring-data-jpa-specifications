package com.fragma.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Department {
    @Id
    @Column(name = "DEPT_ID")
    private Long dept_id;

    @Column(name = "DEPT_NAME")
    private String dept_name;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Employee> emps;
}
