package com.fragma.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {
    private List<ColumnMetadata> columnMetadataList;
    private List<Map<String, String>> dataRowList; /// data rows
	private int page;
    private int size;
    private long totalRecords;

    public static class ColumnMetadata {
        private String columnName;
        private DataType dataType;

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public void setDataType(DataType dataType) {
            this.dataType = dataType;
        }

        public String getColumnName() {
            return this.columnName;
        }

        public DataType getDataType() {
            return this.dataType;
        }
    }

    public enum DataType {
        NUMBER, FLOAT, STRING, BOOLEAN, DATE, DATE_TIME;
    }
}
