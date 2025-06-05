package World;

import Canvas.*;
import Particle.DebugParticle;
import Particle.Vector2D;


import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        for (Collider collider : colliders) {
            addCollisionForce(collider);
        }
        renderer.update();
    }

    /**
     * adds a collision force to a collider upon collision with the world border
     * @param collider the collider
     */

    private void addCollisionForce(Collider collider) {
        Vector2D force = new Vector2D();
        if(collider.getPosition().getX() + collider.getRadius() > width){
            force.add(new Vector2D(-1, 0));
        }
        if(collider.getPosition().getY() + collider.getRadius() > height){
            force.add(new Vector2D(0, -1));
        }
        if(collider.getPosition().getX() - collider.getRadius() < 0){
            force.add(new Vector2D(1, 0));
        }
        if(collider.getPosition().getY() - collider.getRadius() < 0){
            force.add(new Vector2D(0, 1));
        }
        collider.addForce(force);
    }

    public static Drawable createExample(int width, int height) {
        final List<DebugParticle> particles = Arrays.stream(DebugParticle.createExampleArray(10, width, height)).toList();
        Drawable renderer = new ParticleRenderer(particles);

        return new World(400, 400, particles, renderer);
    }
}
