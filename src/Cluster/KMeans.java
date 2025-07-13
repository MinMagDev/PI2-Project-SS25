package Cluster;

import Genom.DNA;
import Species.Species;
import Species.SpeciesParticle;
import Species.Ecosystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMeans<S extends Specimen> {


    private final List<S> specimens;
    private final List<Species> species;

    public KMeans(List<S> specimens, List<Species> species){
        this.specimens = specimens;
        this.species = species;
    }

    public List<Species> getSpecies() {
        return species;
    }

    final static int MAX_K = 9;

    public Run run(){

       Run[] allRuns = new Run[15];

        int bestK = 2;
        float bestScore = -1f;
        float currentScore;
        for (int k = 2; k <= MAX_K; k++){
            System.out.println("K is " + k);
            //Setup
            GenPoint[] genPoints = new GenPoint[specimens.size()];
            ClusterCentroid[] centroids = new ClusterCentroid[k];
            for (int i = 0; i < genPoints.length; i++){
                var specimen = specimens.get(i);
                genPoints[i] = new GenPoint(specimen.getDNA(), specimen::updateSpecies);
            }
            System.out.println("init centroids");

            for (int i = 0; i < k; i++) {
                if(i < species.size()){
                    centroids[i] = new ClusterCentroid(species.get(i).getDNA(), specimens.size(), i);
                } else {
                    centroids[i] = new ClusterCentroid(specimens.get(i % specimens.size()).getDNA(), specimens.size(), i);
                }

                System.out.println(centroids[i].toDNA());
            }

            //Clustering
            Run run = run(genPoints, centroids);;
            currentScore = Silhouette.meanSilhouette((GenPoint[]) run.dataPoints, (ClusterCentroid[]) run.centroids);
            allRuns[k] = run;

            System.out.println("k = " + k + " with silhouette of: " + currentScore);
            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestK = k;
            }
        }

        System.out.println("Best k found: " + bestK);
        return allRuns[bestK];
    }


    public static Run run(GenPoint[] genPoints, ClusterCentroid[] centroids){
        int i = 0;
        boolean next = true;
        boolean end  = false;
        while (next) {
            System.out.println("Iteration: " + i);
            System.out.println();

            if (end) next = false;
            end = !iterate(genPoints,centroids);
            if (!end) next = true;
            i++;
        }
        return new Run(genPoints,centroids);
    }

    private static boolean iterate(GenPoint[] genPoints, ClusterCentroid[] centroids) {
        for (GenPoint point : genPoints) {
            ClusterCentroid old = point.getNearestClusterCentroid();
            ClusterCentroid nearest = point.calculateNearestClusterCentroid(centroids);
            if (old == nearest) continue;
            nearest.addPointToCluster(point);
            if (old == null) continue;
            old.removePointFromCluster(point);
        }

        for (int i = 0; i < centroids.length; i++) {
            System.out.println("Cluster " + i + " size: " + centroids[i].getClusteredPoints().length);
        }

        boolean changed = true;

        for (ClusterCentroid centroid: centroids) {
            if (!centroid.updatePosition()) changed = false;
        }

        return changed;
    }

    public static <T extends Specimen> Run run(List<T> specimens, List<Species> species){
        return new KMeans<T>(specimens, species).run();
    }
}
