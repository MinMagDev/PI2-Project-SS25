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
