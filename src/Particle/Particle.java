package Particle;
import World.Collider;

import java.util.LinkedList;
import java.util.List;


public abstract class Particle implements Collider, Entity {
    public static double MAX_SPEED = 3;

    public int getParticleID() {
        return particleID;
    }

    private static class ForceManager {


        /**
         * saves the sum of forces (the difference in velocity) between two frames
         */
        private final Vector2D deltaVelocity= new Vector2D(0, 0);

        /**
         * saves a new sum of forces that's not constrained by MAX_SPEED
         */
        private final Vector2D unlimitedDeltaVelocity = new Vector2D(0, 0);

        private final Vector2D velocity = new Vector2D(0, 0);

        public void add(Vector2D force){
            deltaVelocity.add(force);
        }
        public void setSpeed(double speed){
            velocity.normalize();
            velocity.mul(speed);
        }
        public void addUnlimitedForce(Vector2D force){
            unlimitedDeltaVelocity.add(force);
        }


        /**
         * Clears the forces List.
         */
        public void clear() {
            deltaVelocity.zero();
            unlimitedDeltaVelocity.zero();
        }

        private void updateVelocity(){
            velocity.add(deltaVelocity);
            if(velocity.length() > MAX_SPEED){
               setSpeed(MAX_SPEED);
            }
            velocity.add(unlimitedDeltaVelocity);
            clear();

        }

        /**
         * calculates the velocity as the sum of all forces
         * @return the velocity vector
         */
        public Vector2D getVelocity(){
             updateVelocity();
             velocity.mul(0.99);
            return velocity;
        }
    }

    protected Vector2D position;
    private int particleID;


    @Override
    public double getRadius() {
        return radius;
    }
    public void setRadius(double r) {
        this.radius = r;
    }

    @Override
    public double getSpeed() {
        return getVelocity().length();
    }

    @Override
    public void checkCollision(Collider collider) {
        if(collider.equals(this)){
            return;
        }
        Vector2D meToCollider = getPosition().to(collider.getPosition());
        final double distance = meToCollider.length();
        if(distance < collider.getRadius()){
            meToCollider.normalize();
            meToCollider.mul(getRadius() + collider.getRadius());
            Vector2D myPosition = getPosition();
            Vector2D newPosition = Vector2D.add(myPosition, meToCollider);
            collider.setPosition(newPosition);
            collider.addForce(meToCollider);
            this.addForce(meToCollider.mul(-0.1 * distance));
        }
    }

    protected double radius;

    public static double SPEED_MULTIPLIER = 100.0;

    private final ForceManager forceManager;


    public Particle(double x, double y, double radius){
        this.position = new Vector2D(x, y);
        this.radius = radius;
        this.forceManager = new ForceManager();
    }

    @Override
    public void setX(double x) {
        this.position.setX(x);
    }

    @Override
    public void setY(double y) {
        this.position.setY(y);
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

    public void addUnlimitedForce(Vector2D force){
        forceManager.addUnlimitedForce(force);
    }

}
