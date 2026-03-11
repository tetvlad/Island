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

        // Ищем жертву в локации
        for (Animal prey : location.getAnimals()) {
            if (prey == this || !prey.isAlive()) continue;

            // Проверяем, есть ли жертва в нашем меню (по нашей таблице)
            Integer chance = eatingProbabilities.get(prey.getClass());
            if (chance != null) {
                int roll = ThreadLocalRandom.current().nextInt(100);
                if (roll < chance) {
                    prey.die(); // Убиваем жертву
                    // Увеличиваем сытость, но не больше максимума
                    this.currentSatiety = Math.min(this.maxSatiety, this.currentSatiety + prey.getWeight());
                    return; // Поел один раз за ход и хватит
                }
            }
        }
    }
}
