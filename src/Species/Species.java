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

    private InteractionType[] interactions;

    public Species(DNA dna, Ecosystem ecosystem) {
        this.dna = dna;
        this.color = dna.getColor();
        this.speed = dna.getSpeed() * ecosystem.getSpeedMultiplier();
        this.interactionRadius = dna.getRadius() * ecosystem.getSpeedMultiplier();

        ecosystem.addSpecies(this);
        id = ecosystem.getSpeciesCount() - 1;

        ecosystem.updateInteractionMatrix();
    }

    private final int id;

    public InteractionType getInteractionWith(Species other) {
        return interactions[other.id];
    }

    void updateInteractions(int newSpeciesCount) {
        interactions = new InteractionType[newSpeciesCount];
        for(int i = 0; i < interactions.length; i++) {
            interactions[i] = dna.getInteraction(i);
        }
    }

    public double getSpeed() {
        return speed;
    }

    public double getInteractionRadius(){
        return interactionRadius;
    }
}
