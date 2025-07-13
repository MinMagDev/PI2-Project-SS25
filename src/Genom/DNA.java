package Genom;

import Species.Species;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;

import static java.util.Map.entry;


public class DNA {

    private final List<Nucleotide> dna;

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

    /**
     * the index of the first nucleotide relevant for hunger rate calculation
     */
    public static final int HUNGER_DNA_POSITION = 12;

    /**
     * the amount of nucleotides following HUNGER_DNA_POSITION relevant for hunger rate calculation
     */
    public static final int HUNGER_DNA_LENGTH = 6;

    /**
     * the index of the first nucleotide relevant for reproduction probability calculation
     */
    public static final int REPRODUCTION_PROBABILITY_DNA_POSITION = 18;

    /**
     * the amount of nucleotides following REPRODUCTION_PROBABILITY_DNA_POSITION relevant for reproduction probability calculation
     */
    public static final int REPRODUCTION_PROBABILITY_DNA_LENGTH = 6;
  

    public static final int INTERACTION_TYPES_POSITION = 24;


    private static final int MAX_SPEED = 10;
    private static final int MAX_FIELD_RADIUS = 40;
    private static final double MAX_HUNGER = 0.005;

    private static final double ZERO_SPEED_THRESHOLD = 0.3d;

    private final InteractionType[] interactions = new InteractionType[9];


    public DNA() {
        dna = generateRandomDNA(128);
    }

    public DNA(List<Nucleotide> dna) {
        this.dna = dna;
    }


    public DNA(Nucleotide n) {
        dna = new ArrayList<>();
        for (int i = 0; i < 128; i ++){
            dna.add(n);
        }
    }

    public static final Map<Character, Nucleotide> nucleotideDictionary = Map.ofEntries(
            entry('a', Nucleotide.A),
            entry('c', Nucleotide.C),
            entry('g', Nucleotide.G),
            entry('t', Nucleotide.T),
            entry('A', Nucleotide.A),
            entry('C', Nucleotide.C),
            entry('G', Nucleotide.G),
            entry('T', Nucleotide.T)
    );

    public static DNA fromString(String dna) {
        dna = dna.trim();
        List<Nucleotide> nucleotides = new ArrayList<>();

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
    public static List<Nucleotide> generateRandomDNA(int size) {
        List<Nucleotide> result = new LinkedList<>();
        Random r = new Random();
        Nucleotide[] nucVals = Nucleotide.values();
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
      
        if (result <= ZERO_SPEED_THRESHOLD) return 0.0d;
        return result/10;
    }

    /**
     * @return the radius for social interaction
     */
    public double getRadius(){
        return getValue(INTERACTION_RADIUS_DNA_POSITION, INTERACTION_RADIUS_DNA_LENGTH, MAX_FIELD_RADIUS);
    }

    /**
     * @return how fast hunger accumulates
     */
    public double getHunger(){
        return getValue(HUNGER_DNA_POSITION, 6, MAX_HUNGER);
    }

    /**
     * @return the probability of reproduction
     */
    public double getReproductionProbability(){
        return getValue(REPRODUCTION_PROBABILITY_DNA_POSITION,6,1);
    }

    public InteractionType getInteractionWith(Species other) {
        final int speciesID = other.getId();
        if(interactions[speciesID] == null){
            interactions[speciesID] = getInteraction(speciesID);
        }
        return interactions[speciesID];
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

        if (dna.isEmpty()) {
            System.out.println("DNA SIZE IS ZERO, from DNA: " + this);
            return InteractionType.NEUTRAL;
        }
        Nucleotide nuc = dna.get((INTERACTION_TYPES_POSITION + species) % dna.size());
      
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
        int parts = dnaLength/3;
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

    /**
     * Mutates the DNA, and returns it as a new List
     * @param amount the expected amount of mutations
     * @return the newly mutated List
     */
    public List<Nucleotide> mutate(int amount) {
        if (dna.isEmpty()) return new DNA().getDNA();
        double probability = (double) amount/dna.size();
        return mutate(probability);
    }

    public List<Nucleotide> getDNA() {
        return dna;
    }

    /**
     * Mutates the DNA with a given Probability
     * @param probability the probabilty of one Nucleotid to mutate
     */
    public List<Nucleotide> mutate(double probability){
        Nucleotide[] nucVals = Nucleotide.values();
        List<Nucleotide> newDNA = new LinkedList<>();
        Random r = new Random();
        for (Nucleotide nucleotide : dna) {
            if (r.nextDouble() >= probability) {
                newDNA.add(nucleotide);
            } else {
                int nuc = r.nextInt(nucVals.length);
                newDNA.add(nucVals[nuc]);
            }

        }
        return newDNA;
    }

    /** Generates a String represenation of a DNA object
     * @return The String represantation
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Nucleotide nuc: dna){
            result.append(nuc);
        }
        return result.toString();
    }

    public DNA mutated(int amount) {
        return new DNA(this.mutate(amount));
    }

}
