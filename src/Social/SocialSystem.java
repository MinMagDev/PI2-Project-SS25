package Social;

import java.util.List;

public class SocialSystem {

   private static final double FALLBACK_RADIUS = 100;

   private final double RADIUS_MULTIPLIER;

   /**
    * the entities that make up the system
    */
   List<? extends SocialEntity> entities;

   public SocialSystem(List<? extends SocialEntity> entities)
   {
      this.RADIUS_MULTIPLIER = 10;
      this.entities = entities;
   }

   public SocialSystem(double radiusMultiplier, List<? extends SocialEntity> entities) {
      this.RADIUS_MULTIPLIER = radiusMultiplier;
      this.entities = entities;
   }

   /**
    * calculates the interactions between the entities
    */
   public void triggerInteractions(){
      for(var interactor : entities){
         for(var interactee: entities){
            if(interactor.equals(interactee)){
               continue;
            }
            var interactorSpecies = interactor.getSpecies();
            var interactorSocialRadius = interactorSpecies != null ? interactorSpecies.getInteractionRadius() * RADIUS_MULTIPLIER : FALLBACK_RADIUS;
            if(interactor.getPosition().distanceTo(interactee.getPosition()) < interactorSocialRadius){
               interactor.interactWith(interactee);
            }
         }
      }
   }

}
