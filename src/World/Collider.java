package World;

import Particle.Vector2D;

public interface Collider {
    Vector2D getPosition();
    double getRadius();
    void addForce(Vector2D force);
}
