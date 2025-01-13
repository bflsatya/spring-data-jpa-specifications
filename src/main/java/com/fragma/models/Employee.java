package com.fragma.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Employee {
    @Id
    @Column(name = "EMP_ID")
    private Long emp_id;

    @Column(name = "EMP_LASTNM")
    private String emp_lastNm;

    @Column(name = "EMP_FIRSTNM")
    private String  emp_firstNm;

    @Column(name = "JOB_NM")
    private String jobNm;

    @Column(name = "MGR_ID", nullable = true)
    private Long manager_id;

    @Column(name = "HIREDT")
    private Date hireDt;

    @Column(name = "SALARY")
    private double salary;

    @Column(name = "COMMISSION", nullable = true)
    private double commission;

    @ManyToOne
    @JoinColumn(name = "DEPT_ID")
    private Department department;

}
