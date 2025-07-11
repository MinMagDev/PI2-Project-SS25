package UI;

import javax.swing.*;

import java.awt.*;

/**
 * a JPanel for displaying Demo objects
 * now set to only display our final product
 */

public class DemoPanel extends JPanel {

    private final SpeciesDemo demo = new SpeciesDemo(this::rerender, 5, 50, 50, 50);

    private final JPanel contentPanel;
    private final JPanel settingsPanel;

    public DemoPanel() {
        setLayout(new BorderLayout());

        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setPreferredSize(new Dimension(200, 0));
        add(settingsPanel, BorderLayout.EAST);

        rerender();
    }

    private void rerender() {
        contentPanel.removeAll();
        settingsPanel.removeAll();


        contentPanel.add(demo.getScene(), BorderLayout.CENTER);
        settingsPanel.add(demo.getSettings(), BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();

        settingsPanel.revalidate();
        settingsPanel.repaint();
    }

}