package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class StoreManager {
    private List<Product> catalog = new ArrayList<>();
    private TreeMap<LocalDate, List<Food>> expiryMap = new TreeMap<>();
    private Map<String, Double> discountMap = new HashMap<>(); // ID -> % скидки
    private LinkedList<String> priceHistory = new LinkedList<>();

    private final String CSV_FILE = "inventory.csv";

    public void add(Product p) {
        catalog.add(p);
        if (p instanceof Food f) {
            expiryMap.computeIfAbsent(f.getExpiryDate(), k -> new ArrayList<>()).add(f);
        }
    }

    public boolean remove(String id) {
        return catalog.removeIf(p -> p.getId().equals(id));
    }

    // Учёт скидок через HashMap (Пункт 2 ТЗ)
    public void setDiscount(String id, double percent) {
        discountMap.put(id, percent);
    }

    // Поиск по ключевым атрибутам (Пункт 3 ТЗ)
    public List<Product> search(String query) {
        return catalog.stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()) ||
                        p.getId().equalsIgnoreCase(query))
                .collect(Collectors.toList());
    }

    // Сортировка (Пункт 5 ТЗ)
    public void sortByName() {
        catalog.sort(Comparator.comparing(Product::getName));
    }

    public void sortByPrice() {
        catalog.sort(Comparator.comparingDouble(Product::getPrice));
    }

    public void updatePrice(String id, double newPrice) {
        catalog.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresent(p -> {
                    String record = String.format("[%s] ID:%s | %s: %.2f -> %.2f",
                            LocalDateTime.now(), id, p.getName(), p.getPrice(), newPrice);
                    priceHistory.add(record);
                    p.setPrice(newPrice);
                });
    }
    // Вывод каталога с учетом скидок из HashMap
    public void showCatalogWithDiscounts() {
        if (catalog.isEmpty()) System.out.println("Каталог пуст.");
        catalog.forEach(p -> {
            double disc = discountMap.getOrDefault(p.getId(), 0.0);
            double finalPrice = p.getPrice() * (1 - disc / 100);
            System.out.print(p);
            if (disc > 0) System.out.printf(" [СКИДКА %.0f%% | Итого: %.2f]", disc, finalPrice);
            System.out.println();
        });
    }

    // Отчет для TreeMap
    public void showExpiryReport() {
        if (expiryMap.isEmpty()) {
            System.out.println("Данных о сроках годности нет.");
            return;
        }
        System.out.println("\n--- ГРУППИРОВКА ПО СРОКАМ (TreeMap) ---");
        expiryMap.forEach((date, list) -> {
            System.out.println("Дата " + date + ": " + list.size() + " шт.");
        });
    }
    public void showAdvancedStats() {
        if (catalog.isEmpty()) return;

        DoubleSummaryStatistics stats = catalog.stream()
                .collect(Collectors.summarizingDouble(Product::getPrice));

        System.out.println("\n--- СТАТИСТИКА ---");
        System.out.printf("Средняя цена: %.2f | Всего товаров: %d%n", stats.getAverage(), stats.getCount());

        // Топ-3 через PriorityQueue
        PriorityQueue<Product> top = new PriorityQueue<>(Comparator.comparingDouble(Product::getPrice).reversed());
        top.addAll(catalog);
        System.out.println("Топ-3 дорогих:");
        for (int i = 0; i < 3 && !top.isEmpty(); i++) System.out.println(top.poll());
    }

    // Сохранение в CSV (Пункт 8 ТЗ - текстовый формат)
    public void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (Product p : catalog) {
                pw.println(p.getId() + "," + p.getCategory() + "," + p.getName() + "," + p.getPrice());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void load() {
        File f = new File(CSV_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                // Упрощенная загрузка для примера (восстанавливаем как одежду или базовый продукт)
                catalog.add(new Clothes(d[0], d[2], Double.parseDouble(d[3]), "N/A"));
            }
        } catch (Exception e) { System.out.println("Ошибка загрузки."); }
    }


    public List<Product> getAll() { return catalog; }
    public List<String> getPriceJournal() { return priceHistory; }
}