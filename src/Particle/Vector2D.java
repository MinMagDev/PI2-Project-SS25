package Particle;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D() {
        this.x = 0.0d;
        this.y = 0.0d;
    }

    public Vector2D(double xy){
        this.x = xy;
        this.y = xy;
    }

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double length(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    //----------------------------------------------------------------------
    //Different Methods to add two Vectors:


    /**
     * Adds the parameters of another Vector to this Vector
     * @param other the Vector to add
     */
    public void add(Vector2D other){
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Adds two vectors together and saves it into the third
     * @param vec1 The first input Vector
     * @param vec2 The second input Vector
     * @param out The output Vector, in which the result is stored
     */
    public static void add(Vector2D vec1, Vector2D vec2, Vector2D out){
        out.x = vec1.x + vec2.x;
        out.y = vec1.y + vec2.y;
    }

    /**
     * Returns the sum of two Vectors
     * @param vec1 The first input Vector
     * @param vec2 The second input Vector
     * @return A new Vector, which is the sum of the inputs
     */
    public static Vector2D add(Vector2D vec1, Vector2D vec2){
        return new Vector2D(vec1.x+vec2.x,vec1.y+vec2.y);
    }

    /**
     * If you need to add more Vectors together
     * @param vecs an Array of Vectors to add
     * @return the sum of all Vectors
     */
    public static Vector2D massSum(Vector2D[] vecs){
        Vector2D result = new Vector2D();
        for(final Vector2D vec : vecs){
            add(result, vec, result);
        }
        return result;
    }

    //------------------------------------------------------------
    //Different Methods to substract Vectors from another

    /**
     * Substraczs the values of another Vector from this Vector
     * @param other the Vector to substract
     */
    public void sub(Vector2D other){
        this.x -= other.x;
        this.y -= other.y;
    }


    /**
     * Substracts two vectors from another and saves it into the third
     * @param vec1 The first input Vector
     * @param vec2 The second input Vector
     * @param out The output Vector, in which the result is stored
     */
    public static void sub(Vector2D vec1, Vector2D vec2, Vector2D out){
        out.x = vec1.x - vec2.x;
        out.y = vec1.y - vec2.y;
    }

    /**
     * Returns the differenc of two Vectors
     * @param vec1 The first input Vector
     * @param vec2 The second input Vector
     * @return A new Vector, which is the difference of the inputs
     */
    public static Vector2D sub(Vector2D vec1, Vector2D vec2){
        return new Vector2D(vec1.x-vec2.x, vec1.y -vec2.y);
    }

    //---------------------------------------------------------------------
    //Multiply functions

    //---------------------------------------------------------------------

    public Vector2D multiplyBy(double lambda) {
        this.x *= lambda;
        this.y *= lambda;
        return this;
    }

    public double distanceTo(Vector2D other){
        return sub(this, other).length();
    }

    public Vector2D to(Vector2D v) {
        return new Vector2D(v.x - this.x, v.y - this.y);
    }

}
