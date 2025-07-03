package UI;

import Canvas.*;
import Editor.EditorWindow;
import Particle.Entity;
import Particle.Vector2D;
import Species.Species;
import World.World;
import Species.SpeciesParticle;

import javax.swing.*;

import static java.util.Map.entry;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DemoPanel extends JPanel {

    Map<String, Demo> demos;


    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel settingsPanel;

    private String current;

    private final Runnable[] pause = new Runnable[1];


    public DemoPanel() {
        demos = new HashMap<>();
        demos.put("Basic particles", new Demo(ParticleRenderer::createExample, (panel) -> {
            panel.add(new JLabel("Keine Optionen"));
            return panel;
        }));
        demos.put("Species", new SpeciesDemo(this::rerender, pause, 5, 50, 50, 50));
        demos.put("Kill", new KillDemo());


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

        settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setPreferredSize(new Dimension(200, 0));
        add(settingsPanel, BorderLayout.EAST);

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
        current = name;
        contentPanel.removeAll();
        settingsPanel.removeAll();

        Demo demo = demos.get(name);
        JPanel settings = demo.getSettings();

        RendererPanel newPanel = new RendererPanel(World.MAX_WIDTH, World.MAX_HEIGHT, demos.get(name).getScene());

        boolean[] isRunning = new boolean[1];

        isRunning[0] = true;

        pause[0] = () -> {
            if (isRunning[0]) {
                newPanel.pause();
                isRunning[0] = false;
            } else {
                newPanel.resume();
                isRunning[0] = true;
            }
        };

        newPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isRunning[0]) {
                    super.mouseClicked(e);
                    return;
                }
                Point relativeClickPosition = e.getPoint();
                if(demo instanceof SpeciesDemo) {
                   SpeciesParticle particle = ((SpeciesDemo) demo).getRenderer().getEntityAt(Vector2D.fromPoint(relativeClickPosition));

                   if(particle != null) {
                       new EditorWindow(particle);
                   }
                }
                super.mouseClicked(e);
            }
        });

        contentPanel.add(newPanel, BorderLayout.CENTER);
        settingsPanel.add(settings, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
        settingsPanel.revalidate();
        settingsPanel.repaint();
    }

    public void rerender() {
        showDemo(current);
    }

}