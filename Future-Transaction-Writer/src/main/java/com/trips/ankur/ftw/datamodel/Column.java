package com.trips.ankur.ftw.datamodel;

import lombok.Data;
import lombok.RequiredArgsConstructor;


/**
 * Defining the columns class.
 * @author tripaank
 *
 */

@Data
@RequiredArgsConstructor
public class Column {
    private final String columnName;
    private final int startIndex;
    private final int endIndex;
    private final String type;
    private final String format;
}