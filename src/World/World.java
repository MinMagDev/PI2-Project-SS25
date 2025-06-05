package World;

import Canvas.*;
import Particle.DebugParticle;
import Particle.Particle;
import Particle.Vector2D;


import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
  * A drawable class that simulates the very crude "physics" involved
 */

public class World implements Drawable {
    private final int width;
    private final int height;

    List<? extends Collider> colliders;

    private final Drawable renderer;

    public World(int width, int height, List<? extends Collider> colliders, Drawable renderer) {
        this.width = width;
        this.height = height;
        this.colliders = colliders;
        this.renderer = renderer;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        // draw the outlines of the world
        g.drawLine(0, 0, width, 0);
        g.drawLine(0, 0, 0, height);
        g.drawLine(0, height, width, 0);
        g.drawLine(width, 0, 0, height);
        renderer.draw(g);
    }

    @Override
    public void update() {
        simulateCollisions();
        renderer.update();
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
            collision = true;
        }
        if(collider.getPosition().getY() + collider.getRadius() > height){
            force.add(new Vector2D(0, -1));
            collision = true;
        }
        if(collider.getPosition().getX() - collider.getRadius() < 0){
            force.add(new Vector2D(1, 0));
            collision = true;
        }
        if(collider.getPosition().getY() - collider.getRadius() < 0){
            force.add(new Vector2D(0, 1));
            collision = true;
        }


        collider.addForce(force);

        if(collision){
            collider.setSpeed(collider.getSpeed() * speedFactor);
        }
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

    /**
     * creates an example world with 10 debug particles
     * @param width the world's width
     * @param height the world's width
     * @return the example world
     */

    public static Drawable createExample(int width, int height) {
        final List<DebugParticle> particles = Arrays.stream(DebugParticle.createExampleArray(10, width, height)).toList();
        Drawable renderer = new ParticleRenderer(particles);

        return new World(width, height, particles, renderer);
    }

    /**
     * creates a demo where two particles bounce off each other
     * @return the demo world
     */
    public static Drawable collisionDemo() {
        final List<DebugParticle> particles = Arrays.stream(DebugParticle.createExampleArray(2, 400, 400)).toList();
        Drawable renderer = new ParticleRenderer(particles);
        Particle p1 = particles.get(0);
        Particle p2 = particles.get(1);

        p1.addForce(p1.getPosition().to(p2.getPosition()));
        p2.addForce(p2.getPosition().to(p1.getPosition()));

        return new World(400, 400, particles, renderer);
    }
}
