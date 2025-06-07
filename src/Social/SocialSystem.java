package Social;

import java.util.List;

public class SocialSystem {


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
            var interactorSpecies = interactor.getSpecies();
            var interactorSocialRadius = interactorSpecies != null ? interactorSpecies.getInteractionRadius() * 10 : 100;
            if(interactor.getPosition().distanceTo(interactee.getPosition()) < interactorSocialRadius){
               interactor.interactWith(interactee);
            }
         }
      }
   }

}
