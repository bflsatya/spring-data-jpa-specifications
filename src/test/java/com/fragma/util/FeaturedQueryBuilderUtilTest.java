package com.fragma.util;

import com.fragma.models.SearchCriteria;
import com.fragma.models.SearchOperation;
import com.fragma.models.SearchRequestDto;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;



public class FeaturedQueryBuilderUtilTest {


    @Test
    public void shouldReturnEQUALSQuery() {

        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setColumnName("emailId");
        searchCriteria1.setColumnDataType(Types.VARCHAR);
        searchCriteria1.setColumnQualifier("ei");
        searchCriteria1.setOperation(SearchOperation.EQUALS.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("test@gmail.com");

        searchCriteria1.setColumnValues(columnValues);
        searchCriteriaList.add(searchCriteria1);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder stringBuilder = FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes);
        Assert.assertEquals(stringBuilder.toString()," AND (ei.emailId =?) ");
    }

    @Test
    public void shouldReturnBETWEENDATESQuery() {

        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setColumnName("createdDate");
        searchCriteria1.setColumnDataType(Types.DATE);
        searchCriteria1.setColumnQualifier("ei");
        searchCriteria1.setOperation(SearchOperation.BETWEEN.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("11-01-2025");
        columnValues.add("12-01-2025");

        searchCriteria1.setColumnValues(columnValues);
        searchCriteriaList.add(searchCriteria1);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder stringBuilder = FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes);
        Assert.assertEquals(stringBuilder.toString()," AND (ei.createdDate BETWEEN cast(? as DATE) AND cast(? as DATE)) ");
    }

    @Test
    public void shouldReturnBETWEENINTEGERSQuery() {

        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setColumnName("age");
        searchCriteria1.setColumnDataType(Types.INTEGER);
        searchCriteria1.setColumnQualifier("ei");
        searchCriteria1.setOperation(SearchOperation.BETWEEN.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("2");
        columnValues.add("5");

        searchCriteria1.setColumnValues(columnValues);
        searchCriteriaList.add(searchCriteria1);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder stringBuilder = FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes);
        Assert.assertEquals(" AND (ei.age BETWEEN ? AND ?) ",stringBuilder.toString());
    }

    @Test
    public void shouldReturnLIKEQuery() {

        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setColumnName("emailId");
        searchCriteria1.setColumnDataType(Types.VARCHAR);
        searchCriteria1.setColumnQualifier("ei");
        searchCriteria1.setOperation(SearchOperation.CONTAINS.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("test@gmail");

        searchCriteria1.setColumnValues(columnValues);
        searchCriteriaList.add(searchCriteria1);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder stringBuilder = FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes);
        Assert.assertEquals(" AND (ei.emailId LIKE ?) ", stringBuilder.toString());
    }

    @Test
    public void shouldReturnNOTLIKEQuery() {

        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setColumnName("emailId");
        searchCriteria1.setColumnDataType(Types.VARCHAR);
        searchCriteria1.setColumnQualifier("ei");
        searchCriteria1.setOperation(SearchOperation.DOES_NOT_CONTAIN.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("test@gmail");

        searchCriteria1.setColumnValues(columnValues);
        searchCriteriaList.add(searchCriteria1);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder stringBuilder = FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes);
        Assert.assertEquals(" AND (ei.emailId NOT LIKE ?) ", stringBuilder.toString());
    }



}