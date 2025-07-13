package Species;

import Genom.DNA;
import java.awt.*;


public class Species {

    private double interactionRadiusMultiplier, speedMultiplier;

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public double getInteractionRadiusMultiplier(){
        return interactionRadiusMultiplier;
    }

    private DNA dna;

    public DNA getDNA() {
        return dna;
    }

    public void setDNA(DNA dna) {
        this.dna = dna;
        this.color = dna.getColor();
        this.speedMultiplier = ecosystem.getSpeedMultiplier();
        this.interactionRadiusMultiplier = ecosystem.getInteractionRadiusMultiplier();
    }

    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }


    private final Ecosystem ecosystem;

    public Ecosystem getEcosystem() {
        return ecosystem;
    }



    public Species(DNA dna, Ecosystem ecosystem) {
        this.dna = dna;
        this.color = dna.getColor();

        this.speedMultiplier = ecosystem.getSpeedMultiplier();
        this.interactionRadiusMultiplier = ecosystem.getInteractionRadiusMultiplier();

        this.ecosystem = ecosystem;

        ecosystem.addSpecies(this);
        id = ecosystem.getSpeciesCount() - 1;
    }

    private final int id;

    public int getId() {
        return id;
    }

}
