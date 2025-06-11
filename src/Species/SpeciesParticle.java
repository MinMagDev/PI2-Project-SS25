package Species;

import Genom.InteractionType;
import Particle.Vector2D;


import Particle.Particle;
import Social.DrawableSocialParticle;
import Social.SocialEntity;

import java.awt.*;
import java.util.Random;


public class SpeciesParticle extends Particle implements DrawableSocialParticle {
    private static final double SPRING_FORCE = 3;
    private final Species species;

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
    public void interactWith(SocialEntity interactee) {
        InteractionType reaction = interactee.getSpecies().getInteractionWith(species);
        Vector2D toInteractee = getPosition().to(interactee.getPosition());
        switch (reaction) {
            case NEUTRAL:
                break;
            case ATTRACT:
                toInteractee.normalize();
                toInteractee.mul(species.getSpeed() * SPEED_MULTIPLIER);
                this.addForce(toInteractee);
                break;
            case REPEL:
                toInteractee.normalize();
                toInteractee.mul(-1 * species.getSpeed() * SPEED_MULTIPLIER);
                this.addForce(toInteractee);
                break;
            case SPRING:
                final double distance = toInteractee.length();
                final double force = (distance - species.getSpeed()) * SPRING_FORCE;
                toInteractee.mul(force/distance);
                interactee.addForce(toInteractee);
                this.addForce(toInteractee.mul(-1));
                break;
        }

    }

    @Override
    public Species getSpecies() {
        return species;
    }

    private final Color color;

    public SpeciesParticle(double canvasWidth, double canvasHeight, Species species){
        super(0, 0, 5);
        Random random = new Random();
        this.position.setX(Math.round(random.nextDouble() * canvasWidth));
        this.position.setY(Math.round(random.nextDouble() * canvasHeight));
        this.color =  species.getColor();
        this.addForce(new Vector2D(0.5, 0.5));
        this.species = species;
    }



    @Override
    public void update() {
        super.update();

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

