package Editor;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * represents a colored DNA position in the editor
 */
public class InterestingDNASite {
    private final String name;
    private final Color color;
    private final int start;
    private final int length;

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


