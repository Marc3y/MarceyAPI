package de.marcey.marceyapi.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPicker<T> {
    private List<T> objects;

    public RandomPicker(T... objs) {
        objects = new ArrayList<>();
        for (T obj : objs) {
            objects.add(obj);
        }
    }

    public RandomPicker(List<T> objs) {
        objects = new ArrayList<>(objs);
    }

    public List<T> getObjects() {
        return objects;
    }

    public T getRandomObject() {
        Random rnd = new Random();
        int value = rnd.nextInt(objects.size());
        return objects.get(value);
    }

    public T getRandomObject(T... withoutObjects) {
        List<T> objectsWithout = new ArrayList<>(objects);
        for (T obj : withoutObjects) {
            objectsWithout.remove(obj);
        }
        if (objectsWithout.isEmpty()) return null;
        Random rnd = new Random();
        int value = rnd.nextInt(objectsWithout.size());
        return objectsWithout.get(value);
    }

    public T getRandomObject(List<T> withoutObjects) {
        List<T> objectsWithout = new ArrayList<>(objects);
        objectsWithout.removeAll(withoutObjects);
        if (objectsWithout.isEmpty()) return null;
        Random rnd = new Random();
        int value = rnd.nextInt(objectsWithout.size());
        return objectsWithout.get(value);
    }
}