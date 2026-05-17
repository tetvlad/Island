package ru.javarush.island.vtetsoshvili.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс Животное - базовый абстрактный класс для всех хищников и травоядных.
 * Определяет общую механику жизненного цикла: перемещение, питание (с учетом вероятностей) и размножение.
 * Сохраняет текущую сытость и местоположение для работы в многопоточной среде.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class Animal extends Entity {
    private static final int CHANCE_OF_REPRODUCTION = 30; // шанс размножения

    protected double maxSatiety; // максимальная сытость
    protected double currentSatiety; // текущая сытость
    protected int speed = 1; // скорость перемещения

    // volatile гарантирует что все потоки увидят актуальное значение
    protected volatile Location currentLocation; // текущее местонахождение животного

    // Карта вероятности поедания других существ (животных и растений)
    protected Map<Class<? extends Entity>, Integer> eatingProbabilities;

    public Animal(double weight, double maxSatiety) {
        setWeight(weight); // Устанавливаем вес через метод родителя Entity
        this.maxSatiety = maxSatiety;
        this.currentSatiety = maxSatiety;
    }

    // метод eat, реализация будет у хищников и травоядных
    public abstract void eat(Location location);

    public void move(Island island, int currentX, int currentY) {
        if (!isAlive()) {
            return;
        }
        if (currentLocation == null) {
            System.err.println("Животное " + this.getClass().getSimpleName() + " не имеет локации. Перемещение невозможно!");
            return;
        }

        int direction = ThreadLocalRandom.current().nextInt(4);
        int newX = currentX;
        int newY = currentY;

        // 0..3
        switch (direction) {
            case 0: newY = Math.max(0, currentY - 1); break; // вверх Y
            case 1: newX = Math.min(island.getWidth() - 1, currentX + 1); break; // вправо X
            case 2: newY = Math.min(island.getHeight() - 1, currentY + 1); break; // вниз Y
            case 3: newX = Math.max(0, currentX - 1); break; // влево X
        }
    }

    public void reproduce(Location location) {
        if (!isAlive()) {
            return;
        }

        // 1. Быстрый подсчет одного типа животных
        int sameSpeciesCount = 0;
        for (Animal a : location.getAnimals()) {
            if (a.getClass() == this.getClass() && a.isAlive()) {
                sameSpeciesCount++;
            }
        }

        // 2. Если лимит животных в клетке достигнут — не множатся
        if (sameSpeciesCount >= getMaxPerCell()) {
            return;
        }

        // 3. Размножение
        if (sameSpeciesCount >= 2 && ThreadLocalRandom.current().nextInt(100) < CHANCE_OF_REPRODUCTION) {
            try {
                Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                // Устанавливаем сытость детенышу
                double babySatiety = baby.getMaxSatiety() > 0 ? baby.getMaxSatiety() / 2 : 0;
                baby.setCurrentSatiety(babySatiety);

                location.addAnimal(baby);
            } catch (Exception e) {
                System.err.println("Ошибка при создании животного: " + e.getMessage());
            }
        }
    }
}
