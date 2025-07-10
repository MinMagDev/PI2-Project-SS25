package Particle;


import Social.SocialEntity;
import Canvas.DrawableParticle;

import java.awt.*;
import java.util.Random;

public class DebugParticle extends Particle implements DrawableParticle, SocialEntity<DebugParticle> {
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


    @Override
    public double getInteractionRadius() {
        return 100;
    }

    public Color color;

    public DebugParticle(int x, int y, int radius, Color color){
        super(x, y, 0, radius);
        this.color = color;
    }
    public DebugParticle(double canvasWidth, double canvasHeight){
        super(0, 0, 0, 5);
        Random random = new Random();
        this.position.setX(Math.round(random.nextDouble() * canvasWidth));
        this.position.setY(Math.round(random.nextDouble() * canvasHeight));
        this.color = Color.BLACK;
        this.addForce(new Vector2D(0.5, 0.5));
    }

    @Override
    public void update() {
        super.update();

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
