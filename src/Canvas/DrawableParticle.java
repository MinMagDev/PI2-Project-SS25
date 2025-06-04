package Canvas;

import java.awt.*;

public interface DrawableParticle {
    int getX();
    int getY();
    int getRadius();
    Color getColor();
    void update();
}
