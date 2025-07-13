package Species;

import Cluster.Specimen;
import Genom.DNA;
import LifeAndDeath.ReproducingParticle;
import Particle.Vector2D;

import Particle.Particle;

import java.awt.*;
import java.util.Random;
import Canvas.*;


public class SpeciesParticle extends Particle implements SpeciesSocialEntity, DrawableParticle, ReproducingParticle, Specimen {

    private Species species;
    private DNA dna;

    private boolean alive = true;
    private boolean reproduce = false;

    private int reproductionCount = 0;
    private static final int MAX_REPRODUCTION_COUNT = 200;
    public static final int EXPECTED_MUTATIONS = 4;
    static final int RADIATION_MUTATIONS = 200;


    private final double speed;
    private double interactionRadius;
    private double speedMultiplier;
    private final double interactionRadiusMultiplier;

    @Override
    public int getXForDrawing() {
        return (int) position.getX() - getRadiusForDrawing();
    }

    @Override
    public int getYForDrawing() {
        return (int) position.getY() - getRadiusForDrawing();
    }

    @Override
    public int getRadiusForDrawing() {
        return (int) radius;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Vector2D getPosition() {
        return super.position;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }



    @Override
    public double getInteractionRadius() {
        return interactionRadius;
    }

    public Species getSpecies() {
        return species;
    }

    public DNA getDNA() {
        return dna;
    }

    public double getSize() {
        return radius;
    }

    public void setSize(double size) {
        setRadius(size);
    }

    public void growConst(double size) {
        this.setSize(radius + size);
    }

    public void growFac(double factor) {
        this.setSize(radius + radius * factor);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;



    public SpeciesParticle(double canvasWidth, double canvasHeight, Species species){
        super(0, 0, 3);
        this.dna = species.getDNA();
        Random random = new Random();
        this.position.setX(Math.round(random.nextDouble() * canvasWidth));
        this.position.setY(Math.round(random.nextDouble() * canvasHeight));
        this.color = species.getColor();
        this.interactionRadiusMultiplier = species.getInteractionRadiusMultiplier();
        this.speedMultiplier = species.getSpeedMultiplier();
        this.interactionRadius = dna.getRadius() * interactionRadiusMultiplier;
        this.speed = dna.getSpeed() * speedMultiplier;
        this.addForce(Vector2D.random());
        this.species = species;
    }



    @Override
    public void update() {
        super.update();
        growConst(-dna.getHunger());
        if (getSize() <= 0) this.kill();
        if (Math.random() <= dna.getReproductionProbability()) reproductionCount++;
        if (reproductionCount >= MAX_REPRODUCTION_COUNT){
            //System.out.println("Reproduce");
            reproduce = true;
            reproductionCount = 0;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        this.alive = false;
    }

    public static SpeciesParticle[] makeParticles(int amount, Species species, int width, int height) {
        SpeciesParticle[] result = new SpeciesParticle[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = new SpeciesParticle(width, height, species);
        }
        return result;
    }

    @Override
    public boolean isReproducing() {
        return reproduce;
    }

    @Override
    public void setReproducing(boolean reproduce) {
        this.reproduce = reproduce;
    }

    @Override
    public SpeciesParticle newChild() {
        DNA newDNA = dna.mutated(EXPECTED_MUTATIONS);
        if(newDNA.getDNA().isEmpty()) {
            return null;
        }
        Species newSpecies = getSpecies();
        SpeciesParticle newParticle = new SpeciesParticle(0,0, newSpecies);
        newParticle.setDNA(newDNA);
        newParticle.setPosition(position);
        return newParticle;
    }

    public void updateValues(){
        interactionRadius = dna.getRadius() * interactionRadiusMultiplier;
        speedMultiplier = dna.getSpeed() * speedMultiplier;
    }

    @Override
    public void updateSpecies(Species species) {
            boolean update = !species.getColor().equals(color);
            if(update){
                System.out.println("updateSpecies");
                System.out.println(Ecosystem.colorText(color, "old"));
            }

            this.species = species;
            this.color = species.getColor();
            if(update){
                System.out.println(Ecosystem.colorText(species.getColor(), "new"));
            }
    }

    public void setDNA(DNA dna) {
        this.dna = dna;
        updateValues();
    }

    public void irradiate(){
        DNA newDNA = dna.mutated(RADIATION_MUTATIONS);
        setDNA(newDNA);
    }
}

