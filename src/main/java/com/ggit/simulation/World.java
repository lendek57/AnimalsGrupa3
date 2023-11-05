package com.ggit.simulation;

import java.util.Random;

public class World {
    public static void main(String[] args) {
        Random random = new Random();
        Animal animal = new Animal(new Vector2D(random.nextInt(100), random.nextInt(100)));
        System.out.println("Położenie zwierzęcia to: " + animal.getPosition());

        for (MapDirection direction : MapDirection.values()) {
            animal.move(direction);
        }
    }
}
