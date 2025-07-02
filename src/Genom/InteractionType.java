package Genom;

/**
 * Describes how an entity should respond to another entity.
 * <p>
 * This is a directional interaction: the current entity is influenced by the other,
 * but not necessarily the other way around.
 *
 * <ul>
 *     <li>{@link #ATTRACT} – The entity moves toward the other.</li>
 *     <li>{@link #REPEL} – The entity moves away from the other.</li>
 *     <li>{@link #SPRING} – The entity tries to maintain a constant distance to the other.</li>
 *     <li>{@link #NEUTRAL} – The entity does not respond to the other at all.</li>
 * </ul>
 *
 * Useful in simulations involving force fields, behavioral models, or controlled avoidance.
 * <p> D.R.E.C.K.
 */
public enum InteractionType {
    /**
     * the entity should get attracted by the other
     */
    ATTRACT,
    /**
     * the entities should be repeled by the other
     */
    REPEL,
    /**
     * the entity should be kept in constant distance to the other
     */
    SPRING,
    /**
     * the entity should not interact with the other
     */
    NEUTRAL
}
