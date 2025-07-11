package UI;

import World.World;

import javax.swing.*;

/*
  D.R.E.C.K Steht für "Durch Rechner Erstellter Code-Kommentar" oder
  "Dubiously Rendered Explanation by Computational Knowledge"
 */

/**
 * Main window of the App
 */
public class DemoWindow extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Evolving Clusters");
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }catch (Exception e){
                System.err.println("NOPE");
            }
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new DemoPanel());
            frame.setSize(World.DEFAULT_WIDTH + 400, World.DEFAULT_HEIGHT +75);
            frame.setVisible(true);
        });
    }
}
