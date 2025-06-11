package Species;

import Particle.Vector2D;


import Particle.Particle;
import Social.DrawableSocialParticle;
import Social.SocialEntity;

import java.awt.*;
import java.util.Random;


public class SpeciesParticle extends Particle implements DrawableSocialParticle {
    private static final double EAT_THREASHOLD = 1;
    private final Species species;
    private boolean alive = true;

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
    public void interactWith(SocialEntity interactee) {
        if(interactee == this) return;
        int reaction = interactee.getSpecies().getInteractionWith(species);
        Vector2D toInteractee = getPosition().to(interactee.getPosition());
        if (reaction == 1 && toInteractee.length() <= EAT_THREASHOLD) {
            interactee.kill();
            //System.out.println("Killed: " + interactee);
        }
        toInteractee.normalize();
        toInteractee.mul(reaction * species.getSpeed());
        addForce(toInteractee);
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

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        this.alive = false;
    }

    static SpeciesParticle[] makeParticles(int amount, Species species, int width, int height) {
        Random r = new Random();
        SpeciesParticle[] result = new SpeciesParticle[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = new SpeciesParticle(width, height, species);
        }
        return result;
    }



}

