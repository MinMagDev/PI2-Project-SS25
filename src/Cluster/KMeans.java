package Cluster;

import Genom.DNA;
import Species.Species;
import Species.SpeciesParticle;
import Species.Ecosystem;

import java.util.List;

public class KMeans {

    private List<SpeciesParticle> particles;
    private List<Species> species;
    private List<Integer> particleSpecies;

    public KMeans(List<SpeciesParticle> particles, List<Species> species){
        this.particles = particles;
        this.species = species;
    }

    public List<Species> getSpecies() {
        return species;
    }

    public List<SpeciesParticle> getParticles() {
        return particles;
    }




    final static int MAX_K = 9;

    public void run(){

        DataPoint[][][] allClusters = new DataPoint[15][][];

        int bestK = 2;
        float bestScore = -1f;
        float currentScore;
        for (int k = 2; k <= MAX_K; k++){
            System.out.println("K is " + k);
            //Setup
            GenPoint[] genPoints = new GenPoint[particles.size()];
            ClusterCentroid[] centroids = new ClusterCentroid[species.size()];
            for (int i = 0; i < genPoints.length; i++){
                genPoints[i] = new GenPoint(particles.get(i).getDNA());
            }
            for (int i = 0; i < centroids.length; i++) {
                centroids[i] = new ClusterCentroid(species.get(i).getDNA(), particles.size(), i);
            }

            //Clustering
            if (k < centroids.length){
                ClusterCentroid[] fewCentroids = new ClusterCentroid[k];
                for (int i = 0; i < k; i++){
                    fewCentroids[i] = centroids[i];
                }
                DataPoint[][] pointss = run(genPoints, fewCentroids);
                currentScore = Silhouette.meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
                allClusters[k] =  pointss;
            }else if( k == centroids.length){
                DataPoint[][] pointss = run(genPoints, centroids);
                currentScore = Silhouette.meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
                allClusters[k] =  pointss;
            } else {
                DataPoint[][] pointss = run(genPoints, centroids, k);
                currentScore = Silhouette.meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
                allClusters[k] =  pointss;
            }
            System.out.println("k = " + k + " with silhouette of: " + currentScore);
            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestK = k;
            }
        }
        updateParticles(allClusters[bestK]);

        System.out.println("Best k found: " + bestK);
    }

    public void updateParticles(DataPoint[][] cluster) {

        for (DataPoint c: cluster[1]){
            ClusterCentroid centroid = (ClusterCentroid) c;
            Ecosystem ecosystem = species.get(0).getEcosystem();
            species.clear();
            species.add(new Species(centroid.toDNA(), ecosystem));
        }

        int particleCounter = 0;
        for (DataPoint point: cluster[0]) {
            int pointClusterID = point.getNearestClusterCentroid().getClusterID();
            particles
                    .get(particleCounter)
                    .setSpecies(species.get(pointClusterID));
        }
    }

    public static void run(List<SpeciesParticle> particles){
        DataPoint[][][] allClusters = new DataPoint[15][][];

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
            DataPoint[][] pointss = run(genPoints, k);
            currentScore = Silhouette.meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
            System.out.println("k = " + k + " with silhouette of: " + currentScore);
            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestK = k;
            }
            allClusters[k] =  pointss;
            k++;
        }
        System.out.println("Best k found: " + bestK);
    }

    public static DataPoint[][] run(GenPoint[] genPoints, int k){
        ClusterCentroid[] centroids = new ClusterCentroid[k];
        for (int i = 0; i<k; i++){
            centroids[i] = new ClusterCentroid(new DNA(), genPoints.length, i);
        }
        return run(genPoints,centroids);
    }

    public static DataPoint[][] run(GenPoint[] genPoints, ClusterCentroid[] centroids, int k){
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

    public static DataPoint[][] run(GenPoint[] genPoints, ClusterCentroid[] centroids){
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
        return new DataPoint[][]{genPoints,centroids};
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

    public static void run(List<SpeciesParticle> particles, List<Species> species){

        DataPoint[][][] allClusters = new DataPoint[15][][];

        int bestK = 2;
        float bestScore = -1f;
        float currentScore;
        for (int k = 2; k <= MAX_K; k++){
            System.out.println("K is " + k);
            //Setup
            GenPoint[] genPoints = new GenPoint[particles.size()];
            ClusterCentroid[] centroids = new ClusterCentroid[species.size()];
            for (int i = 0; i < genPoints.length; i++){
                genPoints[i] = new GenPoint(particles.get(i).getDNA());
            }
            for (int i = 0; i < centroids.length; i++) {
                centroids[i] = new ClusterCentroid(species.get(i).getDNA(), particles.size(), i);
            }

            //Clustering
            if (k < centroids.length){
                ClusterCentroid[] fewCentroids = new ClusterCentroid[k];
                for (int i = 0; i < k; i++){
                    fewCentroids[i] = centroids[i];
                }
                DataPoint[][] pointss = run(genPoints, fewCentroids);
                currentScore = Silhouette.meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
                allClusters[k] =  pointss;
            }else if( k == centroids.length){
                DataPoint[][] pointss = run(genPoints, centroids);
                currentScore = Silhouette.meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
                allClusters[k] =  pointss;
            } else {
                DataPoint[][] pointss = run(genPoints, centroids, k);
                currentScore = Silhouette.meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
                allClusters[k] =  pointss;
            }
            System.out.println("k = " + k + " with silhouette of: " + currentScore);
            if(currentScore > bestScore) {
                bestScore = currentScore;
                bestK = k;
            }
        }
        System.out.println("Best k found: " + bestK);
    }
}
