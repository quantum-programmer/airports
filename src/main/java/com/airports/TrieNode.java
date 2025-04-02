package com.airports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
    private final Map<Character, TrieNode> children = new HashMap<>();
    private final List<Airport> airports = new ArrayList<>();

    public void insert(String word, Airport airport) {
        TrieNode current = this;
        for (char c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
            current.airports.add(airport); // Добавляем аэропорт во все узлы пути
        }
    }

    public List<Airport> search(String prefix) {
        TrieNode current = this;
        for (char c : prefix.toCharArray()) {
            current = current.children.get(c);
            if (current == null) {
                return List.of();
            }
        }
        return current.airports;
    }
}