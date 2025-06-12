package org.example;

import java.util.HashMap;

public class SymbolTable {

    private static final HashMap<String, Integer> table = new HashMap<>();

    static {
        for (int i = 0; i <= 15; i++) {
            table.put("R" + i, i);
        }
        table.put("SP", 0);
        table.put("LCL", 1);
        table.put("ARG", 2);
        table.put("THIS", 3);
        table.put("THAT", 4);
        table.put("SCREEN", 16384);
        table.put("KBD", 24576);
    }

    public static void addEntry(String symbol, int address) {
        table.put(symbol, address);
    }

    public static boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    public static int getAddress(String symbol) {
        return table.get(symbol);
    }

    public static void clear() {
        table.clear();
    }

    public static void reset() {
        table.clear();
        for (int i = 0; i <= 15; i++) table.put("R" + i, i);
        table.put("SP", 0);
        table.put("LCL", 1);
        table.put("ARG", 2);
        table.put("THIS", 3);
        table.put("THAT", 4);
        table.put("SCREEN", 16384);
        table.put("KBD", 24576);
    }
}
