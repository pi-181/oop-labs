package com.demkom58.lab14.graph;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EntityManager {
    private final Set<Entity> entities = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Set<Updatable> updatables = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Set<Drawable> drawables = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void add(Entity entity) {
        entities.add(entity);

        if (entity instanceof Drawable)
            drawables.add((Drawable) entity);

        if (entity instanceof Updatable)
            updatables.add((Updatable) entity);
    }

    public void shrink() {
        final List<Entity> dead = entities.stream()
                .filter(Entity::isDead)
                .collect(Collectors.toList());

        entities.removeAll(dead);
        updatables.removeAll(dead);
        drawables.removeAll(dead);
    }

    public Collection<Entity> getEntities() {
        return entities;
    }

    public Collection<Drawable> getDrawables() {
        return drawables;
    }

    public Collection<Updatable> getUpdatables() {
        return updatables;
    }

    private static class Ordered<T> implements Comparable<Integer> {
        private final int layer;
        private final T obj;

        private Ordered(int layer, T obj) {
            this.layer = layer;
            this.obj = obj;
        }

        public int getLayer() {
            return layer;
        }

        public T getObj() {
            return obj;
        }

        @Override
        public int compareTo(@NotNull Integer o) {
            return Integer.compare(this.layer, o);
        }
    }
}