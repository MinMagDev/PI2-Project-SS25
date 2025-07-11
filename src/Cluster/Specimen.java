package Cluster;

import Genom.DNA;

public interface Specimen {
    DNA getDNA();
    default void updateColor() {}
}
