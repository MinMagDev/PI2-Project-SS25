package Editor;

import javax.swing.*;

public class AttributeLabel extends JLabel {
    private final String attribute;

    public AttributeLabel(String attribute, String initalValue) {
        this.attribute = attribute;
        this.setText(initalValue);
    }

    @Override
    public void setText(String text) {
        super.setText(attribute + ": " + text);
    }
}
