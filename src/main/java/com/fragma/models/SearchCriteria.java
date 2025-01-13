package com.fragma.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String columnName;
    private List<Object> columnValues;
    private String operation;
    //specifies the SQL data type of the Column. from java.sql.Types
    private int columnDataType;
    private String columnQualifier;
}
