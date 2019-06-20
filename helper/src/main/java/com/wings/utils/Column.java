package com.wings.utils;

/**
 * Purpose: Create Column for SQLite Database
 *
 * @author HetalD
 * Created On June 17,2019
 * Modified On June 19,2019
 */
public class Column {

    public String columnName;
    public String columnDataType;

    /**
     * Create column with column type
     *
     * @param columnName      Name of the column
     * @param columnDataTypes Type of the column
     */
    public Column(String columnName, String... columnDataTypes) {
        this.columnName = columnName.replaceAll(" ", "_");
        String finalDatatype = "";
        for (int i = 0; i < columnDataTypes.length; i++) {
            if (!columnDataTypes[i].startsWith(" ")) {
                columnDataTypes[i] = " " + columnDataTypes[i];
            }
            if (!columnDataTypes[i].endsWith(" ")) {
                columnDataTypes[i] = columnDataTypes[i] + " ";
            }
            finalDatatype += columnDataTypes[i].toUpperCase();
        }
        this.columnDataType = finalDatatype;
    }
}
