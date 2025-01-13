package com.fragma.models;

import java.util.HashMap;
import java.util.Map;

public enum EntityAlias {
    EMAIL_INSTANCE("ei","ei"), EMAIL_MASTER("em","em");



    String entityName;
    String alias;

    EntityAlias(String entityName, String alias) {
        this.entityName = entityName;
        this.alias = alias;
    }

    private String getEntityName() {
        return this.entityName;
    }

    private String getAlias() {
        return this.alias;
    }

    private static Map<String, String> entityNameToAliasMap = new HashMap();

    static {
        for(EntityAlias entityAlias : EntityAlias.values()) {
            entityNameToAliasMap.put(entityAlias.getEntityName(), entityAlias.getAlias());
        }
    }

    public static String getEntityAliasByName(String entityName) {
        return entityNameToAliasMap.get(entityName);
    }







}
