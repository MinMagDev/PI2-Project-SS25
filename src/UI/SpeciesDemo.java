package UI;

import Editor.EditorWindow;
import Genom.DNA;
import Particle.Particle;
import Particle.Vector2D;
import Social.SocialParticleRenderer;
import Species.*;
import World.World;
import Canvas.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SpeciesDemo extends Demo{
    static double ZAP_FACTOR = 100d;

    private final JSlider socialRadiusMultiplier, speedMultiplier, speciesAmount, specimensAmount, maxSpeed;
    private Runnable zap;

    private Ecosystem ecosystem;

    public SpeciesDemo(Runnable renderer, Runnable[] pause, int species, int specimens, int socialRadiusMultiplier, int speedMultiplier) {
        this.ecosystem = new Ecosystem();


        this.socialRadiusMultiplier = new JSlider(JSlider.HORIZONTAL, 1, 100, socialRadiusMultiplier);
        this.speedMultiplier = new JSlider(JSlider.HORIZONTAL, 1, 100, speedMultiplier);
        this.maxSpeed = new JSlider(JSlider.HORIZONTAL, 1, 100, (int) Particle.MAX_SPEED * 10);



        this.speciesAmount = new JSlider(JSlider.HORIZONTAL, 0, 10, species);
        this.specimensAmount = new JSlider(JSlider.HORIZONTAL, 1, 100, specimens);

        JButton restartButton = new JButton("(Re)start simulation");
        restartButton.addActionListener(e -> {
            ecosystem = new Ecosystem();

            Particle.MAX_SPEED = (double) this.maxSpeed.getValue() / 10;

            ecosystem.setSpeedMultiplier((double) this.speedMultiplier.getValue() / 5);
            ecosystem.setSocialRadiusMultiplier((double) this.socialRadiusMultiplier.getValue());

            super.setScene(() -> createDemo(speciesAmount.getValue(), specimensAmount.getValue()));

            renderer.run();
        });

        JButton zapButton = new JButton("Zap");
        zapButton.addActionListener(e -> {
            zap.run();
        });

        JButton editorButton = new JButton("Open editor");
        editorButton.addActionListener(e -> {
            new EditorWindow();
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            pause[0].run();
        });


        super.setSettings((panel) -> {
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.add(editorButton);
            panel.add(zapButton);
            panel.add(pauseButton);

            panel.add(new JLabel("Max speed:"));
            panel.add(maxSpeed);

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
                particle.addUnlimitedForce(Vector2D.random().mul(ZAP_FACTOR));
            });
        };



        return new World(World.MAX_WIDTH, World.MAX_HEIGHT, particles, renderer);
    }
}
