package com.fragma.models;

import org.junit.Test;


public class SearchOperationTest {

    @Test
    public void testEnum() {
        String sqlOperationBySearchOperation = SearchOperation.getSQLOperationBySearchOperation(SearchOperation.CONTAINS);
        String test = String.format(sqlOperationBySearchOperation, "test");
        System.out.println(test);

        System.out.println(SearchOperation.getSQLOperationBySearchOperationStr("dcn"));
        System.out.println(String.format("No formatters", "te"));

    }


}