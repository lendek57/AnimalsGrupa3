package com.ggit.simulation;

public class Animal {
    private Vector2D position;
    private int id;
    private int energy;
    private int age;
    private static int counter = 0;

    public Animal(Vector2D position, int energy) {
        this.position = position;
        this.energy = energy;
        age = 1;
        id = counter++;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public Animal dayOlder() {
        age++;
        return this;
    }

    public Animal withChangedEnergy(int newEnergy) {
        energy = newEnergy;
        return this;
    }

    public int getId() {
        return id;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void move(MapDirection direction) {
        position = pbc(position.add(direction.getUnitVector()));
        System.out.println("Nowa pozycja zwierzęcia: " + position);
    }

    private Vector2D pbc(Vector2D position) {
        int width = Simulation.getWidth();
        int height = Simulation.getHeight();
        if (position.getX() < 0) return position.add(new Vector2D(width, 0));
        if (position.getX() >= width) return position.subtrack(new Vector2D(width, 0));
        if (position.getY() < 0) return position.add(new Vector2D(0, height));
        if (position.getY() >= height) return position.subtrack(new Vector2D(0, height));
        return position;
    }
}
