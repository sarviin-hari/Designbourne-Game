package game.actors.enemies;

/**
 * An interface representing the ability for an actor to modify and reset its damage values.
 *
 * This interface provides methods to both update the damage based on certain conditions
 * (such as environmental changes or power-ups) and to reset the damage value to its original state.
 *
 * Implementing classes can use this interface to adaptively change their damage output
 * in response to gameplay events.
 *
 * @author Raynen Jivash
 * @version 1.0
 */
public interface UpdateDamage {
    /**
     * Updates the actor's damage based on a certain condition or event.
     * Implementing classes should define the logic for how the damage gets updated.
     */
    void updateActorDamage();
    /**
     * Resets the actor's damage to its original value.
     * Implementing classes should define the logic for reverting the damage update.
     */
    void resetActorDamage();
}
