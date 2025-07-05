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
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SpeciesDemo extends Demo{
    static double ZAP_FACTOR = 100d;

    private final JSlider socialRadiusMultiplier, speedMultiplier, speciesAmount, specimensAmount, maxSpeed;
    private Runnable zap, pause;

    private Ecosystem ecosystem;

    public SocialParticleRenderer<SpeciesParticle> getRenderer() {
        return renderer;
    }

    private SocialParticleRenderer<SpeciesParticle> renderer;

    public SpeciesDemo(Runnable renderer, int species, int specimens, int socialRadiusMultiplier, int speedMultiplier) {
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton zapButton = new JButton("⚡");
        zapButton.addActionListener(e -> {
            zap.run();
        });

        buttonPanel.add(zapButton);


        JButton pauseButton = new JButton("⏯");
        pauseButton.addActionListener(e -> {
            pause.run();
        });

        buttonPanel.add(pauseButton);

        super.setSettings((panel) -> {

            buttonPanel.setBackground(panel.getBackground());

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.add(buttonPanel);

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

    @Override
    public RendererPanel getScene() {
        RendererPanel panel = super.getScene();

        Reference<Boolean> isRunning = new Reference<>(true);

        pause = () -> {
            if (isRunning.get()) {
                panel.pause();
                isRunning.set(false);
            } else {
                panel.resume();
                isRunning.set(true);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isRunning.get()) {
                    super.mouseClicked(e);
                    return;
                }
                Point relativeClickPosition = e.getPoint();
                SpeciesParticle particle = getRenderer().getEntityAt(Vector2D.fromPoint(relativeClickPosition));
                if(particle != null) {
                    new EditorWindow(particle, (dna) -> {
                        panel.repaint();
                    });
                }
                super.mouseClicked(e);
            }
        });

        return panel;
    }

    private Drawable createDemo(int species, int specimens){

        ArrayList<SpeciesParticle> particles = new ArrayList<>();

        for(int i = 0; i < species; i++) {
            Species s = new Species(new DNA(), ecosystem);
            particles = new ArrayList<>(Stream.concat(particles.stream(), Arrays.stream(SpeciesParticle.makeParticles(specimens, s, World.MAX_WIDTH, World.MAX_HEIGHT))).toList());
        }



        this.renderer = new SocialParticleRenderer<SpeciesParticle>(particles);

        zap = () -> {
            renderer.forEachEntity(particle -> {
                particle.addUnlimitedForce(Vector2D.random().mul(ZAP_FACTOR));
            });
        };



        return new World(World.MAX_WIDTH, World.MAX_HEIGHT, particles, renderer);
    }
}
