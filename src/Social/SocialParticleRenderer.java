package Social;

import Canvas.DrawableParticle;
import Canvas.ParticleRenderer;
import LifeAndDeath.EntityManager;

import java.util.List;


/**
 * renders a social particle system
 * @param <T> the particles class
 */
public class SocialParticleRenderer<T extends DrawableParticle & SocialEntity> extends ParticleRenderer<T> implements EntityManager<T> {

    public SocialParticleRenderer(List<T> particles) {
        super(particles);
        socialSystem = new SocialSystem<T>(particles);
    }

    private final SocialSystem<T> socialSystem;


    @Override
    public void update() {
        super.update();
        socialSystem.triggerInteractions();
    }

    @Override
    public void addEntity(T e) {
        super.addEntity(e);
    }

    @Override
    public void removeEntity(T e) {
        super.removeEntity(e);
    }
}
