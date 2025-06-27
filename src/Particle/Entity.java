package Particle;

public interface Entity {

    Vector2D getPosition();
    void setPosition(Vector2D position);

    double getSize();
    void setSize(double size);

    void growConst(double size);
    void growFac(double factor);
}
