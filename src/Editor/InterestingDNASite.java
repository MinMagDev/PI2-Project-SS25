package Editor;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class InterestingDNASite {
    private String name;
    private Color color;
    private int start, length;

    public InterestingDNASite(String name, Color color, int start, int length) {
        this.name = name;
        this.color = color;
        this.length = length;
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public SimpleAttributeSet getAttributeSet() {
        var attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, color);
        return attr;
    }

    public boolean isInterestingNucleotidePosition(int pos) {
        return  pos >= start && pos < start + length;
    }

    @Override
    public String toString() {
        return getName() + " " + getColor().toString() + " " + start + " " + length;
    }
}


