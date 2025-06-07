package UI;

import World.World;

import javax.swing.*;

public class DemoWindow extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Demos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new DemoPanel());
            frame.setSize(World.MAX_WIDTH + 200, World.MAX_HEIGHT+75);
            frame.setVisible(true);
        });
    }
}
