package Canvas;

import java.awt.*;

public interface DrawableParticle {
    public int getX();
    public int getY();
    public int getRadius();
    public Color getColor();
    public void update();
}
