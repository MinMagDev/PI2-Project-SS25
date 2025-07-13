package Editor;

import Genom.DNA;
import Species.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;


public class EditorWindow extends JFrame {



    public EditorWindow(SpeciesParticle particle, Consumer<DNA> confirmEditHandler) {
        var species = particle.getSpecies();
        var display = new DNADisplay(species, particle.getDNA(), confirmEditHandler.andThen((dna) -> {
            particle.setDNA(dna);
            dispose();
        }), () -> particle.setColor(Color.CYAN));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(display);

        add(panel);

        setUp();

    }

    private void setUp(){
        setTitle("DNA Editor");
        setSize(1150, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // nur dieses Fenster schließen


        setVisible(true);
    }


}
