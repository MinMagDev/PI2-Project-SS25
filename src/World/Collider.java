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
     * adds a force to the collider
     * @param force the force
     */
    void addForce(Vector2D force);

    /**
     * called each frame on every collider with every other collider to simulate collisions
     * @param collider the other collider
     */
    void checkCollision(Collider collider);


    /**
     * sets the colliders x position
     * @param x the new x position
     */
    void setX(double x);

    /**
     * sets the colliders y position
     * @param y new y position
     */
    void setY(double y);

    /**
     * sets the colliders position
     */
    void setPosition(Vector2D position);

    /**
     * adds a force to the collider which is not checked by MAX_SPEED
     * used for the zap button
     * @param force the force vector
     */
    void addUnlimitedForce(Vector2D force);

}
