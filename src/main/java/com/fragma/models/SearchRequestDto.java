package com.fragma.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDto {
    private List<SearchCriteria> searchCriteriaList;
    private List<SortCriteria> sortCriteriaList;
    //private String dataOperation;
}
// Request Params -- Page Number an Page Size