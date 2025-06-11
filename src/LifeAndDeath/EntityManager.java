package LifeAndDeath;

public interface EntityManager<T> {
    void addEntity(T e);
    void removeEntity(T e);
}
