package com.fragma.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortCriteria {
    private String columnName;
    private String sortDir;
    private String columnQualifier;
}
