package Cluster;

import Genom.DNA;
import Genom.Nucleotide;

import java.util.ArrayList;
import java.util.List;

public class DataPoint {
    private static final int MAX_COMPARABLE_DNA_LENGTH = 160;
    public static final int MAX_COMPARABLE_BINARY_VECTOR_LENGTH = MAX_COMPARABLE_DNA_LENGTH * 4;
    private int[] binaryVector;
    private ClusterCentroid nearestClusterCentroid;

    final private int length;

    public DataPoint(DNA dna){
        this.binaryVector = new int[MAX_COMPARABLE_BINARY_VECTOR_LENGTH];
        calculateBinaryVector(dna);
        this.length = dna.getDNA().size();
    }

    public DataPoint(String dna){
        this(DNA.fromString(dna));
    }

    /**
     * Calculates the Distance between two points, by comparing blocks of 4 (for every Nucleotid 1) and counting changes
     * @param p1 first point
     * @param p2 second point
     * @return the distance between the two points
     */
    public static float distance(int[] p1, int[] p2) {
        float distance = 0;
        for (int i = 0; i < p1.length; i += 4) {
            int index1 = -1;
            int index2 = -1;
            for (int j = 0; j < 4; j++) {
                if (p1[i + j] == 1) index1 = j;
                if (p2[i + j] == 1) index2 = j;
            }
            if (index1 != index2) distance++;
        }
        return distance;
    }

    public static float length(int[] v){
        int result = 0;
        for (int coord: v){
            result += Math.abs(coord);
        }
        return (float) Math.sqrt(result);
    }

    private void calculateBinaryVector(DNA dna) {
        int count = 0;
        for (Nucleotide nucleotide : dna.getDNA()){
            int ordinal = nucleotide.ordinal();
            binaryVector[4 * count + ordinal] = 1;
            count++;
        }
    }

    public static int[] addBinaryVectors(int[] v1, int[] v2){
        int[] result = new int[v1.length];
        for (int i = 0; i < result.length; i++){
            result[i] = v1[i] + v2[i];
        }
        return result;
    }

    public static int[] divideBinaryVector(int[] v, int size){
        int[] vRes = v.clone();
        if (size == 0) return v;
        for (int i = 0; i < v.length; i++){
            vRes[i] = vRes[i]/size;
        }
        return vRes;
    }

    /**
     * Finds the mean binary Vector of a non-binary counter Vector
     * @param v Vector v, contains counts of the Nucleotids
     * @return Hot One Off Vector
     */
    public static int[] findMean(int[] v){
        for (int i = 0; i < MAX_COMPARABLE_DNA_LENGTH; i++){
            int maxJ = 0;
            int maxJVal = 0;

            //finds the maximum Amount of Nucleotids off a Block of 4
            for (int j = 0; j < 4; j++){
                int val = v[i*4+j];
                if(val > maxJVal){
                    maxJ = j;
                    maxJVal = val;
                }
            }

            //Replaces the maximum value with 1, the other with 0
            for (int j = 0; j < 4; j++){
                if(j != maxJ) {
                    v[i*4+j] = 0;
                    continue;
                }
                v[i*4+j]  = 1;
            }
        }
        return v;
    }


    public ClusterCentroid getNearestClusterCentroid() {
        return nearestClusterCentroid;
    }

    public void setNearestClusterCentroid(ClusterCentroid nearestClusterCentroid) {
        this.nearestClusterCentroid = nearestClusterCentroid;
    }

    public int[] getBinaryVector(){
        return binaryVector;
    }

    public int getCoordinate(int i) {
        return binaryVector[i];
    }

    protected void setBinaryVector(int[] newPosition) {
        this.binaryVector = newPosition;
    }

    public static String asString(int[] v){
        String res = "[";
        for (int x: v){
            res += x;
            res += ", ";
        }
        return res += "]";
    }

    public static Boolean compare(int[] v1, int[] v2){
        if(v1.length != v2.length) return false;
        for (int i = 0; i<v2.length; i++){
            if (v1[i] != v2[i]) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return asString(this.binaryVector);
    }

    /**
     * converts this data point back into DNA
     */
    public DNA toDNA() {
        List<Nucleotide> newDNA = new ArrayList<>();
        for(int i = 0; i < length; i++){
            for(int j = 0; j < 4; j++){
               if(binaryVector[i*4+j] == 1){
                   newDNA.add(Nucleotide.values()[j]);
                   break;
               }
            }

        }
        return new DNA(newDNA);
    }
}
