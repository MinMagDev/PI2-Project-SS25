package Social;

import java.util.List;

public class SocialSystem {

   /**
    * interaction radius to simulate sensing (and reduce complexity)
    */
   static final double INTERACTION_RADIUS = 10000;

   /**
    * the entities that make up the system
    */
   List<? extends SocialEntity> entities;

   public SocialSystem(List<? extends SocialEntity> entities) {
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
            if(interactor.getPosition().distanceTo(interactee.getPosition()) < INTERACTION_RADIUS){
               interactor.interactWith(interactee);
            }
         }
      }
   }

}
