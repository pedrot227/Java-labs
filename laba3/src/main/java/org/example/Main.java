package org.example;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static StoreManager manager = new StoreManager();
    private static Scanner sc = new Scanner(System.in);
    private static boolean isAdmin = false;

    public static void main(String[] args) {
        manager.load();
        System.out.print("Войти как админ? (1 - Да, 0 - Нет): ");
        isAdmin = "1".equals(sc.nextLine());

        while (true) {
            System.out.println("\n1. Показать всё | 2. Поиск | 3. Фильтр | 4. Сортировка | 5. Статистика | 6. Экспорт CSV");
            if (isAdmin) System.out.println("7. Добавить | 8. Удалить | 9. Изменить цену");
            System.out.println("0. Выход");

            String choice = sc.nextLine();
            try {
                switch (choice) {
                    case "1" -> manager.getAll().forEach(System.out::println);
                    case "2" -> {
                        System.out.print("Название: ");
                        manager.searchByName(sc.nextLine()).forEach(System.out::println);
                    }
                    case "3" -> {
                        System.out.print("Категория: "); String c = sc.nextLine();
                        System.out.print("Мин. цена: "); double min = Double.parseDouble(sc.nextLine());
                        System.out.print("Макс. цена: "); double max = Double.parseDouble(sc.nextLine());
                        manager.complexFilter(c, min, max).forEach(System.out::println);
                    }
                    case "4" -> {
                        System.out.print("Поле (1-имя, 2-цена): "); String f = sc.nextLine().equals("1") ? "name" : "price";
                        System.out.print("Порядок (1-ASC, 2-DESC): "); boolean asc = sc.nextLine().equals("1");
                        manager.getSorted(f, asc).forEach(System.out::println);
                    }
                    case "5" -> manager.showStats();
                    case "6" -> manager.exportToCSV();
                    case "7" -> { if(isAdmin) addProduct(); }
                    case "8" -> { if(isAdmin) removeProduct(); }
                    case "9" -> { if(isAdmin) editProduct(); }
                    case "0" -> { manager.save(); return; }
                }
            } catch (Exception e) {
                System.out.println("ОШИБКА: Проверьте корректность ввода (числа, даты)!");
                manager.log("ОШИБКА ВВОДА: " + e.getMessage());
            }
        }
    }

    private static void editProduct() {
        System.out.print("ID товара: "); String id = sc.nextLine();
        System.out.print("Новая цена: "); double p = Double.parseDouble(sc.nextLine());
        if (manager.updatePrice(id, p)) System.out.println("Обновлено.");
        else System.out.println("Не найден.");
    }

    private static void addProduct() {
        System.out.print("Тип (1-Еда, 2-Электро, 3-Одежда): ");
        int type = Integer.parseInt(sc.nextLine());
        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Название: "); String n = sc.nextLine();
        System.out.print("Цена: "); double p = Double.parseDouble(sc.nextLine());

        if (type == 1) {
            System.out.print("Дата (гггг-мм-дд): ");
            manager.add(new Food(id, n, p, LocalDate.parse(sc.nextLine())));
        } else if (type == 2) {
            System.out.print("Гарантия: ");
            manager.add(new Electronics(id, n, p, Integer.parseInt(sc.nextLine())));
        } else {
            System.out.print("Размер: ");
            manager.add(new Clothes(id, n, p, sc.nextLine()));
        }
    }

    private static void removeProduct() {
        System.out.print("ID для удаления: ");
        if (manager.remove(sc.nextLine())) System.out.println("Удалено.");
    }
}