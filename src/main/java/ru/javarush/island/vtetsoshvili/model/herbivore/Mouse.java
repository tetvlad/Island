package ru.javarush.island.vtetsoshvili.model.herbivore;
import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Mouse extends Herbivore {
    public Mouse() {
        super(0.05, 0.01); setSpeed(1); setMaxPerCell(500);
        this.eatingProbabilities = Map.of(Plant.class, 100, Caterpillar.class, 90);
    }
}
