package Genom;

public enum InteractionType {
    /**
     * the entities should attract each other
     */
    ATTRACT,
    /**
     * the entities should repel each other
     */
    REPEL,
    /**
     * the entities should be kept in constant distance to each other
     */
    SPRING,
    /**
     * the entities should not interact with each other
     */
    NEUTRAL;

    @Override
    public String toString() {
        return switch (this) {
            case ATTRACT -> "→←";
            case REPEL -> "←→";
            case SPRING -> "spring";
            case NEUTRAL -> "◌";
        };
    }

}
