package com.fragma.repos;

import com.fragma.models.SearchCriteria;
import com.fragma.models.SearchOperation;
import com.fragma.models.SearchRequestDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Objects;

import static com.fragma.models.QueryConstants.*;
import static com.fragma.models.EntityAlias.*;
import static com.fragma.models.SearchOperation.*;

@Repository
public class FeaturedQueryBuilderDao {
    private static final Logger LOGGER = LogManager.getLogger(FeaturedQueryBuilderDao.class);

    public FeaturedQueryBuilderDao() {
    }

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void getQueryResults(SearchRequestDto searchRequestDto, int pageNumber, int pageSize) {

    }


    public static StringBuilder buildQueryWithSearchCriteria(SearchRequestDto searchRequestDto, List<Object> queryParams, List<Integer> dataTypes) {
        List<SearchCriteria> searchCriteriaList = searchRequestDto.getSearchCriteriaList();
        StringBuilder queryBuilder = new StringBuilder();
        if(Objects.nonNull(searchCriteriaList) && !searchCriteriaList.isEmpty()) {
            for(SearchCriteria searchCriteria : searchCriteriaList) {
                String columnEntityName = searchCriteria.getColumnEntityName();
                String columnName = searchCriteria.getColumnName();
                String entityAliasName = getEntityAliasByName(columnEntityName);
                String operation = searchCriteria.getOperation();
                Object columnValue = searchCriteria.getColumnValue();
                int sqlDataType = searchCriteria.getSqlDataType();
                queryBuilder.append(SPACE)
                            .append(AND)
                            .append(SPACE)
                            .append(entityAliasName).append(DOT).append(columnName).
                            append(SPACE);
                addPlaceHoldersParamsAndDataTypesByOperationType(operation, sqlDataType, columnValue,
                                                                queryBuilder, queryParams, dataTypes);
                System.out.println("Test");
           }
        }


        return null;
    }



    private static void addPlaceHoldersParamsAndDataTypesByOperationType(String operation, int sqlDataType, Object columnValue,
                                                                  StringBuilder queryBuilder, List<Object> queryParams,
                                                                  List<Integer> dataTypes) {

        SearchOperation searchOperation = getSearchOpEnumByOperationStr(operation);
        switch(searchOperation) {
            case EQUALS:
            case GREATER_THAN_EQUALS:
            case LESS_THAN_EQUALS: {
                queryBuilder.append(SPACE)
                            .append(searchOperation.getSqlOperator()).append(PLACEHOLDER)
                            .append(SPACE);
                queryParams.add(columnValue);
                dataTypes.add(sqlDataType);
                break;
            }
            case CONTAINS:
            case DOES_NOT_CONTAIN: {
                queryBuilder.append(SPACE)
                            .append(searchOperation.getSqlOperator())
                            .append(SPACE)
                            .append(PLACEHOLDER)
                            .append(SPACE);
                queryParams.add(LIKE_WILDCARD + columnValue + LIKE_WILDCARD);
                dataTypes.add(sqlDataType);
                break;
            }

        }
    }

}
