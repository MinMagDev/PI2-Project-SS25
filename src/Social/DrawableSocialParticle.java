package Social;


import Canvas.DrawableParticle;

/**
 * combination of the interfaces because ? extends doesn't support multiple interfaces
 */

public interface DrawableSocialParticle extends DrawableParticle, SocialEntity { }
