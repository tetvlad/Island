package ru.javarush.island.vtetsoshvili.model.herbivore;

import ru.javarush.island.vtetsoshvili.model.Animal;
import ru.javarush.island.vtetsoshvili.model.Location;
import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {

    public Herbivore(double weight, double maxSatiety) {
        super(weight, maxSatiety);
    }

    @Override
    public void eat(Location location) {
        if (currentSatiety >= maxSatiety) return;

        if (eatingProbabilities == null || eatingProbabilities.isEmpty()) return;

        // Травоядное. Сначала есть толкьо траву
        Integer plantChance = eatingProbabilities.get(Plant.class);
        if (plantChance != null && !location.getPlants().isEmpty()) {
            int roll = ThreadLocalRandom.current().nextInt(100);
            if (roll < plantChance) {
                Plant plant = location.removePlant(); // кушаем растение
                if (plant != null) {
                    this.currentSatiety = Math.min(this.maxSatiety, this.currentSatiety + plant.getWeight());
                    return;
                }
            }
        }

        // Если растений нет (или не съел), проверяем, может ли он съесть животное (как Утка ест Гусеницу)
        for (Animal prey : location.getAnimals()) {
            if (prey == this || !prey.isAlive()) continue;

            Integer chance = eatingProbabilities.get(prey.getClass());
            if (chance != null) {
                int roll = ThreadLocalRandom.current().nextInt(100);
                if (roll < chance) {
                    prey.die();
                    this.currentSatiety = Math.min(this.maxSatiety, this.currentSatiety + prey.getWeight());
                    return;
                }
            }
        }
    }
}
