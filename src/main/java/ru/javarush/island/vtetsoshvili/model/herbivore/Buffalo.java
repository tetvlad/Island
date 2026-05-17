package ru.javarush.island.vtetsoshvili.model.herbivore;
import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Buffalo extends Herbivore {
    public Buffalo() {
        super(700.0, 100.0); setSpeed(3); setMaxPerCell(10);
        this.eatingProbabilities = Map.of(Plant.class, 100);
    }
}
