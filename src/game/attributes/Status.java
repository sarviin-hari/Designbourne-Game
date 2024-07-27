package game.attributes;

/**
 * An enum class representing various statuses that an actor can be in during the game.
 * Example: If the player is sleeping, you can attach Status.SLEEP to the player class.
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Sarviin Hari
 * @author Jun Hirano
 * @version 1.0
 */
public enum Status {
    /**
     * Actor is hostile toward enemies
     */
    HOSTILE_TO_ENEMY,
    /**
     * An actor cannot be spawned at the location
     */
    UNSPAWNABLE,
    /**
     * An actor that can purchase items
     */
    MERCHANT,
    /**
     * An actor that is an Enemy
     */
    ENEMY,

    /**
     * An actor that is affected by the changes in Weather
     */
    HOSTILE_TO_WEATHER,

    /**
     * An actor is spawned
     */
    SPAWNED,

    /**
     * A boss enemy
     */
    BOSS_ENEMY,

    /**
     * Marked for destruction if on the ground
     */
    DESTROY_IF_ON_GROUND,
    /**
     * A boss enemy has been defeated
     */
    BOSS_DEFEATED,
    /**
     * Actor is holding a knife
     */
    HOLDING_KNIFE,
    /**
     * Actor is holding a hammer
     */
    HOLDING_HAMMER,
    /**
     * An actor that is a blacksmith
     */
    SMITH
}


