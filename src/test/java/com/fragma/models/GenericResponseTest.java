package com.fragma.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericResponseTest  {
    @Test
    public void testGenericResponse() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        GenericResponse genericResponse = new GenericResponse();
        GenericResponse.ColumnMetadata columnMetadata1 = new GenericResponse.ColumnMetadata();
        columnMetadata1.setColumnName("EmailId");
        columnMetadata1.setDataType(GenericResponse.DataType.STRING);

        GenericResponse.ColumnMetadata columnMetadata2 = new GenericResponse.ColumnMetadata();
        columnMetadata2.setColumnName("Age");
        columnMetadata2.setDataType(GenericResponse.DataType.NUMBER);

        List<GenericResponse.ColumnMetadata> columnMetadataList = new ArrayList<>();
        columnMetadataList.add(columnMetadata1);
        columnMetadataList.add(columnMetadata2);

        genericResponse.setColumnMetadataList(columnMetadataList);

        List<Map<String, String>> dataRowList = new ArrayList<>();
        Map<String, String> dataRow1 = new HashMap<>();
        dataRow1.put("EmailId", "test@fragma.com");
        dataRow1.put("Age","2");

        Map<String, String> dataRow2 = new HashMap<>();
        dataRow2.put("EmailId", "test_2@fragma.com");
        dataRow2.put("Age","98");

        dataRowList.add(dataRow1);
        dataRowList.add(dataRow2);

        genericResponse.setDataRowList(dataRowList);
        genericResponse.setPage(1);
        genericResponse.setSize(10);
        genericResponse.setTotalRecords(2);

        String s = objectMapper.writeValueAsString(genericResponse);
        System.out.printf(s);

    }
}