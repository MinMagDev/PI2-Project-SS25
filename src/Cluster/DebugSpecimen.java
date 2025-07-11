package Cluster;

import Genom.DNA;
import Species.*;

import java.util.ArrayList;
import java.util.List;

public class DebugSpecimen implements Specimen {

    @Override
    public DNA getDNA() {
        return dna;
    }

    public void setDNA(DNA dna) {
        this.dna = dna;
    }

    private DNA dna;

    public DebugSpecimen(Species species){
        this.dna = species.getDNA();
    }

    void mutate(int amount){
        setDNA(dna.mutated(amount));
    }

    public DataPoint dataPoint(){
        return new DataPoint(dna);
    }

    static void makeSpecimens(int nSpecies, int nSpecimens, boolean mutated, int muatationAmnt, List<Species> species, List<DebugSpecimen> specimens) {
        Ecosystem ecosystem = Ecosystem.createExampleEcosystem(nSpecies);
        ecosystem.forEachSpecies((aSpecies) -> {
            species.add(aSpecies);
            for(int i = 0; i < nSpecimens; i++){
                DebugSpecimen debugSpecimen = new DebugSpecimen(aSpecies);
                if(mutated){
                    debugSpecimen.mutate(muatationAmnt);
                }
                specimens.add(debugSpecimen);
            }
        });

    }

    static List<DebugSpecimen> makeSpecimens(int n, int nSpecies, boolean mutated, int muatationAmnt){
        List<DebugSpecimen> specimens = new ArrayList<>();
        List<Species> species = new ArrayList<>();
        makeSpecimens(nSpecies, n, mutated, muatationAmnt, species, specimens);
        return specimens;
    }

}
