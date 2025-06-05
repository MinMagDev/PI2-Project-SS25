package Canvas;

import java.awt.*;

public interface DrawableParticle {
    /**
     * @return the x-coordinate of the particle
     */
    int getX();

    /**
     * @return the y-coordinate of the particle
     */
    int getY();

    /**
     * @return the particle's radius
     */
    int getRadius();

    /**
     * @return the particle's color
     */
    Color getColor();

    /**
     * Updates for the next frame
     */
    void update();
}
