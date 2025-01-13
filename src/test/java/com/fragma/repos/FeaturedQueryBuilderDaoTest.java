package com.fragma.repos;

import com.fragma.models.SearchCriteria;
import com.fragma.models.SearchOperation;
import com.fragma.models.SearchRequestDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FeaturedQueryBuilderDaoTest {

    @Test
    public void buildQueryWithSearchCriteria() {
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        searchCriteriaList.add(constructSearchCriteria("emailId","test@outlook.com","cn",1,"EmailInstance"));
        searchRequestDto.setSearchCriteriaList(searchCriteriaList);
        FeaturedQueryBuilderDao.buildQueryWithSearchCriteria(searchRequestDto, new ArrayList<>(), new ArrayList<>());

    }

    private SearchCriteria constructSearchCriteria(String columnName, Object columnValue,
                                                   String operation, int sqlDataType, String columnEntityName) {
        return new SearchCriteria(columnName,columnValue,operation,sqlDataType,columnEntityName);
    }
}