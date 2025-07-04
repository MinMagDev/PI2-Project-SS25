package Cluster;

import Genom.DNA;
import Species.Ecosystem;
import Species.Species;
import Species.SpeciesParticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {
    public static void main(String[] args) {
        Ecosystem testSystem = new Ecosystem();
        Random r = new Random();

        //A small Test:
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            SpeciesParticle mainParticle = new SpeciesParticle(0,0,
                    new Species(new DNA(), testSystem));
            particles.add(mainParticle);
            main.add(mainParticle);
        }
        for (int i = 0; i < 100; i++){
            SpeciesParticle mainParticle = main.get(r.nextInt(main.size()));
            particles.add(mainParticle.newChild());
        }
        run(particles);
       // run(new GenPoint[]{new GenPoint(new DNA())}, 1);
//        SpeciesParticle p1 = new SpeciesParticle(0,0,
//                new Species(new DNA(), testSystem));
//        SpeciesParticle p2 = new SpeciesParticle(0,0,
//                new Species(new DNA(), testSystem));
//        SpeciesParticle p3 = p1.newChild();
//        int[] v1 = new DataPoint(p1.getDNA()).getBinaryVector();
//        int[] v2 = new DataPoint(p2.getDNA()).getBinaryVector();
//        int[] v3 = new DataPoint(p3.getDNA()).getBinaryVector();
//
//        System.out.println("d(1,1) = " + DataPoint.distance(v1,v1));
//        System.out.println("d(1,2) = " + DataPoint.distance(v1,v2));
//        System.out.println("d(1,3) = " + DataPoint.distance(v1,v3));
    }

    public static void run(List<SpeciesParticle> particles){
        GenPoint[] genPoints = new GenPoint[particles.size()];
        DataPoint[][][] allClusters = new DataPoint[15][][];
        for (int i = 0; i < genPoints.length; i++){
            genPoints[i] = new GenPoint(particles.get(i).getDNA());
        }
        int k = 2;
        int bestK = 2;
        float bestScore = -1f;
        float currentScore = -1f;
        float lastScore = -1f;
        while (currentScore >= lastScore){
            lastScore = currentScore;
            DataPoint[][] pointss = run(genPoints, k);
            currentScore = meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
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

    private static float meanSilhouette(GenPoint[] points, ClusterCentroid[] centroids) {
        float result = 0.0f;
        for (GenPoint point: points){
            result += silhouette(point, centroids);
        }
        return  result/points.length;
    }

    /**
     * Calculates the Silhouette score of a particle, which lies between (-1 and 1)
     * https://en.wikipedia.org/wiki/Silhouette_(clustering)
     * @param point The point i
     * @param centroids Array of all centroids
     * @return Silhouette score (in [-1,1])
     */
    private static float silhouette(GenPoint point, ClusterCentroid[] centroids) {
        float a = calcSameMeanD(point);
        float b = calcSmallestMeanD(point,centroids);
        float result = (a-b)/Math.max(a,b);
        return result;
    }

    /**
     * Calculates the Mean Distance between a point and all other points in the same Cluster
     * @param point Point i
     * @return Mean Distance
     */
    private static float calcSameMeanD(GenPoint point) {
        ClusterCentroid centroid = point.getNearestClusterCentroid();
        float addedDistance = 0.0f;
        for (GenPoint other: centroid.getClusteredPoints()){
            if (point != other){
                float thisDistance = DataPoint.distance(other.getBinaryVector(), point.getBinaryVector());

                addedDistance +=  thisDistance;
            }
        }
        return addedDistance/(centroid.getClusteredPoints().length-1);
    }

    /**
     * Calculates the smallest mean distance, between a Point i and all other points in other clusters
     * @param point Point i
     * @param centroids Array of all Clustercentroids
     * @return smallest Meand Distance
     */
    private static float calcSmallestMeanD(GenPoint point, ClusterCentroid[] centroids) {
        float smallestMean = Float.MAX_VALUE;
        for (ClusterCentroid centroid: centroids){
            if (centroid == point.getNearestClusterCentroid()) continue;
            float addedDistance = 0;
            GenPoint[] others = centroid.getClusteredPoints();
            for (GenPoint other : others) {
                addedDistance += DataPoint.distance(other.getBinaryVector(), point.getBinaryVector());
            }
            float mean = addedDistance / others.length;
            if (smallestMean > mean) {
                smallestMean = mean;
            }
        }
        return smallestMean;
    }

    public static DataPoint[][] run(GenPoint[] genPoints, int k){
        ClusterCentroid[] centroids = new ClusterCentroid[k];
        for (int i = 0; i<k; i++){
            centroids[i] = new ClusterCentroid(new DNA(), genPoints.length);
        }
        return run(genPoints,centroids);
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

        boolean changed = true;

        for (ClusterCentroid centroid: centroids) {
            if (!centroid.updatePosition()) changed = false;
        }
        System.out.println("Changed? :" + changed);
        return changed;
    }
}
