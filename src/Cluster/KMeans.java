package Cluster;

import Genom.DNA;
import Species.Species;
import Species.SpeciesParticle;
import Species.Ecosystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMeans<S extends Specimen> {

    /**
     * saves the result of a completed run
     * @param dataPoints the data points
     * @param centroids the cluster centroids
     */
    public record Run(DataPoint[] dataPoints, DataPoint[] centroids) {}

    private final List<S> specimens;
    private final List<Species> species;
    private final List<Species> particleSpecies;

    public KMeans(List<S> specimens, List<Species> species){
        this.specimens = specimens;
        this.species = species;
        particleSpecies = new ArrayList<>();
    }

    public List<Species> getSpecies() {
        return species;
    }

    public List<Species> getParticleSpecies() {
        return particleSpecies;
    }



    final static int MAX_K = 9;

    public void run(){

       Run[] allRuns = new Run[15];

        int bestK = 2;
        float bestScore = -1f;
        float currentScore;
        for (int k = 2; k <= MAX_K; k++){
            System.out.println("K is " + k);
            //Setup
            GenPoint[] genPoints = new GenPoint[specimens.size()];
            ClusterCentroid[] centroids = new ClusterCentroid[species.size()];
            for (int i = 0; i < genPoints.length; i++){
                genPoints[i] = new GenPoint(specimens.get(i).getDNA());
            }
            System.out.println("init centroids");

            for (int i = 0; i < centroids.length; i++) {
                centroids[i] = new ClusterCentroid(species.get(i).getDNA(), specimens.size(), i);
                System.out.println(centroids[i].toDNA());
            }

            //Clustering
            Run run;
            if (k < centroids.length){
                ClusterCentroid[] fewCentroids = new ClusterCentroid[k];
                for (int i = 0; i < k; i++){
                    fewCentroids[i] = centroids[i];
                }
               run = run(genPoints, fewCentroids);
            }else if( k == centroids.length){
                run = run(genPoints, centroids);
            } else {
                run = run(genPoints, centroids, k);
            }
            currentScore = Silhouette.meanSilhouette((GenPoint[]) run.dataPoints, (ClusterCentroid[]) run.centroids);
            allRuns[k] = run;

            System.out.println("k = " + k + " with silhouette of: " + currentScore);
            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestK = k;
            }
        }
        updateParticles(allRuns[bestK]);

        System.out.println("Best k found: " + bestK);
    }

    public void updateParticles(Run run) {
        /*Ecosystem ecosystem = species.get(0).getEcosystem();
        species.clear();
        for (DataPoint c: run.centroids){
            ClusterCentroid centroid = (ClusterCentroid) c;
            System.out.println("Add Cluster");
            Species newSpecies = new Species(centroid.toDNA(), ecosystem);
            species.add(newSpecies);
        }

        particleSpecies.clear();
        for (DataPoint point: run.dataPoints) {
            int pointClusterID = point.getNearestClusterCentroid().getClusterID();
            System.out.println("pointClusterID: " + pointClusterID);
            System.out.println("Species length: " + species.size());
            particleSpecies.add(species.get(pointClusterID));
        }*/

        System.out.println("UPDATE");

        Ecosystem ecosystem;

        if(species.isEmpty()){
            throw new IllegalStateException("species list is empty");
        } else {
            ecosystem = species.getFirst().getEcosystem();
        }

        List<ClusterCentroid> centroids = new ArrayList<>(Arrays.asList((ClusterCentroid[]) run.centroids));
        int i = 0;


        System.out.println("centroids size: " + centroids.size());
        System.out.println("species size: " + species.size());

        // trim species list
        while(species.size() > centroids.size()){
            species.removeFirst();
        }


        // update current species
        while(!centroids.isEmpty()){
            System.out.println("–––––––––––––––");

            DNA newDNA;
            Species currentSpecies;
            if(i < species.size()){
                currentSpecies = species.get(i);
                System.out.println("old DNA:");
                System.out.println(currentSpecies.getDNA());
                final var centroid = new GenPoint(currentSpecies.getDNA()).calculateNearestClusterCentroid(centroids.toArray(new ClusterCentroid[0]));
                centroids.remove(centroid);
                newDNA = centroid.toDNA();
                currentSpecies.setDNA(newDNA);
            } else {
                newDNA = centroids.removeFirst().toDNA();
                currentSpecies = new Species(newDNA, ecosystem);
            }

            System.out.println("new DNA:");
            System.out.println(currentSpecies.getDNA());
            System.out.println("–––––––––––––––");
        }



        // update colors
        for(var specimen : specimens){
            specimen.updateColor();
        }

    }

    public static void run(List<SpeciesParticle> particles){
        Run[] allRuns = new Run[15];

        int k = 2;
        int bestK = 2;
        float bestScore = -1f;
        float currentScore = -1f;
        float lastScore = -1f;
        while (currentScore >= lastScore){

            GenPoint[] genPoints = new GenPoint[particles.size()];
            for (int i = 0; i < genPoints.length; i++){
                genPoints[i] = new GenPoint(particles.get(i).getDNA());
            }

            lastScore = currentScore;
            Run run = run(genPoints, k);
            currentScore = Silhouette.meanSilhouette((GenPoint[]) run.dataPoints, (ClusterCentroid[]) run.centroids);
            System.out.println("k = " + k + " with silhouette of: " + currentScore);
            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestK = k;
            }
            allRuns[k] = run;
            k++;
        }
        System.out.println("Best k found: " + bestK);
    }

    public static Run run(GenPoint[] genPoints, int k){
        ClusterCentroid[] centroids = new ClusterCentroid[k];
        for (int i = 0; i<k; i++){
            centroids[i] = new ClusterCentroid(new DNA(), genPoints.length, i);
        }
        return run(genPoints,centroids);
    }

    public static Run run(GenPoint[] genPoints, ClusterCentroid[] centroids, int k){
        ClusterCentroid[] newCentroids = new ClusterCentroid[k];
        for (int i = 0; i<centroids.length; i++){
            newCentroids[i] = centroids[i];
        }
        for (int i = 0; i < k-centroids.length; i++){
            int pos = centroids.length + i;
            newCentroids[pos] = new ClusterCentroid(new DNA(), genPoints.length, pos);
        }
        return run(genPoints,newCentroids);
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
        //System.out.println("Changed? :" + changed);
        return changed;
    }

    public static void run(List<? extends Specimen> particles, List<Species> species){
        KMeans km = new KMeans(particles, species);
        km.run();
    }
}
