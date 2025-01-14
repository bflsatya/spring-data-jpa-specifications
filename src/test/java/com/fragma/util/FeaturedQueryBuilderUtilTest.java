package com.fragma.util;

import com.fragma.models.SearchCriteria;
import com.fragma.models.SearchOperation;
import com.fragma.models.SearchRequestDto;
import com.fragma.models.SortCriteria;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
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
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder);
        Assert.assertEquals(" AND (ei.emailId =?) ", queryBuilder.toString());
        Assert.assertEquals(1, queryParams.size());
        Assert.assertEquals(1, dataTypes.size());
        Assert.assertEquals("test@gmail.com", queryParams.get(0));
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
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder);
        Assert.assertEquals(queryBuilder.toString()," AND (ei.createdDate BETWEEN cast(? as DATE) AND cast(? as DATE)) ");
        Assert.assertEquals(2, queryParams.size());
        Assert.assertEquals(2, dataTypes.size());
        Assert.assertEquals("11-01-2025", queryParams.get(0));
        Assert.assertEquals("12-01-2025", queryParams.get(1));
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
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder);
        Assert.assertEquals(" AND (ei.age BETWEEN ? AND ?) ",queryBuilder.toString());
        Assert.assertEquals(2, queryParams.size());
        Assert.assertEquals(2, dataTypes.size());
        Assert.assertEquals("2", queryParams.get(0));
        Assert.assertEquals("5", queryParams.get(1));
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
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder);

        Assert.assertEquals(" AND (ei.emailId LIKE ?) ", queryBuilder.toString());
        Assert.assertEquals(1, queryParams.size());
        Assert.assertEquals(1, dataTypes.size());
        Assert.assertEquals("%test@gmail%", queryParams.get(0));
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
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder);

        Assert.assertEquals(" AND (ei.emailId NOT LIKE ?) ", queryBuilder.toString());
        Assert.assertEquals(1, queryParams.size());
        Assert.assertEquals(1, dataTypes.size());
        Assert.assertEquals("%test@gmail%", queryParams.get(0));
    }

    @Test
    public void shouldReturnMultipleSearchCriteriaQuery() {
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

        SearchCriteria searchCriteria2 = new SearchCriteria();
        searchCriteria2.setColumnName("age");
        searchCriteria2.setColumnDataType(Types.INTEGER);
        searchCriteria2.setColumnQualifier("ei");
        searchCriteria2.setOperation(SearchOperation.BETWEEN.getSearchOpStr());

        List<Object> columnValues2 = new ArrayList<>();
        columnValues2.add("2");
        columnValues2.add("5");

        searchCriteria2.setColumnValues(columnValues2);

        searchCriteriaList.add(searchCriteria1);
        searchCriteriaList.add(searchCriteria2);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder);
        Assert.assertEquals(" AND (ei.emailId LIKE ?)  AND (ei.age BETWEEN ? AND ?) ", queryBuilder.toString());
        //System.out.println(queryBuilder.toString());
    }


    @Test
    public void shouldReturnSORTBYASCENDINGQuery() {

        List<SortCriteria> sortCriteriaList = new ArrayList<>();
        SortCriteria sortCriteria1 = new SortCriteria();
        sortCriteria1.setColumnName("emailId");
        sortCriteria1.setColumnQualifier("ei");
        sortCriteria1.setSortDir("ASC");

        sortCriteriaList.add(sortCriteria1);

        SortCriteria sortCriteria2 = new SortCriteria();
        sortCriteria2.setColumnName("name");
        sortCriteria2.setColumnQualifier("em");
        sortCriteria2.setSortDir("DESC");

        sortCriteriaList.add(sortCriteria2);

        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSortCriteria(sortCriteriaList, queryBuilder);

        Assert.assertEquals(" ORDER BY ei.emailId ASC,em.name DESC", queryBuilder.toString());
    }


    @Test
    public void shouldReturnMultipleSearchCriteriaAndMultipleSortCriteriaQuery() {
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

        SearchCriteria searchCriteria2 = new SearchCriteria();
        searchCriteria2.setColumnName("age");
        searchCriteria2.setColumnDataType(Types.INTEGER);
        searchCriteria2.setColumnQualifier("ei");
        searchCriteria2.setOperation(SearchOperation.BETWEEN.getSearchOpStr());

        List<Object> columnValues2 = new ArrayList<>();
        columnValues2.add("2");
        columnValues2.add("5");

        searchCriteria2.setColumnValues(columnValues2);

        searchCriteriaList.add(searchCriteria1);
        searchCriteriaList.add(searchCriteria2);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder);

        List<SortCriteria> sortCriteriaList = new ArrayList<>();
        SortCriteria sortCriteria1 = new SortCriteria();
        sortCriteria1.setColumnName("emailId");
        sortCriteria1.setColumnQualifier("ei");
        sortCriteria1.setSortDir("ASC");

        sortCriteriaList.add(sortCriteria1);

        SortCriteria sortCriteria2 = new SortCriteria();
        sortCriteria2.setColumnName("name");
        sortCriteria2.setColumnQualifier("em");
        sortCriteria2.setSortDir("DESC");

        sortCriteriaList.add(sortCriteria2);

        FeaturedQueryBuilderUtil.buildQueryWithSortCriteria(sortCriteriaList, queryBuilder);
        Assert.assertEquals(" AND (ei.emailId LIKE ?)  AND (ei.age BETWEEN ? AND ?)  ORDER BY ei.emailId ASC,em.name DESC",
                            queryBuilder.toString());
    }


    @Test
    public void shouldTestArrayInitialization() {
        List<Object> queryParams= new ArrayList<>();
        List<Integer> paramDataTypes = new ArrayList<>();

        queryParams.add("param1");
        queryParams.add("param2");

        paramDataTypes.add(1);
        paramDataTypes.add(2);
        Pair<Object[],int[]> paramConversionResult = FeaturedQueryBuilderUtil.convertParamsToArray(queryParams,paramDataTypes);
        System.out.println(paramConversionResult);
        System.out.println(Arrays.toString(paramConversionResult.getLeft()));
        System.out.println(Arrays.toString(paramConversionResult.getRight()));

        queryParams.add("param3");
        queryParams.add("param4");

        paramDataTypes.add(3);
        paramDataTypes.add(4);

        Pair<Object[],int[]> paramConversionResult1 = FeaturedQueryBuilderUtil.convertParamsToArray(queryParams,paramDataTypes);
        System.out.println(paramConversionResult1);
        System.out.println(Arrays.toString(paramConversionResult.getLeft()));
        System.out.println(Arrays.toString(paramConversionResult.getRight()));

    }





}