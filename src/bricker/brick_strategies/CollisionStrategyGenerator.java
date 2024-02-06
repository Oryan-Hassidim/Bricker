package bricker.brick_strategies;

/**
 * An interface for generating collision strategies.
 * 
 * @see CollisionStrategy
 * @see BasicCollisionStrategy
 * @author Orayn Hassidim
 */
public interface CollisionStrategyGenerator {
    /**
     * Generates a collision strategy.
     * 
     * @return a collision strategy
     */
    public CollisionStrategy generateStrategy();
}
