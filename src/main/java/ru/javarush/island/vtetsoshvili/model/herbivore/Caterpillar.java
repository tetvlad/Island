package ru.javarush.island.vtetsoshvili.model.herbivore;
import ru.javarush.island.vtetsoshvili.model.Plant;
import java.util.Map;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(0.01, 0.0); // Есть ей вроде как не нужно
        setSpeed(0); setMaxPerCell(1000);
        this.eatingProbabilities = Map.of(Plant.class, 100);
    }
}
