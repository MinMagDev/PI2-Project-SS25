package Cluster;

import Genom.DNA;
import Species.Species;

public interface Specimen {
    DNA getDNA();
    default void updateSpecies(Species species) {}
}
