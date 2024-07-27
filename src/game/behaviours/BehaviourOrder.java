package game.behaviours;

/**
 * The `BehaviourOrder` interface defines a contract for implementing classes to specify the behavior order rank.
 * Behavior order rank indicates the priority or order of execution for behaviors of enemies.
 *
 * Authored by:
 * @author Sarviin Hari
 * @version 1.0
 */
public interface BehaviourOrder {

    /**
     * Gets the behavior order rank for this specific behavior.
     *
     * @return An integer representing the order or priority of this behavior.
     *         Lower values indicate higher priority or precedence.
     */
    int behaviourOrderRank();
}
