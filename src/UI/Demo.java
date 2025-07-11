package UI;

import Canvas.*;
import World.World;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * basic framework for a scene
 * for visual debugging purposes we had multiple demos during development
 * but we only have one scene in our final product
 */

public class Demo {

    /**
     * the function that creates the scene
     */
    private Supplier<Drawable> scene = () -> new EmptyCanvas();
    /**
     * the function that creates the settings panel for the scene
     */
    private Function<JPanel, JPanel> settings = panel -> panel;

    public Demo() {}

    /**
     * @param scene the function that creates the scene
     * @param settings the function that creates the settings panel for the scene
     */
    public Demo(Supplier<Drawable> scene, Function<JPanel, JPanel> settings) {
        this.scene = scene;
        this.settings = settings;
    }

    /**
     * @param scene the function that creates the scene
     */

    public Demo(Supplier<Drawable> scene){
        this.scene = scene;
        this.settings = null;
    }

    /**
     * @return the settings JPanel
     */

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

    /**
     * creates a renderer for the scene
     * @return the new JPanel for the scene
     */
    public RendererPanel getScene() {
        return new RendererPanel(World.DEFAULT_WIDTH, World.DEFAULT_HEIGHT, scene.get());
    }

    /**
     * sets the scene
     * @param scene the new scene
     */
    public void setScene(Supplier<Drawable> scene) {
        this.scene = scene;
    }


}
