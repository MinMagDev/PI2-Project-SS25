package Genom;

import Species.Species;
import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.util.*;
import java.util.List;

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
    public DNA(List<Nucleotid> dna){
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
        return getValue(INTERACTION_RADIUS_DNA_POSITION,
                INTERACTION_RADIUS_DNA_LENGTH,
                MAX_FIELD_RADIUS);
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

    /**
     * Returns the Interaction of the Particle to another particle.
     * @param other The Species of the Particle to interact with
     * @return The Type of Interaction read out of DNA
     */
    public InteractionType getInteractionWith(Species other) {
        return getInteraction(other.getId());
    }


    /**
     * Calculates a normalized double value from a subsequence of DNA elements.
     * <p>
     * The method first extracts an integer value from a segment of the DNA,
     * based on the ordinal values of its elements. It then scales this value
     * proportionally to the given `max`, resulting in a double between 0 and `max`.
     * <p>
     * It's basically like squeezing meaning out of bytes — except the meaning is
     * made up, and the points don't matter.
     *
     * <p>D.R.E.C.K</p>
     *
     * @param start The index in the DNA sequence where the extraction begins.
     * @param length How many DNA elements to consider.
     * @param max The upper bound to scale the value to.
     * @return A double between 0 and `max` — probably. Unless something weird happens.
     */
    private double getValue(int start, int length, double max){
        double result = 0.0d;
        result += getIntValue(start, start+length);
        result *= max / (double)getMaxValue(length);
        return result;
    }


    /**
     * D.R.E.C.K.: Converts a segment of DNA bases to an integer value.
     *<p>
     * Iterates from the start to the end index (exclusive), and accumulates
     * the ordinal values of the DNA bases. If the end index exceeds the DNA
     * length, it returns 0 to avoid errors. (How generous!)[1]
     *<p>
     * [1] Yes Chat, not only makes Comments how it functions, but also how he finds
     * it - Levi
     * <p>
     * @param start The starting index in the DNA sequence.
     * @param end The exclusive end index.
     * @return The accumulated ordinal value of the DNA bases.
     */
    private int getIntValue(int start, int end){
        int result = 0;
        for(int i = start; i < end; i++){
            result += dna.get(i).ordinal();
        }
        return result;
    }

    /**
     * Returns how the species should interact to other species
     * @param species The Species to interact with
     * @return The Interaction to the Species as {@link InteractionType}
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

    /**
     * Applies probabilistic mutation to the DNA sequence.
     * <p>
     * Each nucleotide has a chance — defined by `probability` — to mutate.
     * Mutation is modeled as either:
     * <ul>
     *     <li>Replacement: The base is swapped with a randomly chosen new base.</li>
     *     <li>Deletion (frameshift): The base is simply skipped, effectively shortening the DNA.</li>
     *     <li>Addition: Another Base is added after the Base, effectively longening the DNA</li>
     * </ul>
     * This behavior is achieved by randomly selecting one of `n + 2` mutation options, with the
     * last two options, adding or deleting a Base
     * <p>
     * This can lead to frame shifts, chaos, and occasional enlightenment.
     * <p>
     * Debug outputs are provided for your existential debugging needs.
     *
     * @param amount How many mutations are expected to happen (Will just call mutate with amount/dna.length)
     * @return A mutated version of the original DNA sequence. May be shorter. May be cursed.
     * <p> Human edited D.R.E.C.K.
     */
    public List<Nucleotid> mutate(int amount) {
        if (dna.size() == 0) return new DNA().getDNA();
        double probabilty = (double)amount/dna.size();
        return mutate(probabilty);
    }

    public List<Nucleotid> getDNA() {
        return dna;
    }

    /**
     * Applies probabilistic mutation to the DNA sequence.
     * <p>
     * Each nucleotide has a chance — defined by `probability` — to mutate.
     * Mutation is modeled as either:
     * <ul>
     *     <li>Replacement: The base is swapped with a randomly chosen new base.</li>
     *     <li>Deletion (frameshift): The base is simply skipped, effectively shortening the DNA.</li>
     *     <li>Addition: Another Base is added after the Base, effectively longening the DNA</li>
     * </ul>
     * This behavior is achieved by randomly selecting one of `n + 2` mutation options, with the
     * last two options, adding or deleting a Base
     * <p>
     * This can lead to frame shifts, chaos, and occasional enlightenment.
     * <p>
     * Debug outputs are provided for your existential debugging needs.
     *
     * @param probability The chance that a given nucleotide mutates (0.0 to 1.0).
     * @return A mutated version of the original DNA sequence. May be shorter. May be cursed.
     * <p> Human edited D.R.E.C.K.
     */
    public List<Nucleotid> mutate(double probability){
        Nucleotid[] nucVals = Nucleotid.values();
        List<Nucleotid> newDNA = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < dna.size(); i++){
            double rD = r.nextDouble();
//            System.out.print(
//                    "Nucleotid " + i + ": rd is "
//                            + rD + " and probability is "
//                    + probability + "; "
//            );
            if(rD >= probability) {
                newDNA.add(dna.get(i));
                continue;
            }
            System.out.print("Mutate, YAY ");
            int nuc = r.nextInt(nucVals.length + 2);
            System.out.println("Random Chosen: " + nuc);
            if(nuc == nucVals.length) {
                continue;
            }else if(nuc > nucVals.length){
                newDNA.add(dna.get(i));
                newDNA.add(nucVals[r.nextInt(nucVals.length)]);
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
