package Editor;

import java.awt.*;
import java.util.function.Supplier;

/**
 * a class representing a dna site which changes a particles attribute
 */

public class AttributeDNASite extends InterestingDNASite {
    private final Supplier<String>  attribute;

    public AttributeDNASite(String name, Color color, int start, int length, Supplier<String> attribute) {
        super(name, color, start, length);
        this.attribute = attribute;
    }

    public Object[] getTableRow(){
        return new String[]{super.getName(), attribute.get()};
    }
}
