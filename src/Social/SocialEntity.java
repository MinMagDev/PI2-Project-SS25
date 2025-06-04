package Social;

import Particle.Vector2D;

import java.util.function.Consumer;

public interface SocialEntity {
    Vector2D getPosition();
    void interactWith(SocialEntity interactee);
}
