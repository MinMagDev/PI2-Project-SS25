package Particle;

public class Vector2D {
    public double x;
    public double y;
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }
    public Vector2D multiplyBy(double lambda) {
        this.x *= lambda;
        this.y *= lambda;
        return this;
    }
    public double distanceTo(Vector2D v) {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    public Vector2D to(Vector2D v) {
        return new Vector2D(v.x - this.x, v.y - this.y);
    }
}
