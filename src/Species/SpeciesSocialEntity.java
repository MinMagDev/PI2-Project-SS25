package Species;

import Genom.InteractionType;
import Particle.Vector2D;
import Social.SocialEntity;

public interface SpeciesSocialEntity extends SocialEntity<SpeciesSocialEntity> {

    double SPRING_FORCE = 1;

    Species getSpecies();

    @Override
    default void interactWith(SpeciesSocialEntity interactee) {
        InteractionType reaction = getInteractionTypeWith(interactee);
        Vector2D toInteractee = getPosition().to(interactee.getPosition());
        switch (reaction) {
            case NEUTRAL:
                break;
            case ATTRACT:
                if(interactee.getSpecies() != this.getSpecies()
                        && toInteractee.length() <= 1
                        && interactee.getSize() <= this.getSize()
                ) {
                    System.out.println("KILL");
                    interactee.kill();
                    this.growFac(interactee.getSize() * 0.1d);
                }
                toInteractee.normalize();
                toInteractee.mul(getSpecies().getSpeed());
                this.addForce(toInteractee);
                break;
            case REPEL:

                toInteractee.normalize();
                toInteractee.mul(-1 * getSpecies().getSpeed());
                this.addForce(toInteractee);
                break;
            case SPRING:
                final double distance = toInteractee.length();
                final double force = (distance - getSpecies().getSpeed()) * SPRING_FORCE;
                toInteractee.mul(force/distance);
                interactee.addForce(toInteractee);
                this.addForce(toInteractee.mul(-1));
                break;
        }
    }

    boolean isAlive();
    void kill();


    default InteractionType getInteractionTypeWith(SpeciesSocialEntity other){
        return getSpecies().getInteractionWith(other.getSpecies());
    }

}
