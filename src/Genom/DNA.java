package Genom;

import Species.Species;
import org.w3c.dom.css.RGBColor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;

import static java.util.Map.entry;


public class DNA {
    private List<Nucleotid> dna;
    /**
     * the index of the first nucleotide relevant for speed value calculation
     */
    public static final int SPEED_DNA_POSITION = 0;
    /**
     * the amount of nucleotides following SPEED_DNA_POSITION relevant for speed value calculation
     */
    public static final int SPEED_DNA_LENGTH = 6;
    /**
     * the index of the first nucleotide relevant for interaction radius calculation
     */
    public static final int INTERACTION_RADIUS_DNA_POSITION = 6;
    /**
     * the amount of nucleotides following SPEED_DNA_POSITION relevant for interaction radius calculation
     */
    public static final int INTERACTION_RADIUS_DNA_LENGTH = 6;
  
  // TODO: add to editor
    private final int HUNGER_POSITION = 12;
    private final int REPRO_PROBABILITY_POSITION = 18;
  

    public static final int INTERACTION_TYPES_POSITION = 24;


    private final int MAX_SPEED = 10;
    private final int MAX_FIELD_RADIUS = 40;
    private final double MAX_HUNGER = 0.005;

    private final double ZERO_SPEED_THREASHOLD = 0.3d;

    private InteractionType[] interactions;


    public DNA() {
        dna = generateRandomDNA(128);
    }

    public DNA(List<Nucleotid> dna) {
        this.dna = dna;
    }

    public static final Map<Character, Nucleotid> nucleotideDictionary = Map.ofEntries(
            entry('a', Nucleotid.A),
            entry('c', Nucleotid.C),
            entry('g', Nucleotid.G),
            entry('t', Nucleotid.T),
            entry('A', Nucleotid.A),
            entry('C', Nucleotid.C),
            entry('G', Nucleotid.G),
            entry('T', Nucleotid.T)
    );

    public static DNA fromString(String dna) {
        dna = dna.trim();
        List<Nucleotid> nucleotides = new ArrayList<>();

        for(char c: dna.toCharArray()) {
            if(!nucleotideDictionary.containsKey(c)) {
                throw new IllegalArgumentException("Illegal Character: " + c);
            }
            nucleotides.add(nucleotideDictionary.get(c));
        }

        return new DNA(nucleotides);
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
        double result = getValue(SPEED_DNA_POSITION, SPEED_DNA_LENGTH, MAX_SPEED);
      
        if (result <= ZERO_SPEED_THREASHOLD) return 0.0d;
        return result/10;
    }

    /**
     * Returns the Radius for the Force Field from the dna
     * @return forcefield Radius
     */
    public double getRadius(){
        return getValue(INTERACTION_RADIUS_DNA_POSITION, INTERACTION_RADIUS_DNA_LENGTH, MAX_FIELD_RADIUS);
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

    public InteractionType getInteractionWith(Species other) {
        return getInteraction(other.getId());
    }
  /**
     * returns a scaled value corresponding to a specific sequence of DNA
     * @param start the first nucleotide to be considered
     * @param length the amount of nucleotides to be considered
     * @param max the maximum allowed value
     * @return the corresponding double
     */
  
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
     * @return interaction type
     */
    public InteractionType getInteraction(int species){

        if (dna.size() == 0) {
            System.out.println("DNA SIZE IS ZERO, from DNA: " + this);
            return InteractionType.NEUTRAL;
        }
        Nucleotid nuc = dna.get((INTERACTION_TYPES_POSITION + species) % dna.size());
      
        switch (nuc) {
            case A -> {
                return InteractionType.REPEL;
            }
            case C -> {
                return InteractionType.NEUTRAL;
            }
            case G -> {
                return InteractionType.SPRING;
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
            if(r.nextDouble() >= probability) {
                newDNA.add(dna.get(i));
                continue;
            }
            int nuc = r.nextInt(nucVals.length + 1);
            System.out.println("Random Chosen: " + nuc);
            if(nuc == nucVals.length) {
                continue;
            }
            newDNA.add(nucVals[nuc]);
        }
        System.out.println("New DNA: " + newDNA);
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
