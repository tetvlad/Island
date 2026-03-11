package ru.javarush.island.vtetsoshvili.model.herbivore;
import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Horse extends Herbivore {
    public Horse() {
        super(400.0, 60.0); setSpeed(4); setMaxPerCell(20);
        this.eatingProbabilities = Map.of(Plant.class, 100);
    }
}
