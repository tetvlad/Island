package ru.javarush.island.vtetsoshvili.model.predator;
import ru.javarush.island.vtetsoshvili.model.herbivore.*;
import java.util.Map;

public class Boa extends Predator {
    public Boa() {
        super(15.0, 3.0);
        setSpeed(1); setMaxPerCell(30);
        this.eatingProbabilities = Map.of(Fox.class, 15, Rabbit.class, 20, Mouse.class, 40, Duck.class, 10);
    }
}
