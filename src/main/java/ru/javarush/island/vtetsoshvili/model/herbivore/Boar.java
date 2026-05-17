package ru.javarush.island.vtetsoshvili.model.herbivore;
import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Boar extends Herbivore {
    public Boar() {
        super(400.0, 50.0); setSpeed(2); setMaxPerCell(50);
        this.eatingProbabilities = Map.of(Plant.class, 100, Mouse.class, 50, Caterpillar.class, 90);
    }
}
