package Canvas;

import LifeAndDeath.EntityManager;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import Particle.Entity;


/**
 * renders particles wooow
 * @param <T> the class that represents the particles
 */
public class ParticleRenderer<T extends DrawableParticle & Entity> implements Drawable, EntityManager<T> {

    public List<T> getParticles() {
        return particles;
    }

    private final List<T> particles;

    public ParticleRenderer(List<T> particles) {
        this.particles = particles;
    }



    public void draw(Graphics g) {
        //Nicht das schönste aber funktioniert
        for (final DrawableParticle particle : particles) {
            g.setColor(particle.getColor());
            final int particleDiameter = particle.getRadiusForDrawing() * 2;
            g.fillOval(particle.getXForDrawing(), particle.getYForDrawing(), particleDiameter, particleDiameter);
        }
    }

    @Override
    public void update() {
        for (DrawableParticle particle: particles){
            particle.update();
        }
    }
    @Override
    public void addEntity(T e) {
        particles.add(e);
    }

    @Override
    public void removeEntity(T e) {
        particles.remove(e);
    }

    @Override
    public void forEachEntity(Consumer<T> action) {
        particles.forEach(action);
    }
}
