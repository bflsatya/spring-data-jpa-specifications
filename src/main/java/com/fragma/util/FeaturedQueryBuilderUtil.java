package com.fragma.util;

import com.fragma.models.SearchCriteria;
import com.fragma.models.SearchOperation;
import com.fragma.models.SearchRequestDto;
import com.fragma.models.SortCriteria;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fragma.models.QueryConstants.*;
import static com.fragma.models.SearchOperation.getSearchOpEnumByOperationStr;

public class FeaturedQueryBuilderUtil {

    public static void buildQueryWithSearchCriteria(SearchRequestDto searchRequestDto,
                                                    List<Object> queryParams, List<Integer> dataTypes,
                                                    StringBuilder queryBuilder, Map<String, Triple<String,String,Integer>> columnMetadataMap) {
        List<SearchCriteria> searchCriteriaList = searchRequestDto.getSearchCriteriaList();
        if(Objects.nonNull(searchCriteriaList) && !searchCriteriaList.isEmpty()) {
            for(SearchCriteria searchCriteria : searchCriteriaList) {
                String uiColumnName = searchCriteria.getUiColumnName();
                String entityAlias = columnMetadataMap.get(uiColumnName).getLeft();
                String dbColumnName = columnMetadataMap.get(uiColumnName).getMiddle();
                int sqlDataType = columnMetadataMap.get(uiColumnName).getRight();
                String operation = searchCriteria.getOperation();
                List<Object> columnValues = searchCriteria.getColumnValues();
                addPlaceHoldersParamsAndDataTypesByOperationType(operation, sqlDataType, columnValues,
                        queryBuilder, queryParams, dataTypes, dbColumnName, entityAlias);
            }
        }
    }



    private static void addPlaceHoldersParamsAndDataTypesByOperationType(String operation, int sqlDataType, List<Object> columnValues,
                                                                         StringBuilder queryBuilder, List<Object> queryParams,
                                                                         List<Integer> dataTypes, String columnName, String entityAlias) {

        SearchOperation searchOperation = getSearchOpEnumByOperationStr(operation);
        switch(searchOperation) {
            // " and (alias.columnName (= or >= or <=) ?) "
            case EQUALS:
            case GREATER_THAN_EQUALS:
            case LESS_THAN_EQUALS: {
                queryBuilder.append(SPACE)
                        .append(AND)
                        .append(SPACE)
                        .append("(")
                        .append(entityAlias).append(DOT).append(columnName)
                        .append(SPACE)
                        .append(searchOperation.getSqlOperator()).append(PLACEHOLDER)
                        .append(")")
                        .append(SPACE);
                queryParams.add(columnValues.get(0));
                dataTypes.add(sqlDataType);
                break;
            }
            // " and (alias.columnName (LIKE or NOT LIKE) ?) "
            case CONTAINS:
            case DOES_NOT_CONTAIN: {
                queryBuilder.append(SPACE)
                        .append(AND)
                        .append(SPACE)
                        .append("(")
                        .append(entityAlias).append(DOT).append(columnName)
                        .append(SPACE)
                        .append(searchOperation.getSqlOperator())
                        .append(SPACE)
                        .append(PLACEHOLDER)
                        .append(")")
                        .append(SPACE);
                queryParams.add(LIKE_WILDCARD + columnValues.get(0) + LIKE_WILDCARD);
                dataTypes.add(sqlDataType);
                break;
            }
            // " and (alias.columnName BETWEEN cast(? as DATE) and cast(? as DATE)) "
            case BETWEEN: {
                if(Types.DATE == sqlDataType) {
                    //
                    queryBuilder.append(SPACE)
                            .append(AND)
                            .append(SPACE)
                            .append("(")
                            .append(entityAlias).append(DOT).append(columnName)
                            .append(SPACE)
                            .append(searchOperation.getSqlOperator())
                            .append(SPACE)
                            .append("cast(").append(PLACEHOLDER).append(SPACE).append("as").append(SPACE).append("DATE").append(")")
                            .append(SPACE)
                            .append(AND)
                            .append(SPACE)
                            .append("cast(").append(PLACEHOLDER).append(SPACE).append("as").append(SPACE).append("DATE").append(")")
                            .append(")")
                            .append(SPACE);

                }
                // " and (alias.columnName BETWEEN ? and ?) "
                else { //NUMERIC
                    queryBuilder.append(SPACE)
                            .append(AND)
                            .append(SPACE)
                            .append("(")
                            .append(entityAlias).append(DOT).append(columnName)
                            .append(SPACE)
                            .append(searchOperation.getSqlOperator())
                            .append(SPACE)
                            .append(PLACEHOLDER).append(SPACE).append(AND).append(SPACE).append(PLACEHOLDER)
                            .append(")")
                            .append(SPACE);
                }
                queryParams.add(columnValues.get(0));
                queryParams.add(columnValues.get(1));
                dataTypes.add(sqlDataType);
                dataTypes.add(sqlDataType);
                break;
            }

        }
    }

    public static void buildQueryWithSortCriteria(List<SortCriteria> sortCriteriaList, StringBuilder queryBuilder, Map<String, Triple<String,String,Integer>> columnMetadataMap) {
        if(Objects.nonNull(sortCriteriaList) && !sortCriteriaList.isEmpty()) {
            queryBuilder.append(SPACE).append(ORDER_BY).append(SPACE);
            for(SortCriteria sortCriteria : sortCriteriaList) {
                String uiColumnName = sortCriteria.getUiColumnName();
                String entityAlias = columnMetadataMap.get(uiColumnName).getLeft();
                String dbColumnName = columnMetadataMap.get(uiColumnName).getMiddle();
                String sortDir = sortCriteria.getSortDir();
                createSortQuery(entityAlias, dbColumnName, sortDir, queryBuilder);
                queryBuilder.append(COMMA);
            }
            queryBuilder.setLength(Math.max(queryBuilder.length() - 1, 0));
        }
    }

    private static void createSortQuery(String entityAlias,String columnName,String sortDir,
                                        StringBuilder queryBuilder) {
        queryBuilder.append(entityAlias).append(DOT).append(columnName)
                    .append(SPACE)
                    .append(sortDir);
    }

    public static Pair<Object[],int[]> convertParamsToArray(List<Object> queryParams, List<Integer> paramDataTypes) {
        Object[] queryParamsArray = new Object[queryParams.size()];
        int[] paramDataTypesArray = new int[paramDataTypes.size()];
        for(int i=0; i< queryParams.size();i++) {
            queryParamsArray[i] = queryParams.get(i);
        }
        for(int i=0; i< paramDataTypes.size();i++) {
            paramDataTypesArray[i] = paramDataTypes.get(i);
        }
        return Pair.of(queryParamsArray,paramDataTypesArray);
    }

    public static void addPageRequestDataToQueryParams(int pageNumber, int pageSize, List<Object> queryParams, List<Integer> paramDataTypes) {
        queryParams.add((pageNumber -1)* pageSize);
        queryParams.add(pageSize);
        paramDataTypes.add(Types.INTEGER);
        paramDataTypes.add(Types.INTEGER);
    }


}
