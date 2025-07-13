package Social;

import Particle.Entity;
import Particle.Vector2D;

public interface SocialEntity<Interactee extends SocialEntity<Interactee>> extends Entity {

    double getSpeed();

    /**
     * @return the entity's position in space (to calculate in whose interaction radius it is)
     */
    Vector2D getPosition();

    /**
     * the interact function that gets called on every entity in this entity's social radius
     * @param interactee the other entity to interact with
     */
    default void interactWith(Interactee interactee) {
        Vector2D toInteractee = getPosition().to(interactee.getPosition());
        toInteractee.normalize();
        toInteractee.mul(getSpeed());
        this.addForce(toInteractee);
    }


    double getInteractionRadius();

    void addForce(Vector2D force);

}
