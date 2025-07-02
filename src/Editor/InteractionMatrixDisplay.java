package Editor;

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

    public InteractionMatrixDisplay(Ecosystem ecosystem) {
        this.ecosystem = ecosystem;


        this.model = new DefaultTableModel();

        updateTable();

        JTable table = new JTable(model);



        int cellSize = 50;
        table.setRowHeight(cellSize);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (column == 0) {
                    JLabel label = (JLabel) c;
                    label.setForeground(ecosystem.getSpecies(row).getColor());
                    label.setFont(bigCirlceFont);
                } else {
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column > 0) {
                    JLabel label = (JLabel) c;
                    label.setForeground(ecosystem.getSpecies(column - 1).getColor());
                    label.setFont(bigCirlceFont);
                    return c;
                }
                return new JLabel("");

            }
        });



        add(new JScrollPane(table));
    }

    private void updateTable() {
        Object[] header = createTableHeader(ecosystem.getSpeciesCount() + 1);
        this.interactionMatrix = new Object[ecosystem.getSpeciesCount()][header.length];
        ecosystem.forEachSpecies(species -> {
            interactionMatrix[species.getId()] = species.getInteractionMatrixRow();
        });

        model.setDataVector(interactionMatrix, header);
    }

    @Override
    public void repaint() {
        if(ecosystem != null) {
            updateTable();
        }
        super.repaint();
    }

    private static String[] createTableHeader(int length){
        String[] result = new String[length];
        Arrays.fill(result, "●");
        return result;
    }

}
