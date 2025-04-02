package com.airports;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AirportIndex {
    private final TrieNode trieRoot = new TrieNode();
    private final int columnIndex;
    private final boolean isNumericColumn;

    public AirportIndex(List<Airport> airports, int columnIndex) {
        this.columnIndex = columnIndex;
        this.isNumericColumn = determineIfNumeric(airports, columnIndex);
        buildIndex(airports);
    }

    private boolean determineIfNumeric(List<Airport> airports, int columnIndex) {
        if (airports.isEmpty()) return false;
        try {
            String value = airports.get(0).getColumn(columnIndex);
            if (value.startsWith("\"") && value.endsWith("\"")) {
                return false;
            }
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void buildIndex(List<Airport> airports) {
        Comparator<Airport> comparator = isNumericColumn
                ? Comparator.comparing(a -> Double.parseDouble(a.getColumn(columnIndex)))
                : Comparator.comparing(a -> a.getColumn(columnIndex).toLowerCase());

        Collections.sort(airports, comparator);

        for (Airport airport : airports) {
            String columnValue = airport.getColumn(columnIndex).toLowerCase();
            trieRoot.insert(columnValue, airport);
        }
    }

    public List<Airport> search(String query) {
        return trieRoot.search(query.toLowerCase());
    }

    public boolean isNumericColumn() {
        return isNumericColumn;
    }
}