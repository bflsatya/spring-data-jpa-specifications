package com.fragma.repos;

import com.fragma.models.SearchCriteria;
import com.fragma.models.SearchOperation;
import com.fragma.models.SearchRequestDto;
import com.fragma.util.FeaturedQueryBuilderUtil;
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


}
