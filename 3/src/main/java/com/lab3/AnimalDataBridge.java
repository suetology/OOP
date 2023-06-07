package com.lab3;

public class AnimalDataBridge {
    private static AnimalDataBridge instance;
    public static AnimalDataBridge getInstance() {
        if (instance == null)
            instance = new AnimalDataBridge();
        return instance;
    }

    private AnimalData animalData;

    public void setAnimalData(AnimalData animalData) {
        this.animalData = animalData;
    }
    public AnimalData getAnimalData() {
        return animalData;
    }
}
