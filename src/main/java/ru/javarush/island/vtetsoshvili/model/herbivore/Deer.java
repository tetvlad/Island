package ru.javarush.island.vtetsoshvili.model.herbivore;
import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Deer extends Herbivore {
    public Deer() {
        super(300.0, 50.0); setSpeed(4); setMaxPerCell(20);
        this.eatingProbabilities = Map.of(Plant.class, 100);
    }
}
