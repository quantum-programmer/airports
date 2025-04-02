package com.airports;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar airports-search.jar <column_index>");
            System.exit(1);
        }

        try {
            int columnIndex = Integer.parseInt(args[0]);
            if (columnIndex < 1) {
                throw new NumberFormatException();
            }

            AirportSearcher searcher = new AirportSearcher(
                    "src/main/resources/airports.dat",
                    columnIndex
            );
            searcher.startInteractiveSearch();
        } catch (NumberFormatException e) {
            System.err.println("Column index must be a positive integer");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}