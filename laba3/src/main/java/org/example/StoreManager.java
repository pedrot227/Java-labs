package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class StoreManager {
    private List<Product> catalog = new ArrayList<>();
    private final String DATA_FILE = "store_inventory.dat";
    private final String LOG_FILE = "journal.log";
    private final String CSV_FILE = "export_catalog.csv";

    public void log(String message) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            pw.printf("[%s] %s%n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), message);
        } catch (IOException ignored) {}
    }

    public void add(Product p) { catalog.add(p); log("ДОБАВЛЕНИЕ: " + p.getName()); }

    public boolean remove(String id) {
        boolean removed = catalog.removeIf(p -> p.getId().equals(id));
        if (removed) log("УДАЛЕНИЕ: ID " + id);
        return removed;
    }

    // --- ПУНКТ 4: РЕДАКТИРОВАНИЕ ---
    public boolean updatePrice(String id, double newPrice) {
        for (Product p : catalog) {
            if (p.getId().equals(id)) {
                double oldPrice = p.getPrice();
                p.setPrice(newPrice);
                log(String.format("ИЗМЕНЕНИЕ ЦЕНЫ: %s (%.2f -> %.2f)", p.getName(), oldPrice, newPrice));
                return true;
            }
        }
        return false;
    }

    // --- ПУНКТ 8: СОРТИРОВКА (Имя/Цена + Направление) ---
    public List<Product> getSorted(String field, boolean asc) {
        Comparator<Product> comp = field.equalsIgnoreCase("name")
                ? Comparator.comparing(Product::getName)
                : Comparator.comparing(Product::getPrice);

        if (!asc) comp = comp.reversed();

        return catalog.stream().sorted(comp).collect(Collectors.toList());
    }

    // --- ПУНКТ 7: КОМБИНИРОВАННЫЙ ФИЛЬТР ---
    public List<Product> complexFilter(String category, double minP, double maxP) {
        return catalog.stream()
                .filter(p -> category.isEmpty() || p.getCategory().equalsIgnoreCase(category))
                .filter(p -> p.getPrice() >= minP && p.getPrice() <= maxP)
                .collect(Collectors.toList());
    }

    // --- ПУНКТ 6: ЭКСПОРТ В CSV ---
    public void exportToCSV() {
        try (PrintWriter writer = new PrintWriter(new File(CSV_FILE))) {
            writer.println("ID,Category,Name,Price");
            for (Product p : catalog) {
                writer.printf("%s,%s,%s,%.2f%n", p.getId(), p.getCategory(), p.getName(), p.getPrice());
            }
            log("ЭКСПОРТ в CSV выполнен");
            System.out.println("Данные успешно экспортированы в " + CSV_FILE);
        } catch (IOException e) {
            System.out.println("Ошибка экспорта: " + e.getMessage());
        }
    }

    // --- ПУНКТ 9: СТАТИСТИКА ПО КАТЕГОРИЯМ ---
    public void showStats() {
        if (catalog.isEmpty()) return;
        System.out.println("\n--- СТАТИСТИКА ---");
        Map<String, Long> counts = catalog.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        counts.forEach((cat, count) -> System.out.println(cat + ": " + count + " шт."));

        double avg = catalog.stream().mapToDouble(Product::getPrice).average().orElse(0);
        System.out.printf("Средняя цена по магазину: %.2f руб.%n", avg);
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(catalog);
            log("СОХРАНЕНИЕ .dat");
        } catch (Exception e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    public void load() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            catalog = (List<Product>) ois.readObject();
        } catch (Exception e) { System.out.println("Ошибка загрузки данных."); }
    }

    public List<Product> getAll() { return catalog; }


    public List<Product> searchByName(String query) {
        return catalog.stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}