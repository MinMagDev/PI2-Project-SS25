package Genom;

public class DNATest {
    public static void main(String[] args) {
        DNA dna1 = new DNA();
        dna1.print();
        System.out.println(dna1.getColor());
        System.out.println(dna1.getRadius());
        System.out.println(DNA.getMaxValue(6));
    }
}
