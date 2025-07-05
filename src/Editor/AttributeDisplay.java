package Editor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class AttributeDisplay extends JPanel {
    private final AttributeDNASite[] attributes;
    private final DefaultTableModel model;

    private static final String[] header = {"Attribute", "Value"};

    public AttributeDisplay(AttributeDNASite[] attributes) {
        this.attributes = attributes;

        this.model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 0) {
                    c.setForeground(attributes[row].getColor());
                } else {
                    c.setForeground(Color.black);
                }
                return c;
            }
        });

        updateTable();

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());

        add(new JLabel("Attributes"), BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);



    }

    public void updateTable() {
        Object[][] data = new Object[attributes.length][];
        for(int i = 0; i < attributes.length; i++) {
            data[i] = attributes[i].getTableRow();
        }
        model.setDataVector(data, header);
    }


}
