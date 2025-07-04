package UI;

import Canvas.*;
import Editor.EditorWindow;
import Particle.Vector2D;
import Species.SpeciesParticle;
import World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Function;
import java.util.function.Supplier;

public class Demo {


    private Supplier<Drawable> scene = () -> new EmptyCanvas();
    private Function<JPanel, JPanel> settings = panel -> panel;

    public Demo() {}

    public Demo(Supplier<Drawable> scene, Function<JPanel, JPanel> settings) {
        this.scene = scene;
        this.settings = settings;
    }

    public Demo(Supplier<Drawable> scene){
        this.scene = scene;
        this.settings = null;
    }

    public JPanel getSettings() {
        if (settings == null) {
            return new JPanel();
        }
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        return settings.apply(panel);
    }

    public void setSettings(Function<JPanel, JPanel> settings) {
        this.settings = settings;
    }

    public RendererPanel getScene() {
        return new RendererPanel(World.MAX_WIDTH, World.MAX_HEIGHT, scene.get());
    }

    public void setScene(Supplier<Drawable> scene) {
        this.scene = scene;
    }


}
