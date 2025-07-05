package Canvas;

import Genom.DNA;
import LifeAndDeath.EntityManager;
import Particle.DebugParticle;
import Particle.Vector2D;
import Species.Species;
import Species.SpeciesParticle;
import World.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;
import Particle.Entity;



public class ParticleRenderer<T extends DrawableParticle & Entity> implements Drawable, EntityManager<T> {

    public List<T> getParticles() {
        return particles;
    }

    private List<T> particles;

    public ParticleRenderer(List<T> particles) {
        this.particles = particles;
    }

    /**
     *  creates an example with 10 particles in a 400x400 area
     * @return the example renderer
     */
    public static ParticleRenderer createExample(){
        return new ParticleRenderer(Arrays.stream(DebugParticle.createExampleArray(10, World.MAX_WIDTH, World.MAX_HEIGHT)).toList());
    }

    public void draw(Graphics g) {
        //Nicht das schönste aber funktioniert
        for (final DrawableParticle particle : particles) {
            g.setColor(particle.getColor());
            final int particleDiameter = particle.getRadiusForDrawing() * 2;
            g.fillOval(particle.getXForDrawing() - particle.getRadiusForDrawing(), particle.getYForDrawing() - particle.getRadiusForDrawing(), particleDiameter, particleDiameter);
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
    public void massRemoveEntities(List<T> es) {
        for (T e: es){
            removeEntity(e);
        }
    }


    @Override
    public void forEachEntity(Consumer<T> action) {
        particles.forEach(action);
    }
}
