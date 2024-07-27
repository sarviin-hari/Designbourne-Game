package game.grounds;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.LocationMap;
import game.actions.MoveMapAction;
import game.actions.UnlockDoorAction;
import game.attributes.Ability;
import java.util.ArrayList;

//I acknowledge the use of ChatGPT in forming code for this class
/**
 * A class representing a locked gate on a game map.
 * Locked gates can be unlocked by the player, allowing them to move to the next map.
 *
 * Authored by:
 * @author Sarviin Hari
 * Modified by:
 * @author Raynen Jivash
 * @version 1.0
 */
public class LockedGate extends Ground {
    /**
     * The action associated with the locked gate.
     */
    private Action moveAction;

    /**
     * An ArrayList of LocationMap objects associated with this gate.
     */
    private ArrayList<LocationMap> locationMaps;

    /**
     * Constructor for the LockedGate class.
     *
     * @param newAction The action associated with the locked gate, which can be performed after unlocking.
     */
    public LockedGate(Action newAction) {
        super('=');
        moveAction = newAction;
    }

    /**
     * Constructs a LockedGate with the specified location map(s).
     *
     * @param locationMaps An ArrayList of LocationMap objects or a single LocationMap object
     *                     to associate with the LockedGate. If multiple LocationMap objects
     *                     are provided, they will all be associated with this gate.
     */
    public LockedGate(ArrayList<LocationMap> locationMaps) {
        super('=');
        this.locationMaps = locationMaps;
    }

    /**
     * Constructs a LockedGate with the specified location map.
     *
     * @param locationMaps The LocationMap object to associate with the LockedGate.
     */
    public LockedGate(LocationMap locationMaps) {
        super('=');
        this.locationMaps = new ArrayList<>();
        this.locationMaps.add(locationMaps);
    }


    /**
     * Constructor for the LockedGate class without specifying an associated action.
     */
    public LockedGate() {
        super('=');
    }

    /**
     * Returns a list of allowable actions that an actor can perform when interacting with the locked gate.
     *
     * @param actor     The actor interacting with the locked gate.
     * @param location  The location of the locked gate on the game map.
     * @param direction The direction from which the actor is interacting with the locked gate.
     * @return An ActionList containing allowable actions for the actor.
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actionList = new ActionList();

        if (actor.hasCapability(Ability.INTERACT_WITH_GATE)) {
            if (!this.hasCapability(Ability.MOVE_MAP)) {
                // Add an UnlockDoorAction if the gate is locked
                actionList.add(new UnlockDoorAction(location));
            } else {
                // Add the associated action if the gate is unlocked
                actionList.add(moveAction);
                for (LocationMap locationMap : locationMaps) {
                    Location locationMapLoc = locationMap.getLocation();
                    String mapToMove = locationMap.getMapToMove();
                    actionList.add(new MoveMapAction(locationMapLoc, mapToMove));
                }
            }
        }

        return actionList;
    }

    /**
     * Override this method to specify whether an actor can enter the locked gate.
     *
     * @param actor The actor trying to enter the gate.
     * @return True if the actor can enter, false otherwise.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        // Check if the gate has the MOVE_MAP capability, indicating it can be entered
        return this.hasCapability(Ability.MOVE_MAP);
    }
}
