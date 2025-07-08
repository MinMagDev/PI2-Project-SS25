package Cluster;

import Genom.DNA;

public class GenPoint extends DataPoint{

    private float distanceToNearestCluster;

    public GenPoint(DNA dna) {
        super(dna);
    }

    /**
     * Calculates the Distance to every cluster centroid and returns the one, which is nearest.
     * @param centroids array of all the centroids
     * @return the centroid out of the list, which is nearest.
     */
    public ClusterCentroid calculateNearestClusterCentroid(ClusterCentroid[] centroids){
        ClusterCentroid nearest = getNearestClusterCentroid();
        if (nearest == null) nearest = centroids[0];
        float distToNearest = DataPoint.distance(this.getBinaryVector(), nearest.getBinaryVector());

        for (ClusterCentroid centroid : centroids) {
           float quadEuklid = DataPoint.distance(this.getBinaryVector(), centroid.getBinaryVector());;
           if (quadEuklid <= distToNearest) {
               nearest = centroid;
               distToNearest = quadEuklid;
           }
        }

        setNearestClusterCentroid(nearest);
        this.distanceToNearestCluster = distToNearest;
        return nearest;
    }

    public float getDistanceToNearestCluster() {
        return distanceToNearestCluster;
    }
}
