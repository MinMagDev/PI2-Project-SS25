package Particle;
import World.Collider;

import java.util.LinkedList;
import java.util.List;


public abstract class Particle implements Collider {
    private class ForceManager {
        final double MAX_SPEED = 3;


        private List<Vector2D> forces;
        private Vector2D velocity = new Vector2D(0, 0);

        public ForceManager(){
            forces = new LinkedList<Vector2D>();
        }
        public void add(Vector2D force){
            forces.add(force);
        }
        public void setSpeed(double speed){
            velocity.normalize();
            velocity.mul(speed);
        }


        /**
         * Clears the forces List.
         */
        public void clear() {
            forces.clear();
        }

        private void updateVelocity(){
            velocity.add(Vector2D.massSum(forces));
            if(velocity.length() > MAX_SPEED){
               setSpeed(MAX_SPEED);
            }
            clear();
        }

        /**
         * calculates the velocity as the sum of all forces
         * @return the velocity vector
         */
        public Vector2D getVelocity(){
             updateVelocity();
            return velocity;
        }
    }

    protected Vector2D position;

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public double getSpeed() {
        return getVelocity().length();
    }

    @Override
    public void setSpeed(double speed) {
        forceManager.setSpeed(speed);
    }

    @Override
    public void checkCollision(Collider collider) {
        Vector2D meToCollider = getPosition().to(collider.getPosition());
        if(meToCollider.length() < collider.getRadius()){
            collider.addForce(meToCollider);
        }
    }

    protected double radius;





    private ForceManager forceManager;


    public Particle(double x, double y){
        this.position = new Vector2D(x, y);
        this.forceManager = new ForceManager();
    }

    /**
     * calculates the velocity as the sum of all forces
     * @return the velocity vector
     */
    public Vector2D getVelocity(){
        return forceManager.getVelocity();
    }

    /**
     * adds a force to the particle
     * @param force the force
     */
    public void addForce(Vector2D force){
        forceManager.add(force);
    }

    /**
     * per frame update
     */
    public void update(){
        Vector2D oldPosition = position;
        position.add(getVelocity());
        //System.out.println("Old: " + oldPosition.toString() + " New: " + position.toString());
    }

}
