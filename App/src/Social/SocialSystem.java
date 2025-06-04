package Social;

import java.util.List;

public class SocialSystem {
   static final double INTERACTION_RADIUS = 10000;


   List<? extends SocialEntity> entities;

   public SocialSystem(List<? extends SocialEntity> entities) {
      this.entities = entities;
   }

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
