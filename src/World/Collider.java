package World;

import Particle.Vector2D;

/**
 * A circle collider to simulate interactions between objects
 */

public interface Collider {
    // passive methods

    /**
     * @return the collider's center
     */
    Vector2D getPosition();

    /**
     * @return the collider's radius
     */
    double getRadius();

    /**
     * @return the collider's speed (= length of velocity vector)
     */
    double getSpeed();


    // active methods

    /**
     * sets the movement speed of the collider
     * @param speed the new length for the velocity vector
     */
    void setSpeed(double speed);

    /**
     * adds a force to the collider
     * @param force the force
     */
    void addForce(Vector2D force);

    /**
     * called each frame on every collider with every other collider to simulate collisions
     * @param collider the other collider
     */
    void checkCollision(Collider collider);

}
