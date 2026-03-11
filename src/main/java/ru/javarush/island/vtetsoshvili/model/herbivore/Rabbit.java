package ru.javarush.island.vtetsoshvili.model.herbivore;

import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(2.0, 0.45); // Вес: 2, Макс еды: 0.45
        setSpeed(2);
        setMaxPerCell(150);

        // Ест только растения
        this.eatingProbabilities = Map.of(
                Plant.class, 100
        );
    }
}
