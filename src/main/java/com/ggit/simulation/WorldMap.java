package com.ggit.simulation;

import java.util.*;

public class WorldMap extends AbstractWorldMap {
    private int dayNumber = 1;
    private Map<Vector2D, Plant> plants;
    private AnimalsMapping animals;
    private static final int noOfPlants = 50;
    private static final int noOfAnimals = 15;
    private static final int animalEnergy = 50;
    private static final int plantEnergy = 10;
    private static final Random random = new Random();

    public WorldMap(int width, int height) {
        super(width, height);
        plants = new HashMap<>();
        for (int i = 0; i < noOfPlants; i++) addPlant();
        animals = new AnimalsMapping();
    }

    private void addPlant() {
        if (width * height == plants.size()) return;

        Vector2D position = getRandomPosition();
        while (isOccupiedByPlant(position)) position = getRandomPosition();
        plants.put(position, new Plant(position));
    }

    private boolean isOccupiedByPlant(Vector2D position) {
        return plants.containsKey(position);
    }

    private Vector2D getRandomPosition() {
        return new Vector2D(random.nextInt(width), random.nextInt(height));
    }

    @Override
    public void run() {
        animals.moveAnimals();
    }

    @Override
    public void eat() {
        animals.list.forEach(animal -> {
            if (isOccupiedByPlant(animal.getPosition())) {
                System.out.println(String.format("Zwierzę %s zjadło roślinę", animal.getId()));
                animal.withChangedEnergy(animal.getEnergy() + plantEnergy);
                plants.remove(animal.getPosition());
                addPlant();
            }
        });
    }

    @Override
    public void startDay() {
        System.out.println("Nowy dzień numer " + dayNumber);
    }

    private class AnimalsMapping {
        List<Animal> list;
        Map<Vector2D, List<Animal>> mapping;

        AnimalsMapping() {
            list = new LinkedList<>();
            mapping = new HashMap<>();
            for (int i = 0; i < noOfAnimals; i++) addAnimal();
        }

        void addAnimal() {
            Animal animal = new Animal(getRandomPosition(), animalEnergy);
            placeAnimalOnMap(animal);
            list.add(animal);
        }

        void placeAnimalOnMap(Animal animal) {
            mapping.computeIfAbsent(animal.getPosition(), pos -> new LinkedList<>()).add(animal);
        }

        void moveAnimals() {
            mapping.clear();
            MapDirection[] directions = MapDirection.values();
            list.forEach(animal -> {
                animal.move(directions[random.nextInt(directions.length)]);
                placeAnimalOnMap(animal);
            });
        }
    }
}
