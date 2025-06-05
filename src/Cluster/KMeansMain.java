package Cluster;

public class KMeansMain {
    public static void main(String[] args) {

        GenPoint[] allPoints = new GenPoint[] {
                new GenPoint(11f,12f, "g1"),
                new GenPoint(8.2f,4f, "g2"),
                new GenPoint(4.2f,7, "g3"),
                new GenPoint(15f,7.5f, "g4"),
                new GenPoint(7.6f,3f, "g5"),
                new GenPoint(12f,11f, "g6"),
                new GenPoint(13.9f,7.9f, "g7")
        };

        ClusterCentroid c1 = new ClusterCentroid(11f,12f, allPoints.length, "c1");
        ClusterCentroid c2 = new ClusterCentroid(8.2f,4f, allPoints.length, "c2");
        ClusterCentroid c3 = new ClusterCentroid(4.2f,7f, allPoints.length, "c3");
        ClusterCentroid c4 = new ClusterCentroid(15f,7.5f, allPoints.length, "c4");

        ClusterCentroid[] centroids = new ClusterCentroid[] {c1,c2};
        ClusterCentroid[] centroidsB = new ClusterCentroid[] {c1,c2,c3,c4};

        int i = 0;
        boolean next = true;
        boolean end  = false;
        boolean last = false;
//        while (next) {
//
//            System.out.println("Iteration: " + i);
//            for (ClusterCentroid centroid : centroids) {
//                centroid.printClusterWithDistance();
//            }
//            System.out.println();
//
//            if (last) next = false;
//            if (end) last = true;
//            end = !iterate(allPoints,centroids);
//            if (i == 2) {
//                System.out.println("g2 - c1: " + GenPoint.calculateQuadEuklidDistance(allPoints[1], c1));
//                System.out.println("g4 - c2: " + GenPoint.calculateQuadEuklidDistance(allPoints[3], c2));
//
//            }
//            i++;
//        }

        while (next) {
            System.out.println("Iteration: " + i);
            for (ClusterCentroid centroid : centroidsB) {
                centroid.printClusterWithDistance();
            }
            System.out.println();

            if (last) next = false;
            if (end) last = true;
            end = !iterate(allPoints,centroidsB);
            i++;
        }

        double test = Math.pow(2,0.5);

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

        boolean changed = true;

        for (ClusterCentroid centroid: centroids) {
            if (!centroid.updatePosition()) changed = false;
        }

        return changed;
    }
}
