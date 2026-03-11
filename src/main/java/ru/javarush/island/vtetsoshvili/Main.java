package ru.javarush.island.vtetsoshvili;

import lombok.extern.slf4j.Slf4j;
import ru.javarush.island.vtetsoshvili.simulation.Simulation;
import ru.javarush.island.vtetsoshvili.model.Island;
import ru.javarush.island.vtetsoshvili.model.Location;
import ru.javarush.island.vtetsoshvili.model.Plant;
import ru.javarush.island.vtetsoshvili.model.predator.*;
import ru.javarush.island.vtetsoshvili.model.herbivore.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Создаем остров 100 х 20 ...");
        Island island = new Island(20, 100);

        log.info("Сбрасываем животных и высаживаем растения на остров...");
        populateIsland(island);

        log.info("Запуск симуляции...");
        ScheduledExecutorService mainPool = Executors.newScheduledThreadPool(1);
        Simulation task = new Simulation(island);

        // Запускаем, период 1 секунда
        mainPool.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

    private static void populateIsland(Island island) {
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location loc = island.getLocation(x, y);

                // Трава
                for (int i = 0; i < 20; i++) {
                    loc.addPlant(new Plant());
                }

                // Хищники
                if (ThreadLocalRandom.current().nextInt(100) < 15) loc.addAnimal(new Wolf());
                if (ThreadLocalRandom.current().nextInt(100) < 10) loc.addAnimal(new Boa());
                if (ThreadLocalRandom.current().nextInt(100) < 15) loc.addAnimal(new Fox());
                if (ThreadLocalRandom.current().nextInt(100) < 5)  loc.addAnimal(new Bear());
                if (ThreadLocalRandom.current().nextInt(100) < 10) loc.addAnimal(new Eagle());

                // Травоядные
                if (ThreadLocalRandom.current().nextInt(100) < 10) loc.addAnimal(new Horse());
                if (ThreadLocalRandom.current().nextInt(100) < 10) loc.addAnimal(new Deer());
                if (ThreadLocalRandom.current().nextInt(100) < 30) loc.addAnimal(new Rabbit());
                if (ThreadLocalRandom.current().nextInt(100) < 40) loc.addAnimal(new Mouse());
                if (ThreadLocalRandom.current().nextInt(100) < 15) loc.addAnimal(new Goat());
                if (ThreadLocalRandom.current().nextInt(100) < 15) loc.addAnimal(new Sheep());
                if (ThreadLocalRandom.current().nextInt(100) < 10) loc.addAnimal(new Boar());
                if (ThreadLocalRandom.current().nextInt(100) < 5)  loc.addAnimal(new Buffalo());
                if (ThreadLocalRandom.current().nextInt(100) < 20) loc.addAnimal(new Duck());

                // Гусеницы
                for(int i = 0; i < 5; i++) {
                    if (ThreadLocalRandom.current().nextInt(100) < 50) loc.addAnimal(new Caterpillar());
                }
            }
        }
    }
}
