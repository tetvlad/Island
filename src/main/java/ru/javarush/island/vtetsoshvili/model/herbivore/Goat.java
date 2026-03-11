package ru.javarush.island.vtetsoshvili.model.herbivore;
import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Goat extends Herbivore {
    public Goat() {
        super(60.0, 10.0); setSpeed(3); setMaxPerCell(140);
        this.eatingProbabilities = Map.of(Plant.class, 100);
    }
}
