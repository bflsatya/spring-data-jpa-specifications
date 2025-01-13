package com.fragma.models;

import java.util.HashMap;
import java.util.Map;

public enum SearchOperation {
    CONTAINS("cn","LIKE"), DOES_NOT_CONTAIN("dcn","NOT LIKE"),
    EQUALS("eq","="),GREATER_THAN_EQUALS("gte",">="),
    LESS_THAN_EQUALS("lte","<="),BETWEEN("btw","BETWEEN");

    private String searchOpStr;
    private String sqlOperator;
    private static Map<String,SearchOperation> searchOperationStrSearchOpMap = new HashMap<>();
    private static Map<SearchOperation,String> searchOpSearchOperationStrMap = new HashMap<>();

    private static Map<SearchOperation,String> searchOpSearchSQLOperationMap = new HashMap<>();
    private static Map<String,String> searchOpStrSearchSQLOperationMap = new HashMap<>();



    SearchOperation(String searchOpStr, String sqlOperator) {
        this.searchOpStr = searchOpStr;
        this.sqlOperator = sqlOperator;
    }



    static {
        for(SearchOperation searchOperation : SearchOperation.values()) {
            searchOperationStrSearchOpMap.put(searchOperation.getSearchOpStr(), searchOperation);
            searchOpSearchOperationStrMap.put(searchOperation, searchOperation.getSearchOpStr());
            searchOpSearchSQLOperationMap.put(searchOperation, searchOperation.getSqlOperator());
            searchOpStrSearchSQLOperationMap.put(searchOperation.getSearchOpStr(), searchOperation.getSqlOperator());
        }
    }

    public String getSearchOpStr() {
        return searchOpStr;
    }

    public void setSearchOpStr(String searchOpStr) {
        this.searchOpStr = searchOpStr;
    }

    public String getSqlOperator(){ return sqlOperator; }

    public static SearchOperation getSearchOperationByStr(String searchOpStr) {
        return searchOperationStrSearchOpMap.get(searchOpStr);
    }

    public static String getSearchOperationStrBySearchOperation(SearchOperation searchOperation) {
        return searchOpSearchOperationStrMap.get(searchOperation);
    }

    public static String getSQLOperationBySearchOperation(SearchOperation searchOperation) {
        return searchOpSearchSQLOperationMap.get(searchOperation);
    }

    public static String getSQLOperationBySearchOperationStr(String searchOperationStr) {
        return searchOpStrSearchSQLOperationMap.get(searchOperationStr);
    }

    public static SearchOperation getSearchOpEnumByOperationStr(String searchOpStr) {
        return searchOperationStrSearchOpMap.get(searchOpStr);
    }

}
