package Cluster;

import Genom.DNA;
import Species.Species;

/**
 * the interface between the SpeciesParticle class and the clustering algorithm
 */

public interface Specimen {

    DNA getDNA();

    default void updateSpecies(Species species) {}
}
