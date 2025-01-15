package com.fragma.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String uiColumnName;
    private List<Object> columnValues;
    private String operation;
}
