public class GenPoint extends DataPoint{

    private float distanceToNearestCluster;

    public GenPoint(float pA, float mA, String name) {
        super(pA, mA, name);
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
        float plusDistance = (float) Math.pow((genPoint.getPlusAnti() - centroid.getPlusAnti()),2);
        float minsDistance = (float) Math.pow((genPoint.getMinusAnti() - centroid.getMinusAnti()),2);
        return plusDistance + minsDistance;
    }

    public float getDistanceToNearestCluster() {
        return distanceToNearestCluster;
    }
}
