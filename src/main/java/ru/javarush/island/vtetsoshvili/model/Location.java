package ru.javarush.island.vtetsoshvili.model;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Класс Локация содержит списки животных и растений.
 * Метод removePlant удаляет последнее растение (без синхронизации в версии 1.0)
 */
public class Location {

    @Getter
    private final List<Animal> animals = new CopyOnWriteArrayList<>(); // многопоточность (1)
    private final List<Plant> plants = new CopyOnWriteArrayList<>();

    public void addAnimal(Animal animal) {
        animals.add(animal);
        animal.setCurrentLocation(this);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public Plant removePlant() {
        synchronized (plants) { // многопоточность (2)
            if (!plants.isEmpty()) {
                return plants.remove(plants.size() - 1);
            }
            return null;
        }
    }

    public List<Plant> getPlants() {
        return plants;
    }

    private final Lock lock = new ReentrantLock();

    public Lock getLock() {
        return lock;
    }

}
