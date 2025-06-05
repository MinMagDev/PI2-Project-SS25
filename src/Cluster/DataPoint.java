public class DataPoint {
    private float plusAnti;
    private float minusAnti;
    private ClusterCentroid nearestClusterCentroid;
    private String pointName;

    public DataPoint(float pA, float mA, String name){
        this.plusAnti = pA;
        this.minusAnti = mA;
        this.pointName = name;
    }


    public float getMinusAnti() {
        return minusAnti;
    }

    public void setMinusAnti(float minusAnti) {
        this.minusAnti = minusAnti;
    }

    public float getPlusAnti() {
        return plusAnti;
    }

    public void setPlusAnti(float plusAnti) {
        this.plusAnti = plusAnti;
    }

    public ClusterCentroid getNearestClusterCentroid() {
        return nearestClusterCentroid;
    }

    public void setNearestClusterCentroid(ClusterCentroid nearestClusterCentroid) {
        this.nearestClusterCentroid = nearestClusterCentroid;
    }

    public String getPointName() {
        return pointName;
    }
}
