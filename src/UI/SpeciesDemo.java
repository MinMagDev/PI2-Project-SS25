package UI;

import Genom.DNA;
import Particle.Vector2D;
import Social.SocialParticleRenderer;
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
    static double ZAP_FACTOR = 1000000d;

    private final JSlider socialRadiusMultiplier, speedMultiplier, speciesAmount, specimensAmount;
    private Runnable zap;

    private Ecosystem ecosystem;

    public SpeciesDemo(Runnable renderer, int species, int specimens, int socialRadiusMultiplier, int speedMultiplier) {
        this.ecosystem = new Ecosystem();


        this.socialRadiusMultiplier = new JSlider(JSlider.HORIZONTAL, 1, 100, socialRadiusMultiplier);
        this.speedMultiplier = new JSlider(JSlider.HORIZONTAL, 1, 100, speedMultiplier);

        this.speciesAmount = new JSlider(JSlider.HORIZONTAL, 0, 10, species);
        this.specimensAmount = new JSlider(JSlider.HORIZONTAL, 1, 100, specimens);

        JButton restartButton = new JButton("(Re)start simulation");
        restartButton.addActionListener(e -> {
            ecosystem = new Ecosystem();

            ecosystem.setSpeedMultiplier((double) this.speedMultiplier.getValue() / 5);
            ecosystem.setSocialRadiusMultiplier((double) this.socialRadiusMultiplier.getValue());

            super.setScene(() -> createDemo(speciesAmount.getValue(), specimensAmount.getValue()));

            renderer.run();
        });

        JButton zapButton = new JButton("Zap");
        zapButton.addActionListener(e -> {
            zap.run();
        });


        super.setSettings((panel) -> {
            panel.add(zapButton);

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

    private Drawable createDemo(int species, int specimens){


        // list of all colors

        List<SpeciesParticle> particles = new ArrayList<>();

        for(int i = 0; i < species; i++) {
            Species s = new Species(new DNA(), ecosystem);
            //s.setColor(colors[i]);
            particles = Stream.concat(particles.stream(), Arrays.stream(SpeciesParticle.makeParticles(specimens, s, World.MAX_WIDTH, World.MAX_HEIGHT))).toList();
        }



        var renderer = new SocialParticleRenderer<SpeciesParticle>(particles);

        zap = () -> {
            renderer.forEachEntity(particle -> {
                particle.addForce(Vector2D.random().mul(ZAP_FACTOR));
            });
        };



        return new World(World.MAX_WIDTH, World.MAX_HEIGHT, particles, renderer);
    }
}
