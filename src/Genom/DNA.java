package Genom;

import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DNA {
    private List<Nucleotid> dna;

    private final int SPEED_POSITION = 0;
    private final int FORCEFIELD_RADIUS_POSITION = 6;
    private final int INTERACTION_POSITION = 12;

    private final int MAX_SPEED = 10;
    private final int MAX_FIELD_RADIUS = 40;

    private final double ZERO_SPEED_THREASHOLD = 0.3d;


    public DNA() {
        dna = generateRandomDNA(128);
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
        double result = 0.0d;
        result += getIntValue(SPEED_POSITION, SPEED_POSITION+6);
        result *= MAX_SPEED/(double)getMaxValue(6);
        if (result <= ZERO_SPEED_THREASHOLD) return 0.0d;
        return result/10;
    }

    /**
     * Returns the Radius for the Force Field from the dna
     * @return forcefield Radius
     */
    public double getRadius(){
        double result = 0.0d;
        result += getIntValue(FORCEFIELD_RADIUS_POSITION, FORCEFIELD_RADIUS_POSITION+6);
        result *= MAX_FIELD_RADIUS / (double)getMaxValue(6);
        return result;
    }

    public int getIntValue(int start, int end){
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

    public void mutate(int amount) {
        double probabilty = amount/dna.size();
        mutate(probabilty);
    }

    /**
     * Mutates the DNA with a given Probability
     * @param probability the probabilty of one Nucleotid to mutate
     */
    public void mutate(double probability){
        Nucleotid[] nucVals = Nucleotid.values();
        Random r = new Random();
        for (int i = 0; i < dna.size(); i++){
            if(r.nextDouble() >= probability) continue;
            int nuc = r.nextInt(nucVals.length + 1);
            if(nuc == nucVals.length) {
                dna.remove(i);
                continue;
            }
            dna.set(i, nucVals[nuc]);
        }
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
