package Social;

import Canvas.DrawableParticle;
import Canvas.ParticleRenderer;
import Particle.DebugParticle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocialParticleRenderer extends ParticleRenderer{
    public SocialParticleRenderer(List<? extends DrawableSocialParticle> particles) {
        super(particles);
        socialSystem = new SocialSystem(particles);
        System.out.println(socialSystem);
    }
    private final SocialSystem socialSystem;

    public static SocialParticleRenderer createExample(){
        return new SocialParticleRenderer(Arrays.stream(DebugParticle.createExampleArray(10, 400, 400)).toList());
    }

    @Override
    public void update() {
        super.update();
        socialSystem.triggerInteractions();
    }
}
