package com.dictionary.helpers;

import com.dictionary.structure.Entity;

import java.util.ArrayList;
import java.util.Iterator;

abstract public class EntityHelper implements Iterable<Entity> {

    protected ArrayList<Entity> entities;

    protected static int size = 0;

    private static int current;

    abstract public boolean add(String title, String description, String image) throws Exception;

    abstract public boolean remove(int index) throws Exception;

    abstract public void disconnect();

    public Entity get(int i) {
        return entities.get(i);
    }

    public static int size() {
        return size;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrent() {
        return current;
    }

    public Iterator<Entity> iterator() {
        return new EntityIterator(this);
    }

}
