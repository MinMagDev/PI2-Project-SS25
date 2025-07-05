package Editor;

import Genom.DNA;
import Species.Ecosystem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Arrays;

public class InteractionMatrixDisplay extends JPanel {
    Object[][] interactionMatrix;
    Ecosystem ecosystem;

    private final DefaultTableModel model;

    private static final Font bigCirlceFont = new Font("Arial", Font.PLAIN, 30);


    public InteractionMatrixDisplay(Ecosystem ecosystem, DNA dna) {
        this.ecosystem = ecosystem;

        this.model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        updateTable(dna);

        JTable table = new JTable(model);



        int cellSize = 50;
        table.setRowHeight(cellSize);


        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    JLabel label = (JLabel) c;
                    label.setForeground(ecosystem.getSpecies(column).getColor());
                    label.setFont(bigCirlceFont);
                    return c;

            }
        });
        header.setReorderingAllowed(false);

        JScrollPane scrollPanel = new JScrollPane(table);

        scrollPanel.setPreferredSize(new Dimension(400, 100));

        setLayout(new BorderLayout());

        add(new JLabel("Interactions"), BorderLayout.NORTH);

        add(scrollPanel, BorderLayout.CENTER);
    }

    private void updateTable(DNA dna) {
        Object[] header = createTableHeader(ecosystem.getSpeciesCount());
        this.interactionMatrix = new Object[1][header.length];
        ecosystem.forEachSpecies(species -> {
            interactionMatrix[0][species.getId()] = dna.getInteraction(species.getId()).toString();
        });

        model.setDataVector(interactionMatrix, header);
    }


    public void update(DNA dna) {
        if(ecosystem != null) {
            updateTable(dna);
        }
        super.repaint();
    }

    private static String[] createTableHeader(int length){
        String[] result = new String[length];
        Arrays.fill(result, "●");
        return result;
    }

}
