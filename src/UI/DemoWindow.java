package UI;

import World.World;

import javax.swing.*;

/**
 * D.R.E.C.K Steht für "Durch Rechner Erstellter Code-Kommentar" oder
 * "Dubiously Rendered Explanation by Computational Knowledge"
 */
public class DemoWindow extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Demos");
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }catch (Exception e){
                System.err.println("NOPE");
            }
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new DemoPanel());
            frame.setSize(World.MAX_WIDTH + 400, World.MAX_HEIGHT+75);
            frame.setVisible(true);
        });
    }
}
