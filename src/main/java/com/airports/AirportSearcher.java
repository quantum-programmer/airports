package com.airports;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AirportSearcher {
    private final AirportIndex index;
    private final int columnIndex;

    public AirportSearcher(String filePath, int columnIndex) throws IOException {
        this.columnIndex = columnIndex;
        List<Airport> airports = AirportParser.parse(filePath);
        this.index = new AirportIndex(airports, columnIndex);
    }

    public void startInteractiveSearch() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите текст для поиска (или '!quit' для выхода):");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("!quit")) {
                break;
            }

            long startTime = System.currentTimeMillis();
            List<Airport> results = index.search(input);
            long duration = System.currentTimeMillis() - startTime;

            System.out.println("Найдено результатов: " + results.size());
            System.out.println("Время поиска: " + duration + " мс");

            for (Airport airport : results) {
                System.out.println(airport.getColumn(columnIndex) + " [" + airport.getRawLine() + "]");
            }
        }

        scanner.close();
    }
}