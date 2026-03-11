package ru.javarush.island.vtetsoshvili.model.predator;

import ru.javarush.island.vtetsoshvili.model.Animal;
import ru.javarush.island.vtetsoshvili.model.Location;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal {

    public Predator(double weight, double maxSatiety) {
        super(weight, maxSatiety);
    }

    @Override
    public void eat(Location location) {
        if (currentSatiety >= maxSatiety) return; // Уже сыт

        if (eatingProbabilities == null || eatingProbabilities.isEmpty()) return;

        // Ищем кого съесть)
        for (Animal prey : location.getAnimals()) {
            if (prey == this || !prey.isAlive()) continue;

            // Проверяем, есть ли животное в таблице
            Integer chance = eatingProbabilities.get(prey.getClass());
            if (chance != null) {
                int roll = ThreadLocalRandom.current().nextInt(100);
                if (roll < chance) {
                    prey.die(); // Съедаем животное
                    this.currentSatiety = Math.min(this.maxSatiety, this.currentSatiety + prey.getWeight());
                    return;
                }
            }
        }
    }
}
