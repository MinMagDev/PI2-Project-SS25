package Canvas;

import java.awt.*;

/**
* Drawables can be drawn using the RendererPanel
 */

public interface Drawable {
    /**
    * Instructions for drawing
    * @param g java.awt.Graphics Object for drawing instriuctions
     */
    void draw(Graphics g);

    /**
     * Updates for the next frame
     */
    void update();
}
