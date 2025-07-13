package Canvas;

import java.awt.*;

/**
 * a drawable that doesn't draw anything
 */

public class EmptyCanvas implements Drawable {
    @Override
    public void draw(Graphics g) {
        // empty
    }

    @Override
    public void update() {
        // empty
    }

}
