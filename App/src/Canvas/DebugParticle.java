package Canvas;

import java.awt.*;
import java.util.Random;

public class DebugParticle implements DrawableParticle {
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public int x;
    public int y;
    public int radius;
    public Color color;
    public DebugParticle(int x, int y, int radius, Color color){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
    public DebugParticle(double canvasWidth, double canvasHeight){
        Random random = new Random();
        this.x = (int)(random.nextDouble() * canvasWidth);
        this.y = (int)(random.nextDouble() * canvasHeight);
        this.radius = 2;
        this.color = Color.BLACK;
    }
    static DebugParticle[] createExampleArray(int size, double canvasWidth, double canvasHeight){
        DebugParticle[] particles = new DebugParticle[size];
        for(int i = 0; i < size; i++){
            particles[i] = new DebugParticle(canvasWidth, canvasHeight);
        }
        return particles;
    }

    @Override
    public void update() {
        this.x++;
    }
}
