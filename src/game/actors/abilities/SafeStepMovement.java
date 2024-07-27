
package game.actors.abilities;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A behaviour representing the intention of an actor to step to an empty exit if one is found.
 * @author Sarviin Hari
 * @version 1.0
 */
public class SafeStepMovement {
    /**
     * A random generator to decide on random movement options.
     */
    Random random = new Random();
    /**
     * The display interface to show debug or other messages.
     */
    private Display display = new Display();
    /**
     * The target actor that the primary actor wants to step away from.
     */
    Actor target;

    /**
     * Constructor to initialize the behavior with a specific target.
     *
     * @param target The actor that the primary actor wants to step away from.
     */
    public SafeStepMovement(Actor target){
        this.target = target;

    }

    /**
     * Determines the available exits and which exit the player will go to
     *
     * @param actor The actor performing the behaviour.
     * @param map   The game map containing the actor.
     * @return A MoveAction to move to the available exit, or null if no exit is available.
     */
    public Action getMovementAction(Actor actor, GameMap map) {
        ArrayList<Action> actions = new ArrayList<>();

        if (!(target.isConscious())){
            for (Exit exit : map.locationOf(actor).getExits()) {
                Location destination = exit.getDestination();

                // Check if the destination location can be entered by the actor
                if (destination.canActorEnter(actor)) {
                    actions.add(destination.getMoveAction(actor, "around", exit.getHotKey()));
                }
            }
//            display.println("In random "  + actions.size());

        }
        else {

            // Iterate through exits from the actor's current location
            List<Location> locations = new ArrayList<>();

            for (Exit exit : map.locationOf(target).getExits()) {
                locations.add(exit.getDestination());
            }

//            display.println(locations.toString());


            for (Exit exit : map.locationOf(actor).getExits()) {
                Location destination = exit.getDestination();
                display.println(destination + " " + (locations.contains(exit.getDestination()) + " " + exit));

                // Check if the destination location can be entered by the actor and if the location is part of the location of the enemy
                if (destination.canActorEnter(actor) && !(locations.contains(destination))) {
                    actions.add(destination.getMoveAction(actor, "around", exit.getHotKey()));
                }
            }
//            display.println("In spec exits " + actions.size()) ;
        }

        // Check if any valid movement actions are available and select a random movement from the available options
        if (!actions.isEmpty()) {
            return actions.get(random.nextInt(actions.size()));
        } else {
            // No valid movement action available, return null
            return null;
        }
    }

}

