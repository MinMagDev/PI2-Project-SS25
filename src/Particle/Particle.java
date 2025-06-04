package Particle;
import java.util.LinkedList;
import java.util.List;


public abstract class Particle {
    private class ForceManager {
        private static final int MAX_FORCES = 5;

        private List<Vector2D> forces;
        public ForceManager(){
            forces = new LinkedList<Vector2D>();
        }
        public void add(Vector2D force){
            forces.add(force);
        }
        private void trim(){
            while(forces.size() > MAX_FORCES){
                forces.remove(0);
            }
        }
        public Vector2D getVelocity(){
            trim();
            Vector2D velocity = new Vector2D(0, 0);
            for(Vector2D force : forces){
                velocity.add(force);
            }
            return velocity;
        }
    }

    protected Vector2D position;


    private ForceManager forceManager;


    public Particle(double x, double y){
        this.position = new Vector2D(x, y);
        this.forceManager = new ForceManager();
    }

    public Vector2D getVelocity(){
        return forceManager.getVelocity();
    }

    public void addForce(Vector2D force){
        forceManager.add(force);
    }

    public void update(){
        position.add(getVelocity());
    }

}
