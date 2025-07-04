package Cluster;

import Genom.DNA;
import Genom.Nucleotid;

import java.util.Vector;

public class DataPoint {
    private static final int MAX_COMPARABLE_DNA_LENGTH = 160;
    public static final int MAX_COMPARABLE_BINARY_VECOTR_LENGTH = MAX_COMPARABLE_DNA_LENGTH * 4;
    private int[] binaryVector;
    private ClusterCentroid nearestClusterCentroid;

    public DataPoint(DNA dna){
        this.binaryVector = new int[MAX_COMPARABLE_BINARY_VECOTR_LENGTH];
        calculateBinaryVector(dna);
    }

    public static float distance(int[] p1, int[] p2) {
        int[] toOther = addBinaryVectors(p1, divideBinaryVector(p2, -1));
        return length(toOther);
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
        for (Nucleotid nucleotid: dna.getDNA()){
            int ordinal = nucleotid.ordinal();
            binaryVector[4 * count + ordinal] = 1;
            count++;
        }
        System.out.print("Calculated Vector");
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

    public static int[] findMean(int[] v){
        for (int i = 0; i < MAX_COMPARABLE_DNA_LENGTH; i++){
            int maxJ = 0;
            int maxJVal = 0;
            for (int j = 0; j < 4; j++){
                int val = v[i*4+j];
                if(val > maxJVal){
                    maxJ = j;
                    maxJVal = val;
                }
            }
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
}
