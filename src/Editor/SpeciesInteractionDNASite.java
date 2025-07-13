package Editor;

import Genom.DNA;
import Species.Ecosystem;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * represents a DNA position changing how this particle interacts with other particles
 */

public class SpeciesInteractionDNASite extends InterestingDNASite {

    public SpeciesInteractionDNASite(int speciesID, Color color) {
        super("interaction", color, DNA.INTERACTION_TYPES_POSITION + speciesID, 1);
    }

    public static List<SpeciesInteractionDNASite> fromEcosystem(Ecosystem ecosystem) {
        List<SpeciesInteractionDNASite> sites = new ArrayList<>();

        ecosystem.forEachSpecies(species -> sites.add(new SpeciesInteractionDNASite(species.getId(), species.getColor())));

        return sites;
    }

    @Override
    public SimpleAttributeSet getAttributeSet() {
        var attr = super.getAttributeSet();
        StyleConstants.setBold(attr, true);
        return attr;
    }
}
