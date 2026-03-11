package ru.javarush.island.vtetsoshvili.model.predator;
import ru.javarush.island.vtetsoshvili.model.herbivore.*;
import java.util.Map;

public class Fox extends Predator {
    public Fox() {
        super(8.0, 2.0);
        setSpeed(2); setMaxPerCell(30);
        this.eatingProbabilities = Map.of(Rabbit.class, 70, Mouse.class, 90, Duck.class, 60, Caterpillar.class, 40);
    }
}
