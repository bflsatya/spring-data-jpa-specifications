package com.fragma.models;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SearchCriteria implements Serializable {
    private String uiColumnName;
    private List<Object> columnValues;
    private String operation;
}
