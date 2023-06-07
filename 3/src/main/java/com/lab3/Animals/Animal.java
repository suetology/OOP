package com.lab3.Animals;

public class Animal {
    public enum Type { CAT, DOG, LION, SPIDER };

    protected String name;
    protected int age;
    protected Type type;

    public Animal(String name, int age, Type type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type.toString();
    }
}
