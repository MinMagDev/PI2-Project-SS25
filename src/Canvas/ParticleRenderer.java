package Canvas;

import Particle.DebugParticle;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ParticleRenderer implements Drawable {

    public List<? extends DrawableParticle> getParticles() {
        return particles;
    }

    private List<? extends DrawableParticle> particles;

    public ParticleRenderer(List<? extends DrawableParticle> particles) {
        this.particles = particles;
    }

    @Override
    public void update() {
        for (DrawableParticle particle : particles) {
            particle.update();
        }
    }

    /**
     *  creates an example with 10 particles in a 400x400 area
     * @return the example renderer
     */
    public static ParticleRenderer createExample(){
        return new ParticleRenderer(Arrays.stream(DebugParticle.createExampleArray(10, 400, 400)).toList());
    }

    public void draw(Graphics g) {
        //Nicht das schönste aber funktioniert
        g.clearRect(0,0, CanvasDemo.screenWidth,CanvasDemo.screenHeight);
        for (final DrawableParticle particle : particles) {
            g.setColor(particle.getColor());
            final int particleDiameter = particle.getRadiusForDrawing() * 2;
            g.fillOval(particle.getXForDrawing() - particle.getRadiusForDrawing(), particle.getYForDrawing() - particle.getRadiusForDrawing(), particleDiameter, particleDiameter);
        }
    }
}
