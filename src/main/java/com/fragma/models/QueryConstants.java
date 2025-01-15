package com.fragma.models;

import java.util.HashMap;
import java.util.Map;

public final class QueryConstants {
    private QueryConstants(){
    }

    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String PLACEHOLDER = "?";
    public static final String LIKE_WILDCARD = "%";

    public static final String ORDER_BY = "ORDER BY";

    public static final String COMMA = ",";

    public static final String EMAIL_MASTER = "EmailMaster";
    public static final String EMAIL_INSTANCE = "EmailInstance";


    public static final Map<String,String> ENTITY_ALIAS_MAP = new HashMap<>();

    static {
        ENTITY_ALIAS_MAP.put(EMAIL_MASTER, "em");
        ENTITY_ALIAS_MAP.put(EMAIL_INSTANCE, "ei");
    }


}
