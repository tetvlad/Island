package ru.javarush.island.vtetsoshvili.model;

/**
 * Базовый абстрактный класс для всех живых организмов на острове.
 * Содержит общие характеристики сущностей: базовый вес, лимит вместимости на одну клетку и статус жизни.
 */
public abstract class Entity {
    private double weight; // вес
    private int maxPerCell; //максимум на одной клетке
    private boolean isAlive = true;

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public int getMaxPerCell() { return maxPerCell; }
    public void setMaxPerCell(int maxPerCell) { this.maxPerCell = maxPerCell; }

    public boolean isAlive() { return isAlive; }
    public void die() { this.isAlive = false; }
}

