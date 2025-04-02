package com.airports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportIndex {
    private final Map<String, List<Airport>> prefixMap = new HashMap<>();
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
        // Sort airports based on column type
        Comparator<Airport> comparator = isNumericColumn
                ? Comparator.comparing(a -> Double.parseDouble(a.getColumn(columnIndex)))
                : Comparator.comparing(a -> a.getColumn(columnIndex).toLowerCase());

        Collections.sort(airports, comparator);

        // Build prefix index
        for (Airport airport : airports) {
            String columnValue = airport.getColumn(columnIndex).toLowerCase();
            for (int i = 1; i <= columnValue.length(); i++) {
                String prefix = columnValue.substring(0, i);
                prefixMap.computeIfAbsent(prefix, k -> new ArrayList<>()).add(airport);
            }
        }
    }

    public List<Airport> search(String query) {
        String lowercaseQuery = query.toLowerCase();
        return prefixMap.getOrDefault(lowercaseQuery, Collections.emptyList());
    }

    public boolean isNumericColumn() {
        return isNumericColumn;
    }
}