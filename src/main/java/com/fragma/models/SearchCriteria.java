package com.fragma.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLType;
import java.sql.Types;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String columnName;
    private Object columnValue;
    private String operation;
    //specifies the SQL data type of the Column. from java.sql.Types
    private int sqlDataType;
    private String columnEntityName;//columnQualifier
}
