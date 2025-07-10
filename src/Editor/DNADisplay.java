package Editor;

import Genom.DNA;
import Species.Ecosystem;
import Species.Species;
import UI.Reference;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class DNADisplay extends JPanel implements KeyListener, MouseListener {
    private InterestingDNASite[] interestingDNASites;

    private static final SimpleAttributeSet standardAttributeSet = new SimpleAttributeSet();
    private static final Font DNAFont = new Font(Font.SANS_SERIF, Font.PLAIN, 25);

    private final Ecosystem ecosystem;
    private Species species;



    private final JTextPane textPane;
    private final StyledDocument doc;

    private final InteractionMatrixDisplay interactionMatrixDisplay;
    private final AttributeDisplay attributeDisplay;

    private String text;

    private int lastClickedPos = -1;
    private boolean editable = false;

    private final Reference<Consumer<DNA>> confirmEditHandler = new Reference<>(null);
    private DNA currentDNA;

    public DNADisplay(Species species, DNA dna, Consumer<DNA> confirmEditHandler) {
        this(species, dna, confirmEditHandler, () -> {});
    }


    public DNADisplay(Species species, DNA dna, Consumer<DNA> confirmEditHandler, Runnable markAction) {
        this.confirmEditHandler.set(confirmEditHandler);

        this.species = species;

        if (dna == null) {
            dna = species.getDNA();
        }

        currentDNA = dna;


        this.ecosystem = species.getEcosystem();

        var fixedSites = new AttributeDNASite[]{
                new AttributeDNASite("speed", Color.CYAN,  DNA.SPEED_DNA_POSITION, DNA.SPEED_DNA_LENGTH, () -> String.valueOf(currentDNA.getSpeed())),
                new AttributeDNASite("interaction radius", Color.GREEN, DNA.INTERACTION_RADIUS_DNA_POSITION, DNA.INTERACTION_RADIUS_DNA_LENGTH, () -> String.valueOf(currentDNA.getRadius())),
                new AttributeDNASite("hunger", Color.ORANGE, DNA.HUNGER_DNA_POSITION, DNA.HUNGER_DNA_LENGTH, (() -> String.valueOf(currentDNA.getHunger()))),
                new AttributeDNASite("reproduction pobability", Color.MAGENTA, DNA.REPRODUCTION_PROBABILITY_DNA_POSITION, DNA.REPRODUCTION_PROBABILITY_DNA_LENGTH, () -> String.valueOf(currentDNA.getReproductionProbability())),
        };

        var fixedSitesStream = Arrays.stream(fixedSites);
        var interactionSites = SpeciesInteractionNucleotidePosition.fromEcosystem(ecosystem);
        var interactionSitesStream = interactionSites.stream();

        interestingDNASites = Stream.concat(fixedSitesStream, interactionSitesStream).toArray(InterestingDNASite[]::new);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel speciesLabel = new JLabel("Species: " + species.getId());
        add(speciesLabel);

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setOpaque(false);
        textPane.setCaretColor(this.getBackground());

        StyleConstants.setForeground(standardAttributeSet, Color.BLACK);
        textPane.setFont(DNAFont);

        doc = textPane.getStyledDocument();

        var baseText = dna.toString();

        text = baseText.substring(0, 64) + "\n" + baseText.substring(64);


        try {
            for (int i = 0; i < text.length(); i++) {

                char c = text.charAt(i);

                doc.insertString(doc.getLength(), String.valueOf(c), getAttributeSetFor(i));
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        textPane.addMouseListener(this);
        textPane.addKeyListener(this);

        textPane.setAlignmentX(CENTER_ALIGNMENT);
        add(textPane);

        this.interactionMatrixDisplay = new InteractionMatrixDisplay(ecosystem, currentDNA);
        this.attributeDisplay = new AttributeDisplay(fixedSites);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));

        tablePanel.add(interactionMatrixDisplay);
        tablePanel.add(attributeDisplay);

        tablePanel.setMaximumSize(new Dimension(800, 10));

        add(tablePanel);

        JPanel buttonPanel = makeButtonPanel(confirmEditHandler, markAction);

        add(buttonPanel);


    }

    private JPanel makeButtonPanel(Consumer<DNA> confirmEditHandler, Runnable markAction) {
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton markButton = new JButton("Mark");
        buttonPanel.add(markButton);
        markButton.addActionListener(e -> {
            this.confirmEditHandler.set(confirmEditHandler.andThen((_dna) -> {
                markAction.run();
            }));
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            this.confirmEditHandler.get().accept(currentDNA);
        });
        buttonPanel.add(confirmButton);
        return buttonPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(!editable) {
            return;
        }
        if (lastClickedPos >= 0 && lastClickedPos < doc.getLength() && text.charAt(lastClickedPos) != '\n') {
            char typedChar = e.getKeyChar();
            if (DNA.nucleotideDictionary.containsKey(typedChar)) {
                replaceCharAt(lastClickedPos, typedChar);
                handleEdit();
            }
        }
    }


    private SimpleAttributeSet getAttributeSetFor(int pos) {

        for(var site: interestingDNASites){
            if(site.isInterestingNucleotidePosition(pos)){
                return site.getAttributeSet();
            }
        }

        return standardAttributeSet;
    }

    private void replaceCharAt(int pos, Character newChar) {
        try {
            doc.remove(pos, 1);
            doc.insertString(pos, String.valueOf(newChar).toUpperCase(), getAttributeSetFor(pos));
        }
        catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        text = text.substring(0, pos) + newChar + text.substring(pos + 1);
    }

    private void resetStyleAt(int pos) {
        replaceCharAt(pos, text.charAt(pos));
    }

    private void handleEdit(){
        currentDNA = DNA.fromString(text.replace("\n", ""));
        interactionMatrixDisplay.update(currentDNA);
        attributeDisplay.updateTable();
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.editable = false;
            resetStyleAt(lastClickedPos);
            this.lastClickedPos = -1;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(editable) {
            resetStyleAt(lastClickedPos);
        }
        int pos = textPane.viewToModel2D(e.getPoint());
        if (pos >= 0 && pos < text.length()) {
            char clickedChar = text.charAt(pos);
            lastClickedPos = pos;
            editable = true;

            SimpleAttributeSet newAttr = new SimpleAttributeSet();
            StyleConstants.setForeground(newAttr, Color.BLUE);
            doc.setCharacterAttributes(pos, 1, newAttr, false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
