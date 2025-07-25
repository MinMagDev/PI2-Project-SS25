package Particle;

import java.awt.*;
import java.util.List;
import java.util.Random;

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

    public Vector2D(Vector2D vec) {
        this.set(vec);
    }

    public Vector2D(boolean random){
        Random r = new Random();
        this.x = r.nextDouble(2)-1;
        this.y = r.nextDouble(2)-1;
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
    /**
     * If you need to add more Vectors together
     * @param vecs an Array of Vectors to add
     * @return the sum of all Vectors
     */
    public static Vector2D massSum(List<Vector2D> vecs){
        double resultX = 0.0d;
        double resultY = 0.0d;
        for(final Vector2D vec : vecs){
            resultX += vec.x;
            resultY += vec.y;
        }
        return new Vector2D(resultX, resultY);
    }

    /**
     * makes this vector (0, 0)
     */

    public void zero(){
        x = 0.0d;
        y = 0.0d;
    }

    //------------------------------------------------------------
    //Different Methods to substract Vectors from another

    /**
     * Substraczs the values of another Vector from this Vector
     * @param other the Vector to substract
     */
    public Vector2D sub(Vector2D other){
        this.x -= other.x;
        this.y -= other.y;
        return this;
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

    /**
     * Multilpies this by a skalar
     * @param lambda The skalar
     */
    public Vector2D mul(double lambda) {
        this.x *= lambda;
        this.y *= lambda;
        return this;
    }

    /**
     * Multiplies a given Vector by a skalar and saves it into an output Vector
     * @param lambda The skalar
     * @param in The input Vector
     * @param out The Vector to save the product to.
     */
    public static void mul(double lambda, Vector2D in, Vector2D out){
        out.x = in.x * lambda;
        out.y = in.y * lambda;
    }

    /**
     * returns a new Vector, that is a Skalar of an Input vector
     * @param lambda The Skalar
     * @param vec the input
     * @return The Product Vector
     */
    public static Vector2D mul(double lambda, Vector2D vec){
        Vector2D out = new Vector2D();
        mul(lambda, vec, out);
        return out;
    }

    //------------------------------------------------------------------------
    //Copy/set Methods:

    /**
     * Sets x and y equal to the input Vector
     * @param in The reference Vector
     */
    public void set(Vector2D in) {
        this.x = in.x;
        this.y = in.y;
    }

    /**
     * Copys the values of this into an out Vector
     * @param out The Vector to Copy the Vector in
     */
    public void copy(Vector2D out){
        out.x = this.x;
        out.y = this.y;
    }

    public static Vector2D random(){
        return new Vector2D(Math.random() > 0.5 ? Math.random() : -1* Math.random(), Math.random() > 0.5 ? Math.random() : -1* Math.random());
    }

    /**
     * Copys an Vector into another
     * @param vec1 Input Vector
     * @param vec2 Output Vector
     */
    public static void  copy(Vector2D vec1, Vector2D vec2){
        vec1.copy(vec2);
    }

    //----------------------------------------------------------------

    public void normalize() {
        if(x == 0.0d && y == 0.0d){
            return;
        }
        double length = this.length();
        this.mul(1/length);
    }

    public double distanceTo(Vector2D other){
        return sub(this, other).length();
    }

    /**
     * calculates the non-normalized direction to another vector
     * @param v the other vector
     * @return the direction
     */

    public Vector2D to(Vector2D v) {
        return new Vector2D(v.x - this.x, v.y - this.y);
    }

    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    public void print(){
        System.out.println(this.toString());
    }

    /**
     * converts java.awt.point to a Vector2D
     */
    public static Vector2D fromPoint(Point point){
        return new Vector2D(point.x, point.y);
    }

}
