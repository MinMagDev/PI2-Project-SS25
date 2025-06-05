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

        /**
         * Clears the forces List.
         */
        public void clear() {
            forces.clear();
        }

        /**
         * calculates the velocity as the sum of all forces
         * @return the velocity vector
         */
        public Vector2D getVelocity(){
             velocity.add(Vector2D.massSum(forces));
             if(velocity.length() > MAX_SPEED){
                 velocity.normalize();
                 velocity.mul(MAX_SPEED);
             }
            return velocity;
        }
    }

    protected Vector2D position;

    @Override
    public double getRadius() {
        return radius;
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
        forceManager.clear();
        //System.out.println("Old: " + oldPosition.toString() + " New: " + position.toString());
    }

}
