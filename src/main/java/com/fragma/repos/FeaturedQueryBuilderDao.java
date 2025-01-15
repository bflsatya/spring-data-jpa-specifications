package com.fragma.repos;

import com.fragma.models.SearchRequestDto;
import com.fragma.util.FeaturedQueryBuilderUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.sql.Types;
import java.util.*;

import static com.fragma.models.QueryConstants.*;

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

        // Send

        Map<String, Triple<String,String,Integer>> uiColumnNameToColumnMetadataMap = constructColumnMetadataMapForAPI();
        FeaturedQueryBuilderUtil.buildQueryWithSearchCriteria(searchRequestDto,queryParams,paramDataTypes,queryBuilder,uiColumnNameToColumnMetadataMap);

        Pair<Object[],int[]> paramConversionResult = FeaturedQueryBuilderUtil.convertParamsToArray(queryParams, paramDataTypes);
        String countQuery = "select count(1) total_rows from ( " + queryBuilder.toString() + " ) count_query";
        List<Integer> countOfRowsResult = jdbcTemplate.query(countQuery, paramConversionResult.getLeft(), paramConversionResult.getRight(),
                                                            (resultSet, i) -> resultSet.getInt("total_rows"));
        int totalNumberOfRows = countOfRowsResult.get(0);
        FeaturedQueryBuilderUtil.buildQueryWithSortCriteria(searchRequestDto.getSortCriteriaList(), queryBuilder, uiColumnNameToColumnMetadataMap);
        queryBuilder.append(" offset ? rows fetch next ? rows only");
        String finalQueryWithSearchSortAndPage = queryBuilder.toString();
        FeaturedQueryBuilderUtil.addPageRequestDataToQueryParams(pageNumber, pageSize, queryParams, paramDataTypes);
        paramConversionResult = FeaturedQueryBuilderUtil.convertParamsToArray(queryParams, paramDataTypes);

        jdbcTemplate.query(finalQueryWithSearchSortAndPage, paramConversionResult.getLeft() , paramConversionResult.getRight(), (resultSet, i) -> null);

        //set Response Object's totalNumberOfRows(totalNumberOfRows)
        //set Response Object's currPageNumber(pageNumber)
        //set Response Object's result from above Query to a Variable
    }

    private Map<String, Triple<String,String,Integer>> constructColumnMetadataMapForAPI() {
        Map<String, Triple<String,String,Integer>> uiColumnNameToColumnMetadataMap = new HashMap<>();
        uiColumnNameToColumnMetadataMap.put("emailId", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_MASTER),"EmailAddress", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("counterPartyId", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_MASTER),"counterPartyId", Types.BIGINT));
        uiColumnNameToColumnMetadataMap.put("activeFlag", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_MASTER),"isActive", Types.BOOLEAN));
        uiColumnNameToColumnMetadataMap.put("outlookId", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"OutlookIDForEmail", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("fromEmail", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"From", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("toEmail", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"To", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("subject", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"Subject", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("receivedDate", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"EmailReceivedDateTime", Types.TIMESTAMP));
        uiColumnNameToColumnMetadataMap.put("emailFilePath", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"EmailFilePathInFileSystem", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("status", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"Status", Types.VARCHAR));
        uiColumnNameToColumnMetadataMap.put("statusLog", Triple.of(ENTITY_ALIAS_MAP.get(EMAIL_INSTANCE),"StatusLog", Types.VARCHAR));

        return uiColumnNameToColumnMetadataMap;
    }

}
