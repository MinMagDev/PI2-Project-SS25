package Particle;

/**
 * interface for a basic entity with position and size
 */
public interface Entity {

    Vector2D getPosition();
    void setPosition(Vector2D position);

    double getSize();
    void setSize(double size);

}
