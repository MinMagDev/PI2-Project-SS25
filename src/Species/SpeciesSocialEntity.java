package Species;

import Genom.DNA;
import Genom.InteractionType;
import Particle.Vector2D;
import Social.SocialEntity;

/**
 * manages interaction between particles of different species
 */

public interface SpeciesSocialEntity extends SocialEntity<SpeciesSocialEntity> {

    double SPRING_FORCE = 1;

    void growConst(double size);

    void growFac(double factor);

    /**
     * @return this entity's species
     */
    Species getSpecies();

    /**
     * @return this entity's DNA
     */
    DNA getDNA();

    @Override
    default void interactWith(SpeciesSocialEntity interactee) {
        InteractionType reaction = getInteractionTypeWith(interactee);
        Vector2D toInteractee = getPosition().to(interactee.getPosition());
        double distanceToInteractee = toInteractee.length();
        switch (reaction) {
            case NEUTRAL:
                break;
            case ATTRACT:
                if(interactee.getSpecies() != this.getSpecies()
                        && distanceToInteractee <= 1 // verhindert, dass Partikel zu gross werden
                        && interactee.getSize() <= this.getSize()
                ) {
                    interactee.kill();
                    this.growFac(interactee.getSize() * 0.1d);
                }
                if(interactee.getSpecies() == this.getSpecies()
                && distanceToInteractee <= 4) break;
                toInteractee.normalize();
                toInteractee.mul(getDNA().getSpeed());
                this.addForce(toInteractee);
                break;
            case REPEL:
                toInteractee.normalize();
                toInteractee.mul(-1 * getSpeed() * 1/distanceToInteractee);
                this.addForce(toInteractee);
                break;
            case SPRING:
                final double force = (distanceToInteractee - getSpeed()) * SPRING_FORCE;
                toInteractee.mul(force/distanceToInteractee);
                this.addForce(toInteractee.mul(-1));
                break;
        }
    }

    void kill();

    /**
     * @param other the other entity
     * @return the interaction with the other entity
     */
    default InteractionType getInteractionTypeWith(SpeciesSocialEntity other){
        return getDNA().getInteractionWith(other.getSpecies());
    }

}
