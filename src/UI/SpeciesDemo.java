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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;


/**
 * drawing instructions for the particle simulation
 */
public class SpeciesDemo extends Demo{

    static final int IRRADIATION_RANGE = 100;

    /**
     * the length of the vector used for the zap force
     */
    static double ZAP_FACTOR = 100d;
    
    // sliders for the settings
    private final SettingsSlider speciesAmount, specimensAmount, maxSpeed, speedMultiplier, interactionRadiusMultiplier;
    // actions for the different buttons
    private final Reference<Runnable> zap = new Reference<>(() -> {}), pause = new Reference<>(() -> {}), cluster = new Reference<>(() -> {}), repaint = new Reference<>(() -> {}), irradiate = new Reference<>(() -> {});

    /**
     * the simulated ecosystem 
     */
    private Ecosystem ecosystem;

    /**
     * the renderer for the simulation
     */
    private SocialParticleRenderer<SpeciesParticle> renderer;

    /**
     * @return the renderer for the simulation
     */
    public SocialParticleRenderer<SpeciesParticle> getRenderer() {
        return renderer;
    }

    

    public SpeciesDemo(Runnable renderer, int species, int specimens) {
        this.ecosystem = new Ecosystem();
        
        // create settings UI
        this.maxSpeed = new SettingsSlider("Max Speed", 1, 100, (int) Particle.MAX_SPEED * 10){
            @Override
            public double getValue() {
                return super.getValue() / 10;
            }
        };

        this.speciesAmount = new SettingsSlider("Species Amount", 1, 10, species);
        this.specimensAmount = new SettingsSlider("Specimens", 1, 100, specimens);

        this.speedMultiplier = new SettingsSlider("Speed Multiplier", 1, 100, 50){
            @Override
            public double getValue() {
                return super.getValue() / 5;
            }
        };
        this.interactionRadiusMultiplier = new SettingsSlider("Interaction Radius", 1, 100, 50){
            @Override
            public double getValue() {
                return super.getValue() / 3;
            }
        };

        JButton restartButton = makeRestartButton(renderer);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));



        buttonPanel.add(new RunnableButton("⚡", zap));

        buttonPanel.add(new RunnableButton("⏯", pause));

        buttonPanel.add(new RunnableButton("☢", irradiate));


        super.setSettings((panel) -> {

            buttonPanel.setBackground(panel.getBackground());

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            makeLabel(panel, "Interact with the simulation:");
            panel.add(buttonPanel);

            panel.add(new RunnableButton("Recalculate species", cluster));

            // Spacer
            panel.add(Box.createRigidArea(new Dimension(0, 30)));

            makeLabel(panel, "For your next simulation:");
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
            panel.add(this.speciesAmount);
            panel.add(this.specimensAmount);

            // Spacer
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            panel.add(this.maxSpeed);
            panel.add(this.speedMultiplier);
            panel.add(this.interactionRadiusMultiplier);

            // Spacer
            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            panel.add(restartButton);

            return panel;

        }
        );



    }

    private static void makeLabel(JPanel panel, String text) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(panel.getBackground());
        JLabel label = new JLabel(text);
        row.add(label);
        row.setMaximumSize(new Dimension(label.getPreferredSize().width + 10, label.getPreferredSize().height + 10));
        panel.add(row);
    }


    private JButton makeRestartButton(Runnable renderer) {
        JButton restartButton = new JButton("(Re)start simulation");
        restartButton.addActionListener(e -> {
            ecosystem = new Ecosystem();

            Particle.MAX_SPEED = this.maxSpeed.getValue();
            ecosystem.setSpeedMultiplier(this.speedMultiplier.getValue());
            ecosystem.setInteractionRadiusMultiplier(this.interactionRadiusMultiplier.getValue());

            super.setScene(() -> createDemo((int) speciesAmount.getValue(), (int) specimensAmount.getValue()));

            renderer.run();
        });
        return restartButton;
    }

    @Override
    public RendererPanel getScene() {
        RendererPanel panel = super.getScene();

        Reference<Boolean> isRunning = new Reference<>(true);

        Reference<Boolean> isIrradiating = new Reference<>(false);

        repaint.set(panel::repaint);

        irradiate.set(() -> {
            isIrradiating.set(!isIrradiating.get());
            if(!isIrradiating.get()) {
                panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                return;
            }
            BufferedImage image = new BufferedImage(IRRADIATION_RANGE, IRRADIATION_RANGE, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            g2d.setColor(new Color(255, 0, 0, 127));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fillOval(0, 0, IRRADIATION_RANGE, IRRADIATION_RANGE);
            g2d.dispose();

            panel.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(IRRADIATION_RANGE / 2, IRRADIATION_RANGE / 2), "circleCursor"));
        });

        pause.set(() -> {
            if (isRunning.get()) {
                panel.pause();
                isRunning.set(false);
            } else {
                panel.resume();
                isRunning.set(true);
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Vector2D relativeClickPosition = Vector2D.fromPoint(e.getPoint());
                if(isRunning.get()) {
                    super.mouseClicked(e);
                    return;
                }

                if(isIrradiating.get()) {
                    getRenderer().getEntitiesInCircle(relativeClickPosition, (double) IRRADIATION_RANGE / 2).forEach((particle) -> {
                        particle.irradiate();
                    });

                } else {
                    SpeciesParticle particle = getRenderer().getEntityAt(relativeClickPosition);
                    if(particle != null) {
                        new EditorWindow(particle, (dna) -> {
                            repaint.get().run();
                        });
                    }
                }

                super.mouseClicked(e);
            }
        });

        return panel;
    }

    private Drawable createDemo(int species, int specimens){

        ArrayList<SpeciesParticle> particles = new ArrayList<>();
        ArrayList<Species> speciesList = new ArrayList<>();

        for(int i = 0; i < species; i++) {
            Species s = new Species(new DNA(), ecosystem);
            speciesList.add(s);
            particles = new ArrayList<>(Stream.concat(particles.stream(), Arrays.stream(SpeciesParticle.makeParticles(specimens, s, World.DEFAULT_WIDTH, World.DEFAULT_HEIGHT))).toList());
        }



        this.renderer = new SocialParticleRenderer<SpeciesParticle>(particles);

        zap.set(() -> {
            renderer.forEachEntity(particle -> {
                particle.addUnlimitedForce(Vector2D.random().mul(ZAP_FACTOR));
            });
        });

        cluster.set(() -> {
            ecosystem.updateSpecies(renderer.getParticles());
            repaint.get().run();
        });

        return new World<SpeciesParticle>(World.DEFAULT_WIDTH, World.DEFAULT_HEIGHT, particles, renderer);
    }
}
