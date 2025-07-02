package World;

import Canvas.*;
import Particle.DebugParticle;
import Particle.Particle;
import Particle.Vector2D;
import Genom.DNA;
import Social.SocialParticleRenderer;
import Species.Species;
import Species.SpeciesParticle;


import java.awt.*;
import java.util.*;
import java.util.List;

/**
  * A drawable class that simulates the very crude "physics" involved
 */

public class World implements Drawable {
    private final int width;
    private final int height;

    public static final int MAX_WIDTH = 1000;
    public static final int MAX_HEIGHT = 1000;
    public static final int MAX_ENTITY_COUNT = 1000;

    //Fuck it, who needs Wildcards
    List<SpeciesParticle> colliders;

    private final Drawable renderer;

    public World(int width, int height, List<SpeciesParticle> colliders, Drawable renderer) {
        this.width = width;
        this.height = height;
        this.colliders = new ArrayList<>(colliders);
        this.renderer = renderer;
    }

    @Override
    public void draw(Graphics g) {
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
        List<SpeciesParticle> toRemove = new ArrayList<>();
        List<SpeciesParticle> allMoms = new ArrayList<>();
        for (int i = 0; i < colliders.size(); i++) {
            if (colliders.get(i) instanceof  SpeciesParticle) {
                SpeciesParticle particle = (SpeciesParticle) colliders.get(i);
                if (!particle.isAlive()) {
                    toRemove.add(particle);
                    if(renderer instanceof ParticleRenderer){
                        ((ParticleRenderer<SpeciesParticle>) renderer).removeEntity(particle);
                    }
                    continue;
                }
                if(particle.isReproducing()){
                    particle.setReproducing(false);
                    allMoms.add(particle);
              }
            }
        }
        for(SpeciesParticle child : createNewChilds(allMoms)){
            if (colliders.size() >= MAX_ENTITY_COUNT) break;
            colliders.add(child);
            if(renderer instanceof ParticleRenderer){
                ((ParticleRenderer<SpeciesParticle>) renderer).addEntity(child);
            }
        }
        colliders.removeAll(toRemove);
        if(renderer instanceof ParticleRenderer){
            ((ParticleRenderer<SpeciesParticle>) renderer).massRemoveEntities(toRemove);
        }
    }

    /**
     * Erzeugt neue Kind-Partikel aus einer Liste von Mutter-Partikeln.
     * <p>
     * Jeder Mutterpartikel ruft {@code newChild()} auf sich selbst auf. Falls ein Kind erzeugt wurde,
     * wird es mittels {@link #createNewChild(SpeciesParticle)} weiterverarbeitet.
     *
     * @param allMoms Eine Liste von {@link SpeciesParticle}-Instanzen, die reproduzieren sollen.
     * @return Eine Liste aller erfolgreich erzeugten Kind-Partikel.
     * <p>
     * D.R.E.C.K
     */
    private List<SpeciesParticle> createNewChilds(List<SpeciesParticle> allMoms) {
        List<SpeciesParticle> childs = new ArrayList<>();
        for (SpeciesParticle mom: allMoms){
             SpeciesParticle child = createNewChild(mom);
            if (child == null) continue;
            childs.add(child);
        }
        return childs;
    }

    /**
     * Erzeugt ein neues Kind-Partikel auf Basis eines Mutterpartikels.
     * <p>
     * Das Kind wird durch den Aufruf von {@code mom.newChild()} erzeugt. Falls ein Kind erzeugt wird,
     * werden anschließend mit {@code updateValues()} zusätzliche Werte aktualisiert.
     *
     * @param mom Der {@link SpeciesParticle}, der das Kind erzeugt.
     * @return Ein neues {@link SpeciesParticle}-Kind oder {@code null}, wenn keins erzeugt wurde.
     * <p>
     * D.R.E.C.K.
     */
    private SpeciesParticle createNewChild(SpeciesParticle mom) {
        SpeciesParticle child = mom.newChild();
        if (child == null) return child;
        child.updateValues();
        return child;
    }

    static final double speedFactor = .2;
    /**
     * adds a collision force to a collider upon collision with the world border
     * @param collider the collider
     */

    private void addBorderCollisionForce(Collider collider) {
        Vector2D force = new Vector2D();
        boolean collision = false;
        if(collider.getPosition().getX() + collider.getRadius() > width){
            force.add(new Vector2D(-1, 0));
            collider.setX(width - collider.getRadius());
            collision = true;
        }
        if(collider.getPosition().getY() + collider.getRadius() > height){
            force.add(new Vector2D(0, -1));
            collider.setY(height - collider.getRadius());
            collision = true;
        }
        if(collider.getPosition().getX() - collider.getRadius() < 0){
            force.add(new Vector2D(1, 0));
            collider.setX(0 + collider.getRadius());
            collision = true;
        }
        if(collider.getPosition().getY() - collider.getRadius() < 0){
            force.add(new Vector2D(0, 1));
            collider.setY(0 + collider.getRadius());
            collision = true;
        }


        collider.addForce(force);
    }

    /**
     * simulates collisions
     */
    private void simulateCollisions() {
        for (Collider collider1 : colliders) {
            addBorderCollisionForce(collider1);
            for (Collider collider2 : colliders) {
                collider1.checkCollision(collider2);
            }

        }
    }
}
