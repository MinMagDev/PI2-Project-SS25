package Genom;

import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DNA {
    private List<Nucleotid> dna;

    private final int SPEED_POSITION = 0;
    private final int FORCEFIELD_RADIUS_POSITION = 6;
    private final int INTERACTION_POSITION = 24;
    private final int HUNGER_POSITION = 12;
    private final int REPRO_PROBABILITY_POSITION = 18;

    private final int MAX_SPEED = 10;
    private final int MAX_FIELD_RADIUS = 40;
    private final double MAX_HUNGER = 0.005;

    private final double ZERO_SPEED_THREASHOLD = 0.3d;


    public DNA() {
        dna = generateRandomDNA(128);
    }
    public DNA(List<Nucleotid> dna){
        this.dna = dna;
    }

    /**
     * Generates a new random DNA-Strang
     * @param size The length of the strang
     * @return The new DNA-Strang
     */
    public static List<Nucleotid> generateRandomDNA(int size) {
        List<Nucleotid> result = new LinkedList<Nucleotid>();
        Random r = new Random();
        Nucleotid[] nucVals = Nucleotid.values();
        for (int i = 0; i < size; i++) {
            int nuc = r.nextInt(4);
            result.add(nucVals[nuc]);
        }
        return result;
    }

    /**
     * Reads the Speed from the dna
     * @return speeeed
     */
    public double getSpeed(){
        double result = getValue(SPEED_POSITION,6,MAX_SPEED);
        if (result <= ZERO_SPEED_THREASHOLD) return 0.0d;
        return result/10;
    }

    /**
     * Returns the Radius for the Force Field from the dna
     * @return forcefield Radius
     */
    public double getRadius(){
        return getValue(FORCEFIELD_RADIUS_POSITION, 6, MAX_FIELD_RADIUS);
    }

    /**
     * Returns how fast hunger accumulates
     * @return
     */
    public double getHunger(){
        return getValue(HUNGER_POSITION, 6, MAX_HUNGER);
    }

    /**
     * Returns the probability of reproductin
     * @return
     */
    public double getReproductionProbability(){
        return getValue(REPRO_PROBABILITY_POSITION,6,1);
    }


    private double getValue(int start, int length, double max){
        double result = 0.0d;
        result += getIntValue(start, start+length);
        result *= max / (double)getMaxValue(length);
        return result;
    }

    private int getIntValue(int start, int end){
        if (end > dna.size()) return 0;
        int result = 0;
        for(int i = start; i < end; i++){
            result += dna.get(i).ordinal();
        }
        return result;
    }

    /**
     * Returns how the species should interact to other species
     * @param species The Species to interact with
     * @return -1: Flee, 0: ignore, 1: Hunt
     */
    public InteractionType getInteraction(int species){
        System.out.println("Species to get Interaction: " + species);
        Nucleotid nuc = dna.get(INTERACTION_POSITION + species);
        switch (nuc) {
            case A -> {
                return InteractionType.REPEL;
            }
            case C -> {
                return InteractionType.NEUTRAL;
            }
            case G -> {
                return InteractionType.NEUTRAL;
            }
            case T -> {
                return InteractionType.ATTRACT;
            }
        }
        return InteractionType.NEUTRAL;
    }

    /**
     * Gets the color of the particle
     * @return The color of the particle
     */
    public Color getColor(){
        int dnaLength = dna.size();
        int parts = (int)dnaLength/3;
        float h = getIntValue(0,parts);
        float s = getIntValue(parts, 2*parts);
        float b = getIntValue(2*parts,dnaLength);
        float maxVal = getMaxValue(parts);
        h =  ((h/maxVal) * 255);
        s = ((s/maxVal) * 255);
        b = (b/maxVal) * 255;
        return new Color(Color.HSBtoRGB(h,s,b));
    }

    public static int getMaxValue(int pow) {
        return 3*pow;
    }

    public List<Nucleotid> mutate(int amount) {
        if (dna.size() == 0) return new DNA().getDNA();
        double probabilty = amount/dna.size();
        return mutate(probabilty);
    }

    public List<Nucleotid> getDNA() {
        return dna;
    }

    /**
     * Mutates the DNA with a given Probability
     * @param probability the probabilty of one Nucleotid to mutate
     */
    public List<Nucleotid> mutate(double probability){
        Nucleotid[] nucVals = Nucleotid.values();
        List<Nucleotid> newDNA = new LinkedList<Nucleotid>();
        Random r = new Random();
        for (int i = 0; i < dna.size(); i++){
            if(r.nextDouble() >= probability) continue;
            int nuc = r.nextInt(nucVals.length + 1);
            if(nuc == nucVals.length) {
                continue;
            }
            newDNA.add(nucVals[nuc]);
        }
        return newDNA;
    }

    /** Generates a String represenation of a DNA object
     * @return The String represantation
     */
    @Override
    public String toString() {
        String result = "";
        for (Nucleotid nuc: dna){
            result += nuc;
        }
        return result;
    }

    public void print(){
        System.out.println(this);
    }
}
