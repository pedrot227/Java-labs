package org.example;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static StoreManager manager = new StoreManager();
    private static Scanner sc = new Scanner(System.in);
    private static boolean isAdmin = false;

    public static void main(String[] args) {
        manager.load();
        System.out.print("Вход (1-Админ, 2-Гость): ");
        isAdmin = "1".equals(sc.nextLine());

        while (true) {
            System.out.println("\n--- МАГАЗИН ---");
            System.out.println("1. Список | 2. Поиск | 3. Сортировать по цене | 4. Статистика | 5. Журнал цен");
            System.out.println("6. Отчет по срокам (TreeMap)"); // Добавили для 100% ТЗ
            if (isAdmin) System.out.println("7. Добавить | 8. Изменить цену | 9. Удалить | 10. Установить скидку");
            System.out.println("0. Выход");

            String choice = sc.nextLine();
            try {
                switch (choice) {
                    case "1" -> manager.showCatalogWithDiscounts(); // Обновленный метод
                    case "2" -> {
                        System.out.print("Введите название для поиска: ");
                        manager.search(sc.nextLine()).forEach(System.out::println);
                    }
                    case "3" -> {
                        manager.sortByPrice();
                        System.out.println("Отсортировано по цене!");
                    }
                    case "4" -> manager.showAdvancedStats();
                    case "5" -> manager.getPriceJournal().forEach(System.out::println);
                    case "6" -> manager.showExpiryReport(); // Наш TreeMap
                    case "7" -> { if(isAdmin) addProduct(); }
                    case "8" -> { if(isAdmin) editPrice(); }
                    case "9" -> {
                        if(isAdmin) {
                            System.out.print("ID для удаления: ");
                            if(manager.remove(sc.nextLine())) System.out.println("Удалено.");
                            else System.out.println("Объект не найден.");
                        }
                    }
                    case "10" -> {
                        if(isAdmin) {
                            System.out.print("ID товара: "); String id = sc.nextLine();
                            System.out.print("% скидки: "); double d = Double.parseDouble(sc.nextLine());
                            manager.setDiscount(id, d);
                        }
                    }
                    case "0" -> { manager.save(); return; }
                    default -> System.out.println("Неверный пункт.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: проверьте формат ввода.");
            }
        }
    }

    private static void editPrice() {
        System.out.print("ID товара: "); String id = sc.nextLine();
        System.out.print("Новая цена: "); double p = Double.parseDouble(sc.nextLine());
        manager.updatePrice(id, p);
    }

    private static void addProduct() {
        System.out.print("Тип (1-Еда, 2-Электро, 3-Одежда): ");
        int t = Integer.parseInt(sc.nextLine());
        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Название: "); String n = sc.nextLine();
        System.out.print("Цена: "); double p = Double.parseDouble(sc.nextLine());

        switch (t) {
            case 1 -> {
                System.out.print("Срок (гггг-мм-дд): ");
                manager.add(new Food(id, n, p, LocalDate.parse(sc.nextLine())));
            }
            case 2 -> manager.add(new Electronics(id, n, p, 24));
            case 3 -> {
                System.out.print("Размер: ");
                manager.add(new Clothes(id, n, p, sc.nextLine()));
            }
        }
    }
}