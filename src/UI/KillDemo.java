package UI;

import Canvas.Drawable;
import Genom.DNA;
import Social.SocialParticleRenderer;
import Species.Species;
import Species.SpeciesParticle;
import World.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class KillDemo extends Demo{
    public KillDemo() {
        super(KillDemo::createDemo);
    }

    private static Drawable createDemo(){
        Species.cleanUpSpecies();

        final Species s1 = new Species(new DNA());
        s1.setColor(Color.BLUE);
        final Species s2 = new Species(new DNA());
        s2.setColor(Color.RED);

        final SpeciesParticle p1 = new SpeciesParticle(World.MAX_WIDTH, World.MAX_HEIGHT, s1);
        final SpeciesParticle p2 = new SpeciesParticle(World.MAX_WIDTH, World.MAX_HEIGHT, s2);

        var p1Top2 = p1.getPosition().to(p2.getPosition()).mul(100);

        p1.addForce(p1Top2);
        p2.addForce(p1Top2.mul(-1));

        var particles = Arrays.asList(p1, p2);

        var renderer = new SocialParticleRenderer(particles);

        return new World(World.MAX_WIDTH, World.MAX_HEIGHT, particles, renderer);
    }

}
