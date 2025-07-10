package Species;

import Genom.DNA;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Ecosystem {
    private final List<Species> species = new ArrayList<Species>();

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    private double speedMultiplier;

    public double getSocialRadiusMultiplier() {
        return socialRadiusMultiplier;
    }

    public void setSocialRadiusMultiplier(double socialRadiusMultiplier) {
        this.socialRadiusMultiplier = socialRadiusMultiplier;
    }

    private double socialRadiusMultiplier;


    void addSpecies(Species species){
        this.species.add(species);
    }
    public int getSpeciesCount(){
        return species.size();
    }
    public void updateInteractionMatrix(){
        final int speciesCount = getSpeciesCount();

        for(Species species : species){
            species.updateInteractions(speciesCount);
        }
    }
    public void forEachSpecies(Consumer<Species> consumer) {
        species.forEach(consumer);
    }

    public static Ecosystem createExampleEcosystem(int speciesCount){
        Ecosystem ecosystem = new Ecosystem();
        for(int i = 0; i < speciesCount; i++){
         new Species(new DNA(), ecosystem);
        }
        return ecosystem;
    }

    public Species getSpecies(int id){
        return species.get(id);
    }

    public void clearSpecies() {
        species.clear();
    }
}
