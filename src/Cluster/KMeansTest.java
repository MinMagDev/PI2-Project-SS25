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
        List<DebugSpecimen> specimens = new ArrayList<>();
        List<Species> species = new ArrayList<>();

        DebugSpecimen.makeSpecimens(n, 10, true, mutations, species, specimens);

        specimens.forEach(specimen -> {
            System.out.println(specimen.getDNA());
        });

        KMeans.run(specimens, species);

        KMeans kMeans = new KMeans(specimens, species);

        kMeans.run();

        species.forEach(aSpecies -> {
            System.out.println(aSpecies.getDNA());
        });
    }

    private static void testDistance() {
        int[] v0 = new DataPoint("AAT").getBinaryVector();
        int[] v1 = new DataPoint("CC").getBinaryVector();
        int[] v2 = new DataPoint("TGC").getBinaryVector();

        System.out.println("d(0,1) = " + DataPoint.distance(v0,v0));
        System.out.println("d(0,1) = " + DataPoint.distance(v0,v1));
        System.out.println("d(0,2) = " + DataPoint.distance(v0,v1));
        System.out.println("d(0,3) = " + DataPoint.distance(v0,v2));
    }

    private static void testMutated() {
        Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();

        main.add(new SpeciesParticle(-1,0,0,
                new Species(new DNA(Nucleotid.C), testSystem)));
        main.add(new SpeciesParticle(-1,0,1,
                new Species(new DNA(Nucleotid.G), testSystem)));
        main.add(new SpeciesParticle(-1,0,2,
                new Species(new DNA(Nucleotid.T), testSystem)));
        main.add(new SpeciesParticle(-1,0,3,
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
            SpeciesParticle mainParticle = new SpeciesParticle(-1,0, i,
                    new Species(new DNA(), testSystem));
            particles.add(mainParticle);
            main.add(mainParticle);
        }
        for (int i = -1; i < amount; i++) {
            particles.add(new SpeciesParticle(-1, 0, i, new Species(new DNA(Nucleotid.C), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, i, new Species(new DNA(Nucleotid.G), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, i, new Species(new DNA(Nucleotid.T), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, i, new Species(new DNA(Nucleotid.A), testSystem)));
        }
        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        KMeans.run(particles,species);
    }

    private static void testSame() {
        /*Ecosystem testSystem = new Ecosystem();
        List<SpeciesParticle> main = new ArrayList<>();
        List<SpeciesParticle> particles = new ArrayList<>();
        List<Species> species = new ArrayList<>();

        main.add(new SpeciesParticle(-1,0, 0,
                new Species(new DNA(Nucleotid.C), testSystem)));
        main.add(new SpeciesParticle(-1,0, 1,
                new Species(new DNA(Nucleotid.G), testSystem)));
        main.add(new SpeciesParticle(-1,0, 2,
                new Species(new DNA(Nucleotid.T), testSystem)));
        main.add(new SpeciesParticle(-1,0,3,
                new Species(new DNA(Nucleotid.A), testSystem)));

        for (int i = -1; i < 10; i++) {
            particles.add(new SpeciesParticle(-1, 0, i, new Species(new DNA(Nucleotid.C), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, i, new Species(new DNA(Nucleotid.G), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, i, new Species(new DNA(Nucleotid.T), testSystem)));
            particles.add(new SpeciesParticle(-1, 0, i, new Species(new DNA(Nucleotid.A), testSystem)));
        }
        for(SpeciesParticle s : main){
            System.out.println(s.getDNA().getDNA());
            species.add(s.getSpecies());
        }

        KMeans.run(particles, species);*/

        // gibt nicht exakt die selben zentroiden wie die ursprünglichen DNAs zurück??

        List<DebugSpecimen> specimens = new ArrayList<>();
        List<Species> species = new ArrayList<>();

        DebugSpecimen.makeSpecimens(2, 10, false, 0, species, specimens);

        System.out.println("specimens");
        specimens.forEach(specimen -> {
            System.out.println(specimen.getDNA());
        });
        System.out.println("species");
        species.forEach(aSpecies -> {
            System.out.println(aSpecies.getDNA());
        });


        KMeans kMeans = new KMeans(specimens, species);

        kMeans.run();

        species.forEach(aSpecies -> {
            System.out.println(aSpecies.getDNA());
        });



    }

    /**
     * Test for correct Binary Vectors
     */
    private static void testBinaryVectors() {
        DataPoint da0 = new DataPoint("ATGCTA");
        DataPoint da1 = new DataPoint("T");
        DataPoint da2 = new DataPoint("G");
        DataPoint da3 = new DataPoint("C");
        System.out.println(da0.toDNA());
        System.out.println(DataPoint.asString(da0.getBinaryVector()));
        System.out.println(DataPoint.asString(da1.getBinaryVector()));
        System.out.println(DataPoint.asString(da2.getBinaryVector()));
        System.out.println(DataPoint.asString(da3.getBinaryVector()));
    }
}