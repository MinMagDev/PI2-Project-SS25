package World;

import Canvas.*;
import LifeAndDeath.EntityManager;
import LifeAndDeath.ReproducingParticle;
import Particle.Particle;
import Particle.Vector2D;
import Species.SpeciesParticle;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
  * A drawable class that simulates the very crude "physics" involved
 * and also manages the reproduction and life cycle aspects of the simulation
 */

public class World<T extends Particle & ReproducingParticle> implements Drawable, EntityManager<T> {
    /**
     * the worlds width in px
     */
    private final int width;
    /**
     * the worlds height in px
     */
    private final int height;

    /**
     * worlds default width in px
     */
    public static final int DEFAULT_WIDTH = 1000;
    /**
     * worlds default height in px
     */
    public static final int DEFAULT_HEIGHT = 1000;
    /**
     * the max number of entities managed by this world instance
     */
    public static final int MAX_ENTITY_COUNT = 1000;

    /**
     * the entities managed by this world
     */
    ArrayList<T> entities;

    private final Drawable renderer;

    public World(int width, int height, ArrayList<T> entities, Drawable renderer) {
        this.width = width;
        this.height = height;
        this.entities = entities;
        this.renderer = renderer;
    }

    @Override
    public void draw(Graphics g) {
        // configure background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0,width,height);
        g.setColor(Color.black);
        // draw the outlines of the world
        g.drawLine(0, 0, width, 0);
        g.drawLine(0, 0, 0, height);
        g.drawLine(0, height, width, height);
        g.drawLine(width, 0, width, height);
        renderer.draw(g);
    }

    @Override
    public void update() {
        updateParticles();
        simulateCollisions();
        renderer.update();
    }

    /**
     * Aktualisiert den Zustand aller {@link SpeciesParticle}-Instanzen in der {@code colliders}-Liste.
     * <p>
     * Entfernt tote Partikel, verarbeitet reproduzierende Partikel (als Mütter) und erzeugt neue Kind-Partikel.
     * Neue Partikel werden zur Liste hinzugefügt (sofern {@code MAX_ENTITY_COUNT} nicht überschritten wird).
     * Tote Partikel werden entfernt und alle Änderungen optional an den zugehörigen Renderer übergeben.
     * <p>
     * (D.R.E.C.K)
     */
    private void updateParticles() {
        List<Runnable> actions = new LinkedList<>();
        for(var particle : entities) {
            if(!particle.isAlive()){
                actions.add(() -> this.removeEntity(particle));
                continue;
            }
            if(particle.isReproducing()){
                T child = particle.newChild();
                particle.setReproducing(false);
                if(child != null){
                    child.updateValues();
                    actions.add(() -> {
                        if(entities.size() < MAX_ENTITY_COUNT) {
                            this.addEntity(child);
                        }
                    });
                }
            }
        }

        for(var action : actions) {
            action.run();
        }

    }



    /**
     * adds a collision force to a collider upon collision with the world border
     * @param collider the collider
     */

    private void addBorderCollisionForce(Collider collider) {
        Vector2D force = new Vector2D();

        if(collider.getPosition().getX() + collider.getRadius() > width){
            force.add(new Vector2D(-1, 0));
            collider.setX(width - collider.getRadius());
        }
        if(collider.getPosition().getY() + collider.getRadius() > height){
            force.add(new Vector2D(0, -1));
            collider.setY(height - collider.getRadius());
        }
        if(collider.getPosition().getX() - collider.getRadius() < 0){
            force.add(new Vector2D(1, 0));
            collider.setX(0 + collider.getRadius());
        }
        if(collider.getPosition().getY() - collider.getRadius() < 0){
            force.add(new Vector2D(0, 1));
            collider.setY(0 + collider.getRadius());
        }

        collider.addForce(force);
    }

    /**
     * simulates collisions
     */
    private void simulateCollisions() {
        for (Collider collider1 : entities) {
            addBorderCollisionForce(collider1);
            for (Collider collider2 : entities) {
                collider1.checkCollision(collider2);
            }

        }
    }

    @Override
    public void addEntity(T e) {
        entities.add(e);
    }

    @Override
    public void removeEntity(T e) {
        entities.remove(e);
    }

    @Override
    public void massRemoveEntities(List<T> es) {

    }

    @Override
    public void forEachEntity(Consumer<T> action) {
        entities.forEach(action);
    }
}
