package com.airports;

public class Airport {
    private final String[] columns;
    private final String rawLine;

    public Airport(String[] columns, String rawLine) {
        this.columns = columns;
        this.rawLine = rawLine;
    }

    public String getColumn(int index) {
        if (index < 1 || index > columns.length) {
            throw new IllegalArgumentException("Invalid column index: " + index);
        }
        return columns[index - 1];
    }

    public String getRawLine() {
        return rawLine;
    }
}