package Particle;
import java.util.LinkedList;
import java.util.List;


public abstract class Particle {
    private class ForceManager {

        private List<Vector2D> forces;
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
            Vector2D velocity = Vector2D.massSum(forces);
            return velocity;
        }
    }

    protected Vector2D position;


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
