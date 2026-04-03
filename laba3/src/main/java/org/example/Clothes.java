package org.example;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Clothes extends Product {
    private String size;

    public Clothes(String id, String name, double price, String size) {
        super(id, name, price);
        this.size = size;
    }

    @Override public String getCategory() { return "Одежда"; }

    @Override
    public String toString() {
        return super.toString() + " | Размер: " + size;
    }
}