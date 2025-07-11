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


    private double interactionRadius;
    private double speed;


    private DNA dna;

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

    private final Ecosystem ecosystem;

    private InteractionType[] interactions;

    public Species(DNA dna, Ecosystem ecosystem) {
        this.dna = dna;
        this.color = dna.getColor();
        this.speed = dna.getSpeed() * ecosystem.getSpeedMultiplier();
        this.interactionRadius = dna.getRadius() * ecosystem.getSpeedMultiplier();

        this.ecosystem = ecosystem;

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

    public int getId() {
        return id;
    }

    public Ecosystem getEcosystem() {
        return ecosystem;
    }

    public void setDNA(DNA dna) {
        this.dna = dna;

        this.color = dna.getColor();
        this.speed = dna.getSpeed() * ecosystem.getSpeedMultiplier();
        this.interactionRadius = dna.getRadius() * ecosystem.getSpeedMultiplier();

        ecosystem.updateInteractionMatrix();
    }

    public Object[] getInteractionMatrixRow() {
        return Stream.concat(Stream.of("●"), Arrays.stream(interactions)).toArray();
    }
}
