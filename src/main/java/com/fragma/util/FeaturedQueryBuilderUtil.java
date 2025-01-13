package com.fragma.util;

import com.fragma.models.SearchCriteria;
import com.fragma.models.SearchOperation;
import com.fragma.models.SearchRequestDto;

import java.sql.Types;
import java.util.List;
import java.util.Objects;

import static com.fragma.models.EntityAlias.getEntityAliasByName;
import static com.fragma.models.QueryConstants.*;
import static com.fragma.models.SearchOperation.getSearchOpEnumByOperationStr;

public class FeaturedQueryBuilderUtil {
    public static StringBuilder buildQueryWithSearchCriteria(SearchRequestDto searchRequestDto, List<Object> queryParams, List<Integer> dataTypes) {
        List<SearchCriteria> searchCriteriaList = searchRequestDto.getSearchCriteriaList();
        StringBuilder queryBuilder = new StringBuilder();
        if(Objects.nonNull(searchCriteriaList) && !searchCriteriaList.isEmpty()) {
            for(SearchCriteria searchCriteria : searchCriteriaList) {
                String columnQualifier = searchCriteria.getColumnQualifier();
                String columnName = searchCriteria.getColumnName();
                String entityAlias = getEntityAliasByName(columnQualifier);
                String operation = searchCriteria.getOperation();
                List<Object> columnValues = searchCriteria.getColumnValues();
                int sqlDataType = searchCriteria.getColumnDataType();
                addPlaceHoldersParamsAndDataTypesByOperationType(operation, sqlDataType, columnValues,
                        queryBuilder, queryParams, dataTypes, columnName, entityAlias);
            }
        }
        return queryBuilder;
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
}
