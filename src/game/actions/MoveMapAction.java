package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An action that represents moving the actor to a specified location on the map.
 * This action can be used to move the actor to a specific location using a direction or command key.
 *
 * Authored by:
 * @author Sarviin Hari
 * @version 1.0
 */
public class MoveMapAction extends Action{

    /**
     * Target location
     */
    private Location moveToLocation;
    /**
     * One of the 8-d navigation
     */
    private String direction;
    /**
     * Or the command key
     */
    private String hotKey;


    /**
     * Constructor to create an Action that will move the Actor to a Location in a given Direction, using
     * a given menu hotkey.
     *
     * Note that this constructor does not check whether the supplied Location is actually in the given direction
     * from the Actor's current location.  This allows for (e.g.) teleporters, etc.
     *
     * @param moveToLocation the destination of the move
     * @param direction the direction of the move (to be displayed in menu)
     * @param hotKey the menu hotkey for this move
     */
    public MoveMapAction(Location moveToLocation, String direction, String hotKey) {
        this.moveToLocation = moveToLocation;
        this.direction = direction;
        this.hotKey = hotKey;
    }

    public MoveMapAction(Location moveToLocation, String mapToMove) {
        this.moveToLocation = moveToLocation;
        this.direction = mapToMove;
    }

    /**
     * Allow the Actor to be moved.
     *
     * Overrides Action.execute()
     *
     * @see Action#execute(Actor, GameMap)
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of the Action suitable for the menu
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        // Iterate through exits from the actor's current location
        for (Exit exit : moveToLocation.getExits()) {
            Location destination = exit.getDestination();

            // Check if the destination location can be entered by the actor
            if (destination.canActorEnter(actor)) {
                return (exit.getDestination().getMoveAction(actor, "around", exit.getHotKey())).execute(actor, map);
            }
        }

        return "All locations are occupied by an actor! Try again!";
    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player moves east"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " travels to " + direction;
    }

    /**
     * Returns this Action's hotkey.
     *
     * @return the hotkey
     */
    @Override
    public String hotkey() {
        return hotKey;
    }
}
