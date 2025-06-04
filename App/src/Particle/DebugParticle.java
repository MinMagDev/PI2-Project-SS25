package Particle;


import Canvas.*;
import Social.DrawableSocialParticle;
import Social.SocialEntity;

import java.awt.*;
import java.util.Random;

public class DebugParticle extends Particle implements DrawableSocialParticle {
    @Override
    public int getX() {
        return (int) position.x;
    }

    @Override
    public int getY() {
        return (int) position.y;
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
        addForce(getPosition().to(interactee.getPosition()).multiplyBy(0.0001));
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
        this.position.x = Math.round(random.nextDouble() * canvasWidth);
        this.position.y = Math.round(random.nextDouble() * canvasHeight);
        this.radius = 5;
        this.color = Color.BLACK;
        //this.addForce(new Vector2D(random.nextDouble(), random.nextDouble()).multiplyBy(5));
    }
    public static DebugParticle[] createExampleArray(int size, double canvasWidth, double canvasHeight){
        DebugParticle[] particles = new DebugParticle[size];
        for(int i = 0; i < size; i++){
            particles[i] = new DebugParticle(canvasWidth, canvasHeight);
        }
        return particles;
    }


}
