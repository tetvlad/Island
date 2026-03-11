package ru.javarush.island.vtetsoshvili.model.predator;
import ru.javarush.island.vtetsoshvili.model.herbivore.*;
import java.util.Map;

public class Bear extends Predator {
    public Bear() {
        super(500.0, 80.0);
        setSpeed(2); setMaxPerCell(5);
        this.eatingProbabilities = Map.of(Boa.class, 80, Horse.class, 40, Deer.class, 80, Rabbit.class, 80,
                Mouse.class, 90, Goat.class, 70, Sheep.class, 70, Boar.class, 50,
                Buffalo.class, 20, Duck.class, 10);
    }
}
