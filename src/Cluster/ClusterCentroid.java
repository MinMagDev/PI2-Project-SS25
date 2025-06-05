
public class ClusterCentroid extends DataPoint{

    private GenPoint[] clusteredPoints;

    public ClusterCentroid(float pA, float mA, int totalPoints, String name) {
        super(pA, mA, name);
        clusteredPoints = new GenPoint[totalPoints];
    }

    public void addPointToCluster(GenPoint genPoint) {
        for (int i = 0; i < clusteredPoints.length; i++){
            if(clusteredPoints[i] == null) {
                clusteredPoints[i] = genPoint;
                break;
            }
        }
    }

    public void removePointFromCluster(GenPoint genPoint) {
        for (int i = 0; i <= clusteredPoints.length; i++){
            if(clusteredPoints[i] == genPoint) {
                clusteredPoints[i] = null;
                break;
            }
        }
    }

    public GenPoint[] getClusteredPoints () {
        int amount = 0;
        for (GenPoint potentialPoint: clusteredPoints) {
            if (potentialPoint != null) amount++;
        }

        GenPoint[] onlyPoints = new GenPoint[amount];
        int onlyIndex = 0;
        for (GenPoint potentialPoint: clusteredPoints) {
            if (potentialPoint != null) {
                onlyPoints[onlyIndex] = potentialPoint;
                onlyIndex++;
            }
        }
        return onlyPoints;
    }

    public boolean updatePosition() {
        float newPlus = 0.0f;
        float newMinus= 0.0f;
        GenPoint[] onlyClusteredPoints = getClusteredPoints();
        for (GenPoint genPoint: onlyClusteredPoints) {
            newPlus += genPoint.getPlusAnti();
            newMinus += genPoint.getMinusAnti();
        }
        int size = onlyClusteredPoints.length;
        newPlus /= size;
        newMinus /= size;

        boolean changed = !(newPlus == this.getPlusAnti() && newMinus == this.getMinusAnti());

        this.setPlusAnti(newPlus);
        this.setMinusAnti(newMinus);
        return changed;
    }

    public void printClusterWithDistance() {
        System.out.println("----------------------------------------------------");
        System.out.println("All points with distance in " + this.getPointName() +
                "(" + this.getPlusAnti() + ", " + this.getMinusAnti() + "):");
        for (GenPoint point: getClusteredPoints()) {
            System.out.println(point.getPointName() +
                    ":\t" +
                    point.getPlusAnti() + ", " + point.getMinusAnti() +
                    "\t" +
                    "d: "+ point.getDistanceToNearestCluster());
        }
    }


}
