package Species;

import Genom.DNA;
import Canvas.*;
import Genom.InteractionType;
import Particle.Particle;
import Social.SocialParticleRenderer;
import World.World;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Species {
    private static final List<Species> species = new ArrayList<Species>();

    public static void cleanUpSpecies(){
        species.clear();
    }

    private final double interactionRadius, speed;


    private final DNA dna;

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;

    public Color getColor() {
        return color;
    }

    public DNA getDNA() {
        return dna;
    }

    private final Map<Integer, InteractionType> interactions;

    public Species(DNA dna) {
        this.dna = dna;
        this.color = dna.getColor();
        this.interactions = new HashMap<>();
        this.speed = dna.getSpeed();
        this.interactionRadius = dna.getRadius();
        species.add(this);
        id = species.size();

        for (Species s : species) {
            s.updateInteractions();
        }
    }

    private final int id;

    public InteractionType getInteractionWith(Species other) {
        return interactions.get(other.id);
    }

    void updateInteractions(){
        for(int i = 0; i < species.size(); i++) {
            interactions.put(species.get(i).id, dna.getInteraction(i));
        }
    }

    public double getSpeed() {
        return speed;
    }

    public double getInteractionRadius(){
        return interactionRadius;
    }
}
