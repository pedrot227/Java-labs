package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите координаты центра круга (x0 y0): ");
            double x0 = scanner.nextDouble();
            double y0 = scanner.nextDouble();

            System.out.print("Введите радиус R: ");
            double r = scanner.nextDouble();

            // PriorityQueue с компаратором по возрастанию расстояния
            PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingDouble(Cell::getDistToCenter));

            // Область поиска: квадрат вокруг круга
            int startX = (int) Math.floor(x0 - r);
            int endX = (int) Math.ceil(x0 + r);
            int startY = (int) Math.floor(y0 - r);
            int endY = (int) Math.ceil(y0 + r);

            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    // Клетка (x,y) — это квадрат от x до x+1 и от y до y+1
                    if (isCellFullyInside(x, y, x0, y0, r)) {
                        pq.add(new Cell(x, y, x0, y0));
                    }
                }
            }

            // Вывод в файл
            saveToFile(pq);

        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введите числовые значения.");
        }
    }

    private static boolean isCellFullyInside(int x, int y, double x0, double y0, double r) {
        // Проверяем самую удаленную точку клетки от центра круга.
        // Для каждой клетки это будет один из ее 4-х углов.
        return isPointInside(x, y, x0, y0, r) &&
                isPointInside(x + 1, y, x0, y0, r) &&
                isPointInside(x, y + 1, x0, y0, r) &&
                isPointInside(x + 1, y + 1, x0, y0, r);
    }

    private static boolean isPointInside(double px, double py, double x0, double y0, double r) {
        // Формула: (x-x0)^2 + (y-y0)^2 <= R^2
        return Math.pow(px - x0, 2) + Math.pow(py - y0, 2) <= Math.pow(r, 2);
    }

    private static void saveToFile(PriorityQueue<Cell> pq) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("cells_in_circle.txt"))) {
            writer.println("Список клеток внутри круга (отсортировано по удаленности от центра):");
            writer.println("------------------------------------------------------------------");

            while (!pq.isEmpty()) {
                // poll() гарантированно достает элементы в порядке приоритета
                writer.println(pq.poll());
            }
            System.out.println("Результаты успешно записаны в файл cells_in_circle.txt");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}