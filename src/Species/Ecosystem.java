package Species;

import Cluster.*;
import Genom.DNA;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * manages a simulation's species
 */

public class Ecosystem {
    private final List<Species> species = new ArrayList<Species>();

    public double getSpeedMultiplier() {
        System.out.println("SpeedMultiplier" + speedMultiplier);
        return speedMultiplier;
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
        System.out.println(" set Speed Multiplier: " + speedMultiplier);
    }

    private double speedMultiplier;

    public double getInteractionRadiusMultiplier() {
        System.out.println(" get Interaction Radius Multiplier: " + interactionRadiusMultiplier);
        return interactionRadiusMultiplier;
    }

    public void setInteractionRadiusMultiplier(double interactionRadiusMultiplier) {
        this.interactionRadiusMultiplier = interactionRadiusMultiplier;
        System.out.println("set Interaction Radius Multiplier: " + interactionRadiusMultiplier);
    }

    private double interactionRadiusMultiplier;

    void addSpecies(Species species){
        this.species.add(species);
    }

    public int getSpeciesCount(){
        return species.size();
    }

    public void forEachSpecies(Consumer<Species> consumer) {
        species.forEach(consumer);
    }

    public Species getSpecies(int id){
        return species.get(id);
    }

    public void updateSpecies(List<? extends Specimen> specimens){
        Run run = KMeans.run(specimens, species);

        System.out.println("UPDATE");


        List<ClusterCentroid> centroids = new ArrayList<>(Arrays.asList(run.centroids));
        int i = 0;



        System.out.println("centroids size: " + centroids.size());
        System.out.println("species size: " + species.size());

        // trim species list
        while(species.size() > centroids.size()){
            species.removeLast();
        }

        // update current species
        while(!centroids.isEmpty()){
            System.out.println("–––––––––––––––");
            DNA newDNA;
            Species currentSpecies;
            ClusterCentroid centroid;
            if(i < species.size()){
                currentSpecies = species.get(i);
                System.out.println("old DNA: " + coloredDNAText(currentSpecies.getDNA()));
                centroid = new GenPoint(currentSpecies.getDNA()).calculateNearestClusterCentroid(centroids.toArray(new ClusterCentroid[0]));
                centroids.remove(centroid);
                newDNA = centroid.toDNA();
                currentSpecies.setDNA(newDNA);
            } else {
                centroid = centroids.removeFirst();
                newDNA = centroid.toDNA();
                currentSpecies = new Species(newDNA, this);
            }
            System.out.println("new DNA: " + coloredDNAText(currentSpecies.getDNA()));
            Arrays.stream(centroid.getClusteredPoints()).forEach(point -> point.updateSpecies(currentSpecies));
            System.out.println("–––––––––––––––");
            i++;
        }

    }

    public static String rgbText(int r, int g, int b, String text) {
        return String.format("\u001B[38;2;%d;%d;%dm%s\u001B[0m", r, g, b, text);
    }

    public static String colorText(Color color, String text) {
        return rgbText(color.getRed(), color.getGreen(), color.getBlue(), text);
    }

    public static String coloredDNAText(DNA dna) {
        return colorText(dna.getColor(), dna.toString());
    }

}
