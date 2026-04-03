package org.example;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class Food extends Product implements Expirable, Discountable {
    private LocalDate expiryDate;

    public Food(String id, String name, double price, LocalDate expiryDate) {
        super(id, name, price);
        this.expiryDate = expiryDate;
    }



    @Override public boolean isExpired() { return LocalDate.now().isAfter(expiryDate); }
    @Override public LocalDate getExpiryDate() { return expiryDate; }
    @Override public String getCategory() { return "Продукты"; }
    @Override public void applyDiscount(double p) { this.price *= (1 - p / 100); }

    @Override
    public String toString() {
        return super.toString() + " | Срок: " + expiryDate + (isExpired() ? " [!] ПРОСРОЧЕНО" : "");
    }
}