package Species;

import java.util.ArrayList;
import java.util.List;

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


    public void addSpecies(Species species){
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





}
