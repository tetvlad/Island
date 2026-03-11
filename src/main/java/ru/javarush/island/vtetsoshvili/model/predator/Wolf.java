package ru.javarush.island.vtetsoshvili.model.predator;

import ru.javarush.island.vtetsoshvili.model.herbivore.*;
import java.util.Map;

public class Wolf extends Predator {
    public Wolf() {
        super(50.0, 8.0);
        setSpeed(3);
        setMaxPerCell(30);

        // Кого ест Волк исходя из таблицы
        this.eatingProbabilities = Map.of(
                Horse.class, 10,
                Deer.class, 15,
                Rabbit.class, 60,
                Mouse.class, 80,
                Goat.class, 60,
                Sheep.class, 70,
                Boar.class, 15,
                Buffalo.class, 10,
                Duck.class, 40
        );
    }
}
