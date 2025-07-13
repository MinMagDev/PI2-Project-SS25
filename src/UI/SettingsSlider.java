package UI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingsSlider extends JPanel {
    private final JSlider slider;

    public SettingsSlider(String name, int min, int max, int defaultValue) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        slider = new JSlider(JSlider.HORIZONTAL, min, max, defaultValue);
        JLabel label = new JLabel(name + ": " + (int) getValue());

        slider.addChangeListener(e -> label.setText(name + ": " + (int) getValue()));

        add(label);
        add(slider);

        setBackground(Color.WHITE);
    }

    public double getValue() {
        return slider.getValue();
    }

}
