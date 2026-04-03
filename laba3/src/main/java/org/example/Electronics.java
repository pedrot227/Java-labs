package org.example;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Electronics extends Product implements Discountable {
    private int warranty;

    public Electronics(String id, String name, double price, int warranty) {
        super(id, name, price);
        this.warranty = warranty;
    }

    public int getWarranty() { return warranty; }
    public void setWarranty(int w) { this.warranty = w; }
    @Override public String getCategory() { return "Электроника"; }
    @Override public void applyDiscount(double p) { this.price *= (1 - p / 100); }

    @Override
    public String toString() {
        return super.toString() + " | Гарантия: " + warranty + " мес.";
    }
}