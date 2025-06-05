package Canvas;

import java.awt.*;

public interface DrawableParticle {
    /**
     * @return the x-coordinate of the particle
     */
    int getXForDrawing();

    /**
     * @return the y-coordinate of the particle
     */
    int getYForDrawing();

    /**
     * @return the particle's radius
     */
    int getRadiusForDrawing();

    /**
     * @return the particle's color
     */
    Color getColor();

    /**
     * Updates for the next frame
     */
    void update();
}
