package Particle;

import Canvas.DrawableParticle;

import java.util.function.Consumer;

public interface ParticleList {
    void forEach(Consumer<DrawableParticle> function);
}
