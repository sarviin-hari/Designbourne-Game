package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Ability;

/**
 * An action class representing the attempt to unlock a door in the game.
 *
 * Authored by:
 * @author Sarviin Hari
 *
 * Modified by:
 * @author Jun Hirano
 * @version 1.0
 */
public class UnlockDoorAction extends Action {

    /**
     * The location in the game map where the door (or gate) is present.
     * This variable is used to access the ground at this location and modify its capabilities.
     */
    private Location locationOfGate;

    /**
     * Constructor for the UnlockDoorAction class.
     *
     * @param initLocationOfGate The location of the door to be unlocked.
     */
    public UnlockDoorAction(Location initLocationOfGate) {
        this.locationOfGate = initLocationOfGate;
    }

    /**
     * Perform the unlocking action, adding the capability to move through the door if the actor has the unlock ability.
     *
     * @param actor The actor performing the action.
     * @param map   The game map where the action occurs.
     * @return A description of the outcome of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Check if the actor has the capability to unlock the door
        if (actor.hasCapability(Ability.UNLOCK_DOOR)) {
            // Add the capability to move through the door's ground
            locationOfGate.getGround().addCapability(Ability.MOVE_MAP);
            return "Gate is unlocked!";
        }

        return "Gate is locked shut!";
    }

    /**
     * Provide a menu description for the action.
     *
     * @param actor The actor performing the action.
     * @return A description to be displayed on the action menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " unlocks Gate";
    }
}
