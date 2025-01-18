package com.fragma.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fragma.models.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GenericSearchController {


    @PostMapping(value = "/testSearchCriteriaPOST", produces = "application/json", consumes = "application/json")
    public String getSearchRequestPOST(@RequestBody SearchRequestDto searchRequestDto /*SortCriteria sortCriteria*/) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(searchRequestDto);
        System.out.println(s);
        return null;
    }

    @GetMapping("/testSearchCriteria")
    public SearchRequestDto getSearchRequestDto() {
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setUiColumnName("emailId");
        searchCriteria1.setOperation(SearchOperation.EQUALS.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("test@gmail.com");

        searchCriteria1.setColumnValues(columnValues);
        searchCriteriaList.add(searchCriteria1);

        SearchCriteria searchCriteria2 = new SearchCriteria();
        searchCriteria2.setUiColumnName("age");
        searchCriteria2.setOperation(SearchOperation.EQUALS.getSearchOpStr());
        List<Object> columnValues2 = new ArrayList<>();
        columnValues2.add(1.1);
        searchCriteria2.setColumnValues(columnValues2);
        searchCriteriaList.add(searchCriteria2);
        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<SortCriteria> sortCriteriaList = new ArrayList<>();
        SortCriteria sortCriteria1 = new SortCriteria();
        sortCriteria1.setUiColumnName("emailId");
        sortCriteria1.setSortDir("ASC");

        sortCriteriaList.add(sortCriteria1);

        SortCriteria sortCriteria2 = new SortCriteria();
        sortCriteria2.setUiColumnName("fromEmail");
        sortCriteria2.setSortDir("DESC");

        sortCriteriaList.add(sortCriteria2);
        searchRequestDto.setSortCriteriaList(sortCriteriaList);

        return searchRequestDto;
    }
}
