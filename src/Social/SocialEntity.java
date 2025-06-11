package Social;

import Particle.Vector2D;
import Species.Species;

public interface SocialEntity {
    /**
     * @return the entity's position in space (to calculate in whose interaction radius it is)
     */
    Vector2D getPosition();

    /**
     * the interact function that gets called on every entity in this entity's social radius
     * @param interactee the other entity to interact with
     */
    void interactWith(SocialEntity interactee);

    /**
     * @return the other entity's species
     */
    Species getSpecies();

    void kill();

    void addForce(Vector2D force);

}
