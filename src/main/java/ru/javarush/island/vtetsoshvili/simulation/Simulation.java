package ru.javarush.island.vtetsoshvili.simulation;

import ru.javarush.island.vtetsoshvili.model.Animal;
import ru.javarush.island.vtetsoshvili.model.Island;
import ru.javarush.island.vtetsoshvili.model.Location;
import ru.javarush.island.vtetsoshvili.model.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Simulation implements Runnable {
    private static final int THREADS = 4;

    private final Island island;
    private final ExecutorService workerPool;
    private volatile boolean running = true;
    private int day = 0;

    public Simulation(Island island) {
        this.island = island;
        this.workerPool = Executors.newFixedThreadPool(THREADS);
    }

    @Override
    public void run() {
        if (running) {
            tick();
        }
    }

    private void tick() {
        day++;
        System.out.println("======= День " + day + " =======");

        int width = island.getWidth();
        int height = island.getHeight();
        List<Callable<Void>> tasks = new ArrayList<>();

        // 1. Формируем задачи для параллельной обработки локаций (Питание, Размножение и смерть)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Location location = island.getLocation(x, y);
                tasks.add(() -> {
                    processLocation(location);
                    return null;
                });
            }
        }

        // Выполняем все задачи с помощью пула потоков
        try {
            List<Future<Void>> futures = workerPool.invokeAll(tasks);
            for (Future<Void> f : futures) {
                f.get(); // Ловим возможные исключения внутри потоков
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Такт прерван: " + e.getMessage());
        } catch (ExecutionException e) {
            System.err.println("Ошибка при выполнении задачи локации: " + e.getCause());
        }

        // 2. Перемещение
        moveAnimals();

        // 3. Рост растений
        growPlants();

        // 4. Вывод детальной статистики
        printStatistics();
    }

    private void processLocation(Location location) {
        location.getLock().lock();
        try {
            List<Animal> currentAnimals = new ArrayList<>(location.getAnimals());
            for (Animal animal : currentAnimals) {
                if (!animal.isAlive()) continue;

                animal.setCurrentSatiety(animal.getCurrentSatiety() - (animal.getMaxSatiety() * 0.1));
                if (animal.getCurrentSatiety() <= 0) {
                    animal.die();
                    continue;
                }

                animal.eat(location);
                animal.reproduce(location);
            }
            location.getAnimals().removeIf(a -> !a.isAlive());
        } finally {
            location.getLock().unlock();
        }
    }

    private void moveAnimals() {
        List<Runnable> moves = new ArrayList<>();

        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location loc = island.getLocation(x, y);
                List<Animal> animals = new ArrayList<>(loc.getAnimals());

                for (Animal animal : animals) {
                    int speed = animal.getSpeed();
                    if (speed > 0) {
                        int steps = ThreadLocalRandom.current().nextInt(speed + 1);
                        if (steps == 0) continue;

                        int newX = x;
                        int newY = y;

                        for (int i = 0; i < steps; i++) {
                            int dir = ThreadLocalRandom.current().nextInt(4);
                            switch(dir) {
                                case 0 -> newY = Math.max(0, newY - 1);
                                case 1 -> newX = Math.min(island.getWidth() - 1, newX + 1);
                                case 2 -> newY = Math.min(island.getHeight() - 1, newY + 1);
                                case 3 -> newX = Math.max(0, newX - 1);
                            }
                        }

                        if (newX != x || newY != y) {
                            final int finalX = newX;
                            final int finalY = newY;
                            moves.add(() -> {
                                loc.removeAnimal(animal);
                                island.getLocation(finalX, finalY).addAnimal(animal);
                            });
                        }
                    }
                }
            }
        }

        for (Runnable move : moves) {
            move.run();
        }
    }

    private void growPlants() {
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location loc = island.getLocation(x, y);
                if (loc.getPlants().size() < 200) loc.addPlant(new Plant());
            }
        }
    }

    public void printStatistics() {
        int totalAnimals = 0;
        int totalPlants = 0;

        Map<String, Integer> animalStats = new HashMap<>();

        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location loc = island.getLocation(x, y);
                for (Animal animal : loc.getAnimals()) {
                    totalAnimals++;
                    String type = animal.getClass().getSimpleName();
                    animalStats.put(type, animalStats.getOrDefault(type, 0) + 1);
                }
                totalPlants += loc.getPlants().size();
            }
        }

        // Формируем статистику
        StringBuilder statsBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : animalStats.entrySet()) {
            statsBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }


        log.info("Статистика: {}Растений = {}, Всего животных = {}", statsBuilder.toString(), totalPlants, totalAnimals);
        System.out.println("----------------------------------------------------------------------------------");

        if (totalAnimals == 0) {
            log.warn("Все умерли! Симуляция остановлена!");
            stop();
            System.exit(0);
        }
    }

    public void stop() {
        running = false;
        workerPool.shutdown();
    }
}
