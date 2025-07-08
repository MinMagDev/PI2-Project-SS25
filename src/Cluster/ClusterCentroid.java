package Cluster;

import Genom.DNA;

public class ClusterCentroid extends DataPoint{

    private GenPoint[] clusteredPoints;
    private int oldClusterdPointsAmount;
    private int clusterdPointAmount;

    public ClusterCentroid(DNA dna){
        super(dna);
    }

    public ClusterCentroid(DNA dna, int totalPoints) {
        super(dna);
        clusteredPoints = new GenPoint[totalPoints];
    }

    public void addPointToCluster(GenPoint genPoint) {
        for (int i = 0; i < clusteredPoints.length; i++){
            if(clusteredPoints[i] == null) {
                clusteredPoints[i] = genPoint;
                clusterdPointAmount++;
                break;
            }
        }
    }

    public void removePointFromCluster(GenPoint genPoint) {
        for (int i = 0; i < clusteredPoints.length; i++){
            if(clusteredPoints[i] == genPoint) {
                clusteredPoints[i] = null;
                clusterdPointAmount--;
                break;
            }
        }
    }

    public GenPoint[] getClusteredPoints() {
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
        int[] newPosition = new int[MAX_COMPARABLE_BINARY_VECTOR_LENGTH];
        GenPoint[] onlyClusteredPoints = getClusteredPoints();
        for (GenPoint genPoint: onlyClusteredPoints) {
            newPosition = addBinaryVectors(genPoint.getBinaryVector(), newPosition);
        }

        newPosition = findMean(newPosition);

        boolean changed = !compare(newPosition, this.getBinaryVector()) || !(oldClusterdPointsAmount == clusterdPointAmount);

        oldClusterdPointsAmount = clusterdPointAmount;
        this.setBinaryVector(newPosition);
        //System.out.println("New Position: " + DataPoint.asString(newPosition));
        return changed;
    }
}
