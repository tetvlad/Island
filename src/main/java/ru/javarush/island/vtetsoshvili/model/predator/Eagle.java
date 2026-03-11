package ru.javarush.island.vtetsoshvili.model.predator;
import ru.javarush.island.vtetsoshvili.model.herbivore.*;
import java.util.Map;

public class Eagle extends Predator {
    public Eagle() {
        super(6.0, 1.0);
        setSpeed(3); setMaxPerCell(20);
        this.eatingProbabilities = Map.of(Fox.class, 10, Rabbit.class, 90, Mouse.class, 90, Duck.class, 80);
    }
}
