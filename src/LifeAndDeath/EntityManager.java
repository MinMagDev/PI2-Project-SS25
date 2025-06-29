package LifeAndDeath;

import java.util.List;
import java.util.function.Consumer;

public interface EntityManager<T> {
    /**
     * Adds an Entity to the List of entities, i.e. particles
     * @param e The Entity to add
     */
    void addEntity(T e);

    /**
     * Removes the Entity from the collection of entities
     * @param e the Entity to remove
     */
    void removeEntity(T e);

    /**
     * Removes all given entites
     * @param es List of Entities
     */
    void massRemoveEntities(List<T> es);
    void forEachEntity(Consumer<T> action);

}
