package com.fragma.models;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SearchRequestDto implements Serializable {
    private List<SearchCriteria> searchCriteriaList;
    private List<SortCriteria> sortCriteriaList;
}
