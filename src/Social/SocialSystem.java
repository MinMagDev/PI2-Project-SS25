package Social;

import LifeAndDeath.EntityManager;

import java.util.List;

public class SocialSystem<T extends SocialEntity> implements EntityManager<T> {

   /**
    * the entities that make up the system
    */
   List<T> entities;

   public SocialSystem(List<T> entities) {
      this.entities = entities;
   }

   /**
    * calculates the interactions between the entities
    */
   public void triggerInteractions() {
      for (int i = 0; i < entities.size(); i++) {
         var interactor = entities.get(i);
         for (int j = 0; j < entities.size(); j++) {
            if (i == j) {
               continue;
            }
            var interactee = entities.get(j);
            if (interactor.getPosition().distanceTo(interactee.getPosition()) < interactor.getInteractionRadius()) {
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
