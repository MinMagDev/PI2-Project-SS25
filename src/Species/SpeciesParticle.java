package Species;

import Genom.InteractionType;
import Particle.Vector2D;


import Particle.Particle;
import Social.DrawableSocialParticle;
import Social.SocialEntity;

import java.awt.*;
import java.util.Random;
import Canvas.*;


public class SpeciesParticle extends Particle implements SpeciesSocialEntity, DrawableParticle {


    private final Species species;
    private boolean alive = true;

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

    public static double SPEED_MULTIPLIER = 10;


    @Override
    public double getInteractionRadius() {
        return interactionRadius;
    }

    public Species getSpecies() {
        return species;
    }



    private final Color color;

    public SpeciesParticle(double canvasWidth, double canvasHeight, Species species){
        super(0, 0, 3);
        Random random = new Random();
        this.position.setX(Math.round(random.nextDouble() * canvasWidth));
        this.position.setY(Math.round(random.nextDouble() * canvasHeight));
        this.color =  species.getColor();
        this.interactionRadius = species.getInteractionRadius();
        this.addForce(new Vector2D(0.5, 0.5));
        this.species = species;
    }



    @Override
    public void update() {
        super.update();

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







}

