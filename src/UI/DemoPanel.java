package UI;

import Canvas.*;
import World.World;

import javax.swing.*;

import static java.util.Map.entry;

import java.awt.*;
import java.util.Map;
import java.util.function.Supplier;

public class DemoPanel extends JPanel {
    static Map<String, Supplier<Drawable>> demos = Map.ofEntries(
            entry("Basic particles", (Supplier<Drawable>)(ParticleRenderer::createExample)),
            entry("Collision of two particles", (Supplier<Drawable>)(World::collisionDemo)),
            entry("Physics", (Supplier<Drawable>)(() -> World.createExample(400, 400))),
            entry("Social behaviour", (Supplier<Drawable>)(World::socialDemo))
    );


    private CardLayout cardLayout;
    private JPanel contentPanel;

    public DemoPanel() {
        setLayout(new BorderLayout());

        String[] demoNames = demos.keySet().toArray(new String[0]);
        JList<String> demoList = new JList<>(demoNames);
        demoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        demoList.setSelectedIndex(0);
        JScrollPane listScroll = new JScrollPane(demoList);
        listScroll.setPreferredSize(new Dimension(150, 0));
        add(listScroll, BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        demoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = demoList.getSelectedValue();
                showDemo(selected);
            }
        });

        // Initial anzeigen
        showDemo(demoNames[0]);
    }

    private void showDemo(String name) {
        contentPanel.removeAll();
        RendererPanel newPanel = new RendererPanel(400, 400, demos.get(name).get());
        contentPanel.add(newPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


}