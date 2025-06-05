package Particle;


import Social.DrawableSocialParticle;
import Social.SocialEntity;

import java.awt.*;
import java.util.Random;

public class DebugParticle extends Particle implements DrawableSocialParticle {
    @Override
    public int getX() {
        return (int) position.getX();
    }

    @Override
    public int getY() {
        return (int) position.getY();
    }

    @Override
    public int getRadius() {
        return radius;
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
        Vector2D toInteractee = getPosition().to(interactee.getPosition());
        toInteractee.mul(0.001);
        addForce(toInteractee);
    }

    public int radius;
    public Color color;

    public DebugParticle(int x, int y, int radius, Color color){
        super(x, y);
        this.radius = radius;
        this.color = color;
    }
    public DebugParticle(double canvasWidth, double canvasHeight){
        super(0, 0);
        Random random = new Random();
        this.position.setX(Math.round(random.nextDouble() * canvasWidth));
        this.position.setY(Math.round(random.nextDouble() * canvasHeight));
        this.radius = 5;
        this.color = Color.BLACK;
        //this.addForce(new Vector2D(random.nextDouble(), random.nextDouble()).multiplyBy(5));
    }

    /**
     * creates an example array of DebugParticles in a given start area
     * @param size number of particles
     * @param canvasWidth width of the start area
     * @param canvasHeight height of the start area
     * @return the example array
     */

    public static DebugParticle[] createExampleArray(int size, double canvasWidth, double canvasHeight){
        DebugParticle[] particles = new DebugParticle[size];
        for(int i = 0; i < size; i++){
            particles[i] = new DebugParticle(canvasWidth, canvasHeight);
        }
        return particles;
    }


}
