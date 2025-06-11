package UI;

import Canvas.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class Demo {


    private Supplier<Drawable> scene;
    private Function<JPanel, JPanel> settings;

    public Demo() {
        this.scene = () -> new EmptyCanvas();
        this.settings = panel -> panel;
    }


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

    public Drawable getScene() {
        return scene.get();
    }

    public void setScene(Supplier<Drawable> scene) {
        this.scene = scene;
    }

}
