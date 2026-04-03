package org.example;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
abstract class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String name;
    protected double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public abstract String getCategory();

    @Override
    public String toString() {
        return String.format("%-5s | %-12s | %-20s | %10.2f руб.", id, getCategory(), name, price);
    }
}