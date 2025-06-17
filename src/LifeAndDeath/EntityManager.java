package LifeAndDeath;

import java.util.function.Consumer;

public interface EntityManager<T> {
    void addEntity(T e);
    void removeEntity(T e);
    void forEachEntity(Consumer<T> action);

}
