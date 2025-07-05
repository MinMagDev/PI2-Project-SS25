package LifeAndDeath;

import Particle.Entity;
import Particle.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface EntityManager<T extends Entity> {
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

    default T getEntityAt(Vector2D pos) {

        ArrayList<T> entities = new ArrayList<>(1);


        forEachEntity((entity) -> {
            Vector2D v = entity.getPosition();
            if(entity.getPosition().distanceTo(pos) <= entity.getSize()){
                entities.add(entity);
            }
        });

        return entities.isEmpty() ? null : entities.getFirst();
    }


}
