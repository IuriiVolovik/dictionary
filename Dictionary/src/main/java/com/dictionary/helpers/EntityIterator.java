package com.dictionary.helpers;

import com.dictionary.structure.Entity;

import java.util.Iterator;


public class EntityIterator implements Iterator<Entity> {

    int index = 0;
    EntityHelper helper;

    public EntityIterator(EntityHelper helper) {
        this.helper = helper;
    }

    public boolean hasNext() {
        if(index < helper.size())
            return true;
        return false;
    }

    public Entity next() {
        Entity entity = helper.get(index);
        index ++;
        return entity;
    }

    public void remove() {
        //to be implemented ...
    }

}
