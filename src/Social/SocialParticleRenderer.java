package Social;

import Canvas.DrawableParticle;
import Canvas.ParticleRenderer;
import LifeAndDeath.EntityManager;
import Particle.DebugParticle;
import World.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocialParticleRenderer<T extends DrawableSocialParticle> extends ParticleRenderer<T> implements EntityManager<T> {
    public SocialParticleRenderer(List<T> particles) {
        super(particles);
        socialSystem = new SocialSystem<T>(particles);
    }

    public SocialParticleRenderer(List<T> particles, double interactionRadiusMultiplier) {
        super(particles);
        socialSystem = new SocialSystem<T>(particles);
    }

    private final SocialSystem<T> socialSystem;

    public static SocialParticleRenderer<DebugParticle> createExample(){
        return new SocialParticleRenderer<>(Arrays.stream(DebugParticle.createExampleArray(10, World.MAX_WIDTH, World.MAX_HEIGHT)).toList());
    }

    @Override
    public void update() {
        super.update();
        socialSystem.triggerInteractions();
    }

    @Override
    public void addEntity(T e) {
        socialSystem.addEntity(e);
        super.addEntity(e);
    }

    @Override
    public void removeEntity(T e) {
        socialSystem.removeEntity(e);
        super.removeEntity(e);
    }
}
