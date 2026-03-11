package ru.javarush.island.vtetsoshvili.model.herbivore;
import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Sheep extends Herbivore {
    public Sheep() {
        super(70.0, 15.0); setSpeed(3); setMaxPerCell(140);
        this.eatingProbabilities = Map.of(Plant.class, 100);
    }
}
