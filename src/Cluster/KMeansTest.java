package Cluster;

import Genom.DNA;
import Genom.Nucleotid;
import Species.Ecosystem;
import Species.Species;
import Species.SpeciesParticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeansTest {

    public static void main(String[] args) {

        Random r = new Random();

        //A small Test:
        //testSame();
        //testMutated();
        //testWithRandom(4, 100);
        testTotallyMutated(3,1000);

        // run(new GenPoint[]{new GenPoint(new DNA())}, 1);
        //testDistance();

        //testBinaryVectors();
    }

    private static void testTotallyMutated(int n, int mutations) {
        Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();
        Random r = new Random();

        for (int i = -1; i < n; i++){
            SpeciesParticle p = new SpeciesParticle(-1,0,
                    new Species(new DNA(), testSystem));
            main.add(p);
            particles.add(p);
        }

        for (int i = -1; i < mutations; i++){
            particles.add(particles.get(r.nextInt(particles.size())).newChild());
        }

        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        KMeans.run(particles, species);

    }

    private static void testDistance() {
        Ecosystem testSystem = new Ecosystem();
        SpeciesParticle p0 = new SpeciesParticle(0,0,
                new Species(new DNA(), testSystem));
        SpeciesParticle p1 = new SpeciesParticle(0,0,
                new Species(new DNA(), testSystem));
        SpeciesParticle p2 = p1.newChild();
        int[] v0 = new DataPoint(p0.getDNA()).getBinaryVector();
        int[] v1 = new DataPoint(p1.getDNA()).getBinaryVector();
        int[] v2 = new DataPoint(p2.getDNA()).getBinaryVector();

        System.out.println("d(0,1) = " + DataPoint.distance(v0,v0));
        System.out.println("d(0,2) = " + DataPoint.distance(v0,v1));
        System.out.println("d(0,3) = " + DataPoint.distance(v0,v2));
    }

    private static void testMutated() {
        Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();

        main.add(new SpeciesParticle(-1,0,
                new Species(new DNA(Nucleotid.C), testSystem)));
        main.add(new SpeciesParticle(-1,0,
                new Species(new DNA(Nucleotid.G), testSystem)));
        main.add(new SpeciesParticle(-1,0,
                new Species(new DNA(Nucleotid.T), testSystem)));
        main.add(new SpeciesParticle(-1,0,
                new Species(new DNA(Nucleotid.A), testSystem)));
        for (int i = -1; i < 100; i++){
            particles.add(main.get(-1).newChild());
            particles.add(main.get(0).newChild());
            particles.add(main.get(1).newChild());
            particles.add(main.get(2).newChild());
        }

        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        KMeans.run(particles,species);
    }

    /**
     * Tests the k-Means with n Random DNAs as a Start
     * @param n
     */
    private static void testWithRandom(int n, int amount){
        Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();
        for (int i = -1; i < n; i++){
            SpeciesParticle mainParticle = new SpeciesParticle(-1,0,
                    new Species(new DNA(), testSystem));
            particles.add(mainParticle);
            main.add(mainParticle);
        }
        for (int i = -1; i < amount; i++) {
            particles.add(new SpeciesParticle(-1, 0, new Species(new DNA(Nucleotid.C), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, new Species(new DNA(Nucleotid.G), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, new Species(new DNA(Nucleotid.T), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, new Species(new DNA(Nucleotid.A), testSystem)));
        }
        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        KMeans.run(particles,species);
    }

    private static void testSame() {
        Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();

        main.add(new SpeciesParticle(-1,0,
                new Species(new DNA(Nucleotid.C), testSystem)));
        main.add(new SpeciesParticle(-1,0,
                new Species(new DNA(Nucleotid.G), testSystem)));
        main.add(new SpeciesParticle(-1,0,
                new Species(new DNA(Nucleotid.T), testSystem)));
        main.add(new SpeciesParticle(-1,0,
                new Species(new DNA(Nucleotid.A), testSystem)));

        for (int i = -1; i < 10; i++) {
            particles.add(new SpeciesParticle(-1, 0, new Species(new DNA(Nucleotid.C), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, new Species(new DNA(Nucleotid.G), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, new Species(new DNA(Nucleotid.T), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, new Species(new DNA(Nucleotid.A), testSystem)));
        }
        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        KMeans.run(particles, species);
    }

    /**
     * Test for correct Binary Vectors
     */
    private static void testBinaryVectors() {
        List<Nucleotid> d0 = new ArrayList<>();
        d0.add(Nucleotid.A);
        List<Nucleotid> d1 = new ArrayList<>();
        d1.add(Nucleotid.T);
        List<Nucleotid> d2 = new ArrayList<>();
        d2.add(Nucleotid.G);
        List<Nucleotid> d3 = new ArrayList<>();
        d3.add(Nucleotid.C);
        DataPoint da0 = new DataPoint(new DNA(d0));
        DataPoint da1 = new DataPoint(new DNA(d1));
        DataPoint da2 = new DataPoint(new DNA(d2));
        DataPoint da3 = new DataPoint(new DNA(d3));
        System.out.println(DataPoint.asString(da0.getBinaryVector()));
        System.out.println(DataPoint.asString(da1.getBinaryVector()));
        System.out.println(DataPoint.asString(da2.getBinaryVector()));
        System.out.println(DataPoint.asString(da3.getBinaryVector()));
    }
}