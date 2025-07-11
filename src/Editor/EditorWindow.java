package Editor;

import Genom.DNA;
import Species.Ecosystem;
import Species.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class EditorWindow extends JFrame {

    public EditorWindow(){

        JButton zapButton = new JButton("Zap");
        zapButton.addActionListener(e -> {
            System.out.println(this.getSize());
        });
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Ecosystem ecosystem = Ecosystem.createExampleEcosystem(3);
        panel.add(new DNADisplay(new Species(new DNA(), ecosystem), null, (dna) -> {}));
        panel.add(zapButton);
        add(panel);

        setUp();
    }

    public EditorWindow(SpeciesParticle particle) {
        this(particle, (dna) -> {});
    }

    public EditorWindow(SpeciesParticle particle, Consumer<DNA> confirmEditHandler) {
        var species = particle.getSpecies();
        var display = new DNADisplay(species, particle.getDNA(), confirmEditHandler.andThen((dna) -> {
            particle.setDNA(dna);
            dispose();
        }), () -> {
            particle.setColor(Color.CYAN);
        });

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



    public static void main(String[] args) {
        new EditorWindow();
    }



}
