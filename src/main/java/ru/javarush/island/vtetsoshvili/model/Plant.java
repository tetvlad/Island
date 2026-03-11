package ru.javarush.island.vtetsoshvili.model;

/**
 * Класс Растение - основной источник пищи для травоядных животных на острове.
 * Не передвигается, не питается и имеет фиксированные характеристики при появлении.
 */
public class Plant extends Entity {
    public Plant() {
        setWeight(1.0);
        setMaxPerCell(200);
    }
}
