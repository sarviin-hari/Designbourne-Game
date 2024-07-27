package game.attributes;

/**
 * An enum representing various abilities that actors can possess in the game.
 * Example: If the player is capable of unlocking doors, you can attach Ability.UNLOCK_DOOR to the player class.
 * @author Sarviin Hari
 * Modified by:
 * @author Choong Lee Ann
 * @author Jun Hirano
 * @version 1.0
 */
public enum Ability {
    /**
     * Ability to unlock doors
     */
    UNLOCK_DOOR,
    /**
     * Ability to move between different maps
     */
    MOVE_MAP,
    /**
     * Ability to enter floor
     */
    ENTER_FLOOR,
    /**
     * Ability to interact with a locked gate
     */
    INTERACT_WITH_GATE,
    /**
     * Ability to drink water from puddle
     */
    DRINK_WATER_FROM_GROUND,
    /**
    * Ability to sell Item to other actor
    */
    SELLABLE,
    /**
     * Ability to walk on the void with no consequences
     */
    VOID_IMMUNE,

}

