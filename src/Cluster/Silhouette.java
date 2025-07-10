package Cluster;

public class Silhouette {
    /**
     * Calculates the mean silhouette score of all points to all other points and clusters
     * @param points array of points, for calculating the silhouette
     * @param centroids array of centroids of all points
     * @return the mean silhouette score
     */
    public static float meanSilhouette(GenPoint[] points, ClusterCentroid[] centroids) {
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
        float b = calcSmallestMeanD(point,centroids);
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
}