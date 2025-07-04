package Cluster;

import Genom.DNA;

public class GenPoint extends DataPoint{

    private float distanceToNearestCluster;

    public GenPoint(DNA dna) {
        super(dna);
    }

    public ClusterCentroid calculateNearestClusterCentroid(ClusterCentroid[] centroids){
        ClusterCentroid nearest = getNearestClusterCentroid();
        if (nearest == null) nearest = centroids[0];
        float distToNearest = calculateQuadEuklidDistance(this, nearest);

        for (ClusterCentroid centroid : centroids) {
           float quadEuklid = calculateQuadEuklidDistance(this, centroid);
           if (quadEuklid <= distToNearest) {
               nearest = centroid;
               distToNearest = quadEuklid;
           }
        }

        setNearestClusterCentroid(nearest);
        this.distanceToNearestCluster = distToNearest;
        return nearest;
    }

    public static float calculateQuadEuklidDistance(GenPoint genPoint, ClusterCentroid centroid) {
        int count = 0;
        for(int i = 0; i < centroid.MAX_COMPARABLE_BINARY_VECOTR_LENGTH; i++){
            //No need for squaring, due to 1^2 = 1 and 0^2 = 0, Yay much more efficent this way
            count += Math.abs(genPoint.getCoordinate(i) - centroid.getCoordinate(i));
        }
        return count;
    }

    public float getDistanceToNearestCluster() {
        return distanceToNearestCluster;
    }
}
