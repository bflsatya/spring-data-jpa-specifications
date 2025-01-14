package com.fragma.repos;

import com.fragma.models.SearchCriteria;
import com.fragma.models.SearchOperation;
import com.fragma.models.SearchRequestDto;
import com.fragma.util.FeaturedQueryBuilderUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
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

        List<Object> queryParams = new ArrayList<>();
        List<Integer> paramDataTypes = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder();
        //queryBuilder should be populated with Base Query
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto,queryParams,paramDataTypes,queryBuilder);

        Pair<Object[],int[]> paramConversionResult = FeaturedQueryBuilderUtil.convertParamsToArray(queryParams, paramDataTypes);
        String countQuery = "select count(1) total_rows from ( " + queryBuilder.toString() + " ) count_query";
        List<Integer> countOfRowsResult = jdbcTemplate.query(countQuery, paramConversionResult.getLeft(), paramConversionResult.getRight(),
                                                            (resultSet, i) -> resultSet.getInt("total_rows"));
        int totalNumberOfRows = countOfRowsResult.get(0);
        FeaturedQueryBuilderUtil.buildQueryWithSortCriteria(searchRequestDto.getSortCriteriaList(), queryBuilder);
        queryBuilder.append(" offset ? rows fetch next ? rows only");
        String finalQueryWithSearchSortAndPage = queryBuilder.toString();
        FeaturedQueryBuilderUtil.addPageRequestDataToQueryParams(pageNumber, pageSize, queryParams, paramDataTypes);
        paramConversionResult = FeaturedQueryBuilderUtil.convertParamsToArray(queryParams, paramDataTypes);

        jdbcTemplate.query(finalQueryWithSearchSortAndPage, paramConversionResult.getLeft() , paramConversionResult.getRight(), (resultSet, i) -> null);

        //set Response Object's totalNumberOfRows(totalNumberOfRows)
        //set Response Object's currPageNumber(pageNumber)
        //set Response Object's result from above Query to a Variable
    }


}
