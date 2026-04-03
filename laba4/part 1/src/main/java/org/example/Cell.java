package org.example;

import java.io.Serializable;

public class Cell {
    private final int x; // Координата X левого нижнего угла
    private final int y; // Координата Y левого нижнего угла
    private final double distToCenter;

    public Cell(int x, int y, double x0, double y0) {
        this.x = x;
        this.y = y;
        // Расстояние от центра клетки (x + 0.5, y + 0.5) до центра круга (x0, y0)
        this.distToCenter = Math.sqrt(Math.pow((x + 0.5) - x0, 2) + Math.pow((y + 0.5) - y0, 2));
    }

    public double getDistToCenter() {
        return distToCenter;
    }

    @Override
    public String toString() {
        return String.format("Клетка [%d; %d] | Расстояние до центра: %.4f", x, y, distToCenter);
    }
}