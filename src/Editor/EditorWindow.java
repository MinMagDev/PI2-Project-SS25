package Editor;

import Genom.DNA;
import Species.Ecosystem;

import javax.swing.*;

public class EditorWindow extends JFrame {
    private Ecosystem ecosystem;

    public EditorWindow(){
        setTitle("Zweites Fenster");
        setSize(1200, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // nur dieses Fenster schließen

        JButton zapButton = new JButton("Zap");
        zapButton.addActionListener(e -> {
            System.out.println(this.getSize());
        });
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new DNADisplay(new DNA()));
        panel.add(zapButton);
        add(panel);


        setVisible(true);
    }

    public static void main(String[] args) {
        new EditorWindow();
    }



}
