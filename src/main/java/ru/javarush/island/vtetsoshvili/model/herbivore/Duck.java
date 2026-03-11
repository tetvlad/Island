package ru.javarush.island.vtetsoshvili.model.herbivore;

import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Duck extends Herbivore {
    public Duck() {
        super(1.0, 0.15);
        setSpeed(4);
        setMaxPerCell(200);

        // Ест и растения, и гусениц
        this.eatingProbabilities = Map.of(
                Plant.class, 100,
                Caterpillar.class, 90
        );
    }
}
