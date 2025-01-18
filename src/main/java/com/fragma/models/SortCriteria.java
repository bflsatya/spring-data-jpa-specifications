package com.fragma.models;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SortCriteria implements Serializable {
    private String uiColumnName;
    private String sortDir;
}
