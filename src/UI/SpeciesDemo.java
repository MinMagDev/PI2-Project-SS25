package UI;

import Genom.DNA;
import Particle.Particle;
import Social.SocialParticleRenderer;
import Social.SocialSystem;
import Species.*;
import World.World;
import Canvas.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SpeciesDemo extends Demo{
    private int species;
    private JSlider socialRadiusMultiplier, speedMultiplier, speciesAmount, specimensAmount;
    private Runnable rerender;

    public SpeciesDemo(Runnable rerender, int species, int specimens, int socialRadiusMultiplier, int speedMultiplier) {
        super(() -> new EmptyCanvas());

        this.species = species;

        this.socialRadiusMultiplier = new JSlider(JSlider.HORIZONTAL, 1, 100, socialRadiusMultiplier);
        this.speedMultiplier = new JSlider(JSlider.HORIZONTAL, 1, 100, speedMultiplier);

        this.speciesAmount = new JSlider(JSlider.HORIZONTAL, 0, 10, species);
        this.specimensAmount = new JSlider(JSlider.HORIZONTAL, 1, 100, specimens);

        JButton restartButton = new JButton("(Re)start simulation");
        restartButton.addActionListener(e -> {
            SpeciesParticle.SPEED_MULTIPLIER = (double) this.speedMultiplier.getValue() / 5;

            Species.cleanUpSpecies();

            super.setScene(() -> createDemo(speciesAmount.getValue(), specimensAmount.getValue(), (double) this.socialRadiusMultiplier.getValue()));

            rerender.run();
        });

        super.setSettings((panel) -> {
            panel.add(new JLabel("Social radius multiplier: "));
            panel.add(this.socialRadiusMultiplier);
            panel.add(new JLabel("Speed multiplier: "));
            panel.add(this.speedMultiplier);
            panel.add(new JLabel("Species: "));
            panel.add(this.speciesAmount);
            panel.add(new JLabel("Specimens: "));
            panel.add(this.specimensAmount);

            panel.add(restartButton);

            return panel;

        }
        );



    }

    private Drawable createDemo(int species, int specimens, double interactionRadiusMultiplier){


        // list of all colors
        Color[] colors = {
                Color.BLACK,
                Color.BLUE,
                Color.CYAN,
                Color.DARK_GRAY,
                Color.GRAY,
                Color.GREEN,
                Color.LIGHT_GRAY,
                Color.MAGENTA,
                Color.ORANGE,
                Color.PINK,
                Color.RED,
                Color.WHITE,
                Color.YELLOW
        };

        List<SpeciesParticle> particles = new ArrayList<>();

        if(species > colors.length) {
            throw new IllegalArgumentException("Species " + species + " is out of bounds");

        }

        for(int i = 0; i < species; i++) {
            Species s = new Species(new DNA());
            s.setColor(colors[i]);
            particles = Stream.concat(particles.stream(), Arrays.stream(SpeciesParticle.makeParticles(specimens, s, World.MAX_WIDTH, World.MAX_HEIGHT))).toList();
        }



        var renderer = new SocialParticleRenderer(particles, interactionRadiusMultiplier);

        return new World(World.MAX_WIDTH, World.MAX_HEIGHT, particles, renderer);
    }
}
