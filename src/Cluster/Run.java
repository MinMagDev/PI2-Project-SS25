package Cluster;


/**
 * saves the result of a completed run
 */

public class Run {
    public ClusterCentroid[] centroids;
    public GenPoint[] dataPoints;

    /**
     * saves the result of a completed run
     * @param dataPoints the data points
     * @param centroids the cluster centroids
     */
    public Run(GenPoint[] dataPoints, ClusterCentroid[] centroids){
        this.centroids = centroids;
        this.dataPoints = dataPoints;
    }
}
