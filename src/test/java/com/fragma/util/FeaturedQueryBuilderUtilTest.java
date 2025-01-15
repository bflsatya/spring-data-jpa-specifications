package com.fragma.util;

import com.fragma.models.SearchCriteria;
import com.fragma.models.SearchOperation;
import com.fragma.models.SearchRequestDto;
import com.fragma.models.SortCriteria;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Types;
import java.util.*;

import static com.fragma.models.QueryConstants.*;


public class FeaturedQueryBuilderUtilTest {


    @Test
    public void shouldReturnEQUALSQuery() {

        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setUiColumnName("emailId");
        searchCriteria1.setOperation(SearchOperation.EQUALS.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("test@gmail.com");

        searchCriteria1.setColumnValues(columnValues);
        searchCriteriaList.add(searchCriteria1);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder, constructColumnMetadataMapForAPI());
        Assert.assertEquals(" AND (em.EmailAddress =?) ", queryBuilder.toString());
        Assert.assertEquals(1, queryParams.size());
        Assert.assertEquals(1, dataTypes.size());
        Assert.assertEquals("test@gmail.com", queryParams.get(0));
    }

    @Test
    public void shouldReturnBETWEENDATESQuery() {

        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setUiColumnName("receivedDate");
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
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder, constructColumnMetadataMapForAPI());
        Assert.assertEquals(queryBuilder.toString()," AND (ei.EmailReceivedDateTime BETWEEN cast(? as DATE) AND cast(? as DATE)) ");
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
        searchCriteria1.setUiColumnName("emId");
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
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder, constructColumnMetadataMapForAPI());
        Assert.assertEquals(" AND (em.EmailMasterID BETWEEN ? AND ?) ",queryBuilder.toString());
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
        searchCriteria1.setUiColumnName("emailId");
        searchCriteria1.setOperation(SearchOperation.CONTAINS.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("test@gmail");

        searchCriteria1.setColumnValues(columnValues);
        searchCriteriaList.add(searchCriteria1);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder, constructColumnMetadataMapForAPI());

        Assert.assertEquals(" AND (em.EmailAddress LIKE ?) ", queryBuilder.toString());
        Assert.assertEquals(1, queryParams.size());
        Assert.assertEquals(1, dataTypes.size());
        Assert.assertEquals("%test@gmail%", queryParams.get(0));
    }

    @Test
    public void shouldReturnNOTLIKEQuery() {

        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setUiColumnName("emailId");
        searchCriteria1.setOperation(SearchOperation.DOES_NOT_CONTAIN.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("test@gmail");

        searchCriteria1.setColumnValues(columnValues);
        searchCriteriaList.add(searchCriteria1);


        searchRequestDto.setSearchCriteriaList(searchCriteriaList);

        List<Object> queryParams= new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder, constructColumnMetadataMapForAPI());

        Assert.assertEquals(" AND (em.EmailAddress NOT LIKE ?) ", queryBuilder.toString());
        Assert.assertEquals(1, queryParams.size());
        Assert.assertEquals(1, dataTypes.size());
        Assert.assertEquals("%test@gmail%", queryParams.get(0));
    }

    @Test
    public void shouldReturnMultipleSearchCriteriaQuery() {
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setUiColumnName("emailId");
        searchCriteria1.setOperation(SearchOperation.CONTAINS.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("test@gmail");

        searchCriteria1.setColumnValues(columnValues);

        SearchCriteria searchCriteria2 = new SearchCriteria();
        searchCriteria2.setUiColumnName("emId");
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
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder, constructColumnMetadataMapForAPI());
        Assert.assertEquals(" AND (em.EmailAddress LIKE ?)  AND (em.EmailMasterID BETWEEN ? AND ?) ", queryBuilder.toString());
        //System.out.println(queryBuilder.toString());
    }


    @Test
    public void shouldReturnSORTBYASCENDINGQuery() {

        List<SortCriteria> sortCriteriaList = new ArrayList<>();
        SortCriteria sortCriteria1 = new SortCriteria();
        sortCriteria1.setUiColumnName("emailId");
        sortCriteria1.setSortDir("ASC");

        sortCriteriaList.add(sortCriteria1);

        SortCriteria sortCriteria2 = new SortCriteria();
        sortCriteria2.setUiColumnName("fromEmail");
        sortCriteria2.setSortDir("DESC");

        sortCriteriaList.add(sortCriteria2);

        StringBuilder queryBuilder = new StringBuilder();
        FeaturedQueryBuilderUtil.buildQueryWithSortCriteria(sortCriteriaList, queryBuilder, constructColumnMetadataMapForAPI());

        Assert.assertEquals(" ORDER BY em.EmailAddress ASC,ei.From DESC", queryBuilder.toString());
    }


    @Test
    public void shouldReturnMultipleSearchCriteriaAndMultipleSortCriteriaQuery() {
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria1 = new SearchCriteria();
        searchCriteria1.setUiColumnName("emailId");
        searchCriteria1.setOperation(SearchOperation.CONTAINS.getSearchOpStr());

        List<Object> columnValues = new ArrayList<>();
        columnValues.add("test@gmail");

        searchCriteria1.setColumnValues(columnValues);

        SearchCriteria searchCriteria2 = new SearchCriteria();
        searchCriteria2.setUiColumnName("emId");
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
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto, queryParams, dataTypes, queryBuilder, constructColumnMetadataMapForAPI());

        List<SortCriteria> sortCriteriaList = new ArrayList<>();
        SortCriteria sortCriteria1 = new SortCriteria();
        sortCriteria1.setUiColumnName("emailId");
        sortCriteria1.setSortDir("ASC");

        sortCriteriaList.add(sortCriteria1);

        SortCriteria sortCriteria2 = new SortCriteria();
        sortCriteria2.setUiColumnName("fromEmail");
        sortCriteria2.setSortDir("DESC");

        sortCriteriaList.add(sortCriteria2);

        FeaturedQueryBuilderUtil.buildQueryWithSortCriteria(sortCriteriaList, queryBuilder, constructColumnMetadataMapForAPI());
        Assert.assertEquals(" AND (em.EmailAddress LIKE ?)  AND (em.EmailMasterID BETWEEN ? AND ?)  ORDER BY em.EmailAddress ASC,ei.From DESC",
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

    private Map<String, Triple<String,String,Integer>> constructColumnMetadataMapForAPI() {
        Map<String, Triple<String,String,Integer>> uiColumnNameToColumnMetadataMap = new HashMap<>();
        uiColumnNameToColumnMetadataMap.put("emId", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_MASTER),"EmailMasterID", Types.BIGINT));
        uiColumnNameToColumnMetadataMap.put("emailId", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_MASTER),"EmailAddress", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("counterPartyId", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_MASTER),"counterPartyId", Types.BIGINT));
        uiColumnNameToColumnMetadataMap.put("activeFlag", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_MASTER),"isActive", Types.BOOLEAN));
        uiColumnNameToColumnMetadataMap.put("outlookId", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"OutlookIDForEmail", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("fromEmail", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"From", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("toEmail", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"To", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("subject", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"Subject", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("receivedDate", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"EmailReceivedDateTime", Types.DATE));
        uiColumnNameToColumnMetadataMap.put("emailFilePath", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"EmailFilePathInFileSystem", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("status", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"Status", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("statusLog", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"StatusLog", Types.VARCHAR));

        return uiColumnNameToColumnMetadataMap;
    }



}