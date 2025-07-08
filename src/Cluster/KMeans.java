package Cluster;

import Genom.DNA;
import Genom.Nucleotid;
import Species.Ecosystem;
import Species.Species;
import Species.SpeciesParticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {

    final static int MAX_K = 6;

    public static void main(String[] args) {

        Random r = new Random();

        //A small Test:
        //testSame();
        //testMutated();
        //testWithRandom(4, 100);
        testTotallyMutated(4,400);

        // run(new GenPoint[]{new GenPoint(new DNA())}, 1);
        //testDistance();

        //testBinaryVectors();
    }

    private static void testTotallyMutated(int n, int mutations) {
        Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < n; i++){
            SpeciesParticle p = new SpeciesParticle(0,0,
                    new Species(new DNA(), testSystem));
            main.add(p);
            particles.add(p);
        }

        for (int i = 0; i < mutations; i++){
            particles.add(particles.get(r.nextInt(particles.size())).newChild());
        }

        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        run(particles,species);

    }

    private static void testDistance() {
        Ecosystem testSystem = new Ecosystem();
        SpeciesParticle p1 = new SpeciesParticle(0,0,
                new Species(new DNA(), testSystem));
        SpeciesParticle p2 = new SpeciesParticle(0,0,
                new Species(new DNA(), testSystem));
        SpeciesParticle p3 = p1.newChild();
        int[] v1 = new DataPoint(p1.getDNA()).getBinaryVector();
        int[] v2 = new DataPoint(p2.getDNA()).getBinaryVector();
        int[] v3 = new DataPoint(p3.getDNA()).getBinaryVector();

        System.out.println("d(1,1) = " + DataPoint.distance(v1,v1));
        System.out.println("d(1,2) = " + DataPoint.distance(v1,v2));
        System.out.println("d(1,3) = " + DataPoint.distance(v1,v3));
    }

    private static void testMutated() {
        Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();

        main.add(new SpeciesParticle(0,0,
                new Species(new DNA(Nucleotid.C), testSystem)));
        main.add(new SpeciesParticle(0,0,
                new Species(new DNA(Nucleotid.G), testSystem)));
        main.add(new SpeciesParticle(0,0,
                new Species(new DNA(Nucleotid.T), testSystem)));
        main.add(new SpeciesParticle(0,0,
                new Species(new DNA(Nucleotid.A), testSystem)));
        for (int i = 0; i < 100; i++){
            particles.add(main.get(0).newChild());
            particles.add(main.get(1).newChild());
            particles.add(main.get(2).newChild());
            particles.add(main.get(3).newChild());
        }

        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        run(particles,species);
    }

    /**
     * Tests the k-Means with n Random DNAs as a Start
     * @param n
     */
    private static void testWithRandom(int n, int amount){
        Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();
        for (int i = 0; i < n; i++){
            SpeciesParticle mainParticle = new SpeciesParticle(0,0,
                   new Species(new DNA(), testSystem));
            particles.add(mainParticle);
            main.add(mainParticle);
        }
        for (int i = 0; i < amount; i++) {
            particles.add(new SpeciesParticle(0, 0, new Species(new DNA(Nucleotid.C), testSystem)));
            particles.add(new SpeciesParticle(0, 0, new Species(new DNA(Nucleotid.G), testSystem)));
            particles.add(new SpeciesParticle(0, 0, new Species(new DNA(Nucleotid.T), testSystem)));
            particles.add(new SpeciesParticle(0, 0, new Species(new DNA(Nucleotid.A), testSystem)));
        }
        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        run(particles,species);
    }

    private static void testSame() {
        Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();

        main.add(new SpeciesParticle(0,0,
                    new Species(new DNA(Nucleotid.C), testSystem)));
        main.add(new SpeciesParticle(0,0,
                new Species(new DNA(Nucleotid.G), testSystem)));
        main.add(new SpeciesParticle(0,0,
                new Species(new DNA(Nucleotid.T), testSystem)));
        main.add(new SpeciesParticle(0,0,
                new Species(new DNA(Nucleotid.A), testSystem)));

        for (int i = 0; i < 10; i++) {
            particles.add(new SpeciesParticle(0, 0, new Species(new DNA(Nucleotid.C), testSystem)));
            particles.add(new SpeciesParticle(0, 0, new Species(new DNA(Nucleotid.G), testSystem)));
            particles.add(new SpeciesParticle(0, 0, new Species(new DNA(Nucleotid.T), testSystem)));
            particles.add(new SpeciesParticle(0, 0, new Species(new DNA(Nucleotid.A), testSystem)));
        }
        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        run(particles, species);
    }

    /**
     * Test for correct Binary Vectors
     */
    private static void testBinaryVectors() {
        List<Nucleotid> d1 = new ArrayList<>();
        d1.add(Nucleotid.A);
        List<Nucleotid> d2 = new ArrayList<>();
        d2.add(Nucleotid.T);
        List<Nucleotid> d3 = new ArrayList<>();
        d3.add(Nucleotid.G);
        List<Nucleotid> d4 = new ArrayList<>();
        d4.add(Nucleotid.C);
        DataPoint da1 = new DataPoint(new DNA(d1));
        DataPoint da2 = new DataPoint(new DNA(d2));
        DataPoint da3 = new DataPoint(new DNA(d3));
        DataPoint da4 = new DataPoint(new DNA(d4));
        System.out.println(DataPoint.asString(da1.getBinaryVector()));
        System.out.println(DataPoint.asString(da2.getBinaryVector()));
        System.out.println(DataPoint.asString(da3.getBinaryVector()));
        System.out.println(DataPoint.asString(da4.getBinaryVector()));
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
                centroids[i] = new ClusterCentroid(species.get(i).getDNA(), particles.size());
            }

            //Clustering
            if (k < centroids.length){
                ClusterCentroid[] fewCentroids = new ClusterCentroid[k];
                for (int i = 0; i < k; i++){
                    fewCentroids[i] = centroids[i];
                }
                DataPoint[][] pointss = run(genPoints, fewCentroids);
                currentScore = meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
                allClusters[k] =  pointss;
            }else if( k == centroids.length){
                DataPoint[][] pointss = run(genPoints, centroids);
                currentScore = meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
                allClusters[k] =  pointss;
            } else {
                DataPoint[][] pointss = run(genPoints, centroids, k);
                currentScore = meanSilhouette((GenPoint[]) pointss[0], (ClusterCentroid[]) pointss[1]);
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

    /**
     * Calculates the mean silhouette score of all points to all other points and clusters
     * @param points array of points, for calculating the silhouette
     * @param centroids array of centroids of all points
     * @return the mean silhouette score
     */
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

        if (point.getNearestClusterCentroid().getClusteredPoints().length == 1) return 0;
        float a = calcSameMeanD(point);
        if (Float.isNaN(a)) System.out.println("a is NaN, for point: " + point.toString());
        float b = calcSmallestMeanD(point,centroids);
        if (Float.isNaN(b)) System.out.println("b is NaN, for point: " + point.toString());
        if (a == b) return 0f;

        //AHHHHHHHHHHHHHHHHHH, b-a NICHT a-b, wie ich es lange hatte
        float result = (b-a)/Math.max(a,b);
        //System.out.println("result is"+ result + " for a = " + a + " and b = "+ b);
        return result;
    }

    /**
     * Calculates the Mean Distance between a point and all other points in the same Cluster
     * @param point Point i
     * @return Mean Distance
     */
    private static float calcSameMeanD(GenPoint point) {
        ClusterCentroid centroid = point.getNearestClusterCentroid();
        if (centroid.getClusteredPoints().length <= 1) return 0f;
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
     * @return smallest Mean Distance
     */
    private static float calcSmallestMeanD(GenPoint point, ClusterCentroid[] centroids) {
        float smallestMean = Float.MAX_VALUE;
        boolean found = false;
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
                found = true;
            }
        }
        if (!found) return 0f;
        return smallestMean;
    }

    public static DataPoint[][] run(GenPoint[] genPoints, int k){
        ClusterCentroid[] centroids = new ClusterCentroid[k];
        for (int i = 0; i<k; i++){
            centroids[i] = new ClusterCentroid(new DNA(), genPoints.length);
        }
        return run(genPoints,centroids);
    }

    public static DataPoint[][] run(GenPoint[] genPoints, ClusterCentroid[] centroids, int k){
        ClusterCentroid[] newCentroids = new ClusterCentroid[k + centroids.length];
        for (int i = 0; i<k; i++){
            newCentroids[i] = new ClusterCentroid(new DNA(), genPoints.length);
        }
        for (int i = 0; i < centroids.length; i++){
            newCentroids[k+i] = centroids[i];
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
}
