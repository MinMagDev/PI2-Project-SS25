package Canvas;

import Species.SpeciesParticle;

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

    /**
     *
     * @return if the Paticle is still alive
     */
    boolean isAlive();

    /**
     *
     * @return if the particle should reproduce
     */
    boolean isReproducing();
    void setReproducing(boolean b);

    default SpeciesParticle newChild(){
        return null;
    }

}
