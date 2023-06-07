package com.lab3;

import com.lab3.Animals.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AnimalData {
    ObservableList<Animal> animals = FXCollections.observableArrayList();

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public ObservableList<Animal> getAnimals() {
        return animals;
    }
}
