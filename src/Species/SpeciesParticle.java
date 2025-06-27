package Species;

import Genom.DNA;
import Particle.Vector2D;

import Particle.Particle;

import java.awt.*;
import java.util.List;
import java.util.Random;
import Canvas.*;
import World.World;


public class SpeciesParticle extends Particle implements SpeciesSocialEntity, DrawableParticle {


    private final Species species;
    private boolean alive = true;
    private boolean reproduce = false;

    private int reproductionCount = 0;
    private final int MAX_REPRO_COUNT = 100;
    public final int EXPECTED_MUTATIONS = 2;

    private final double interactionRadius;

    @Override
    public int getXForDrawing() {
        return (int) position.getX();
    }

    @Override
    public int getYForDrawing() {
        return (int) position.getY();
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
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public static double SPEED_MULTIPLIER = 10;


    @Override
    public double getInteractionRadius() {
        return interactionRadius;
    }

    public Species getSpecies() {
        return species;
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

    private final Color color;

    public SpeciesParticle(double canvasWidth, double canvasHeight, Species species){
        super(0, 0, 3);
        Random random = new Random();
        this.position.setX(Math.round(random.nextDouble() * canvasWidth));
        this.position.setY(Math.round(random.nextDouble() * canvasHeight));
        this.color =  species.getColor();
        this.interactionRadius = species.getInteractionRadius();
        this.addForce(new Vector2D(true));
        this.species = species;
    }



    @Override
    public void update() {
        super.update();
        growConst(-species.getHunger());
        if (getSize() <= 0) this.kill();
        if (Math.random() <= species.getReproductionProb()) reproductionCount++;
        if (reproductionCount >= MAX_REPRO_COUNT){
            System.out.println("Reproduce");
            reproduce = true;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        this.alive = false;
    }

    public static SpeciesParticle[] makeParticles(int amount, Species species, int width, int height) {
        Random r = new Random();
        SpeciesParticle[] result = new SpeciesParticle[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = new SpeciesParticle(width, height, species);
        }
        return result;
    }


    public boolean isReproducing() {
        return reproduce;
    }

    public void setReproducing(boolean reproduce) {
        this.reproduce = reproduce;
    }

    @Override
    public SpeciesParticle newChild() {
        DNA newDNA = new DNA(species.getDNA().mutate(EXPECTED_MUTATIONS));
        Species newSpecies = getSpecies();
        newSpecies.setDNA(newDNA);
        SpeciesParticle newParticle = new SpeciesParticle(0,0, newSpecies);
        newParticle.setPosition(position);
        return newParticle;
    }
}

