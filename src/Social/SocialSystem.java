package Social;

import LifeAndDeath.EntityManager;

import java.util.List;

public class SocialSystem<T extends SocialEntity> implements EntityManager<T> {

   private static final double FALLBACK_RADIUS = 100;

   private final double RADIUS_MULTIPLIER;

   /**
    * the entities that make up the system
    */
   List<T> entities;

   public SocialSystem(List<T> entities) {
      this.RADIUS_MULTIPLIER = 10;
      this.entities = entities;
   }

   public SocialSystem(double radiusMultiplier, List<T> entities) {
      this.RADIUS_MULTIPLIER = radiusMultiplier;
      this.entities = entities;
   }

   /**
    * calculates the interactions between the entities
    */
   public void triggerInteractions() {
      for (var interactor : entities) {
         for (var interactee : entities) {
            if (interactor.equals(interactee)) {
               continue;
            }
            var interactorSpecies = interactor.getSpecies();
            var interactorSocialRadius = interactorSpecies != null ? interactorSpecies.getInteractionRadius() * RADIUS_MULTIPLIER : FALLBACK_RADIUS;
            if (interactor.getPosition().distanceTo(interactee.getPosition()) < interactorSocialRadius) {
               interactor.interactWith(interactee);
            }
         }
      }
   }


   @Override
   public void addEntity(T e) {
      entities.add(e);
   }

   @Override
   public void removeEntity(T e) {
      entities.remove(e);
   }
}
