package Editor;

import Genom.DNA;
import Species.Ecosystem;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpeciesInteractionNucleotidePosition extends InterestingDNASite {

    public SpeciesInteractionNucleotidePosition(int speciesID, Color color) {
        super("interaction", color, DNA.INTERACTION_TYPES_POSITION + speciesID, 1);
    }

    public static List<SpeciesInteractionNucleotidePosition> fromEcosystem(Ecosystem ecosystem) {
        List<SpeciesInteractionNucleotidePosition> sites = new ArrayList<>();
        ecosystem.forEachSpecies(species -> {
            sites.add(new SpeciesInteractionNucleotidePosition(species.getId(), species.getColor()));
        });
        return sites;
    }

    @Override
    public SimpleAttributeSet getAttributeSet() {
        var attr = super.getAttributeSet();
        StyleConstants.setBold(attr, true);
        return attr;
    }
}
