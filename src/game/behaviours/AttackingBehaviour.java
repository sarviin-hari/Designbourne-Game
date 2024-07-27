package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.attacks.AttackAction;
import game.attributes.Status;

/**
 * A behaviour representing the intention of an actor to attack a nearby player if one is found.
 * This behaviour checks adjacent locations for the presence of a player and returns an AttackAction if one is found.
 *
 * Authored by:
 * @author Sarviin Hari
 * @version 1.0
 */
public class AttackingBehaviour implements Behaviour, BehaviourOrder {

    /** Priority of the attack behaviour */
    private static final int ATTACK_BEHAVIOUR_PRIORITY = 1;

    /**
     * Determines the action to be performed by the actor based on their intention to attack a player.
     *
     * @param actor The actor performing the behaviour.
     * @param map   The game map containing the actor.
     * @return An AttackAction to attack a nearby player, or null if no player is found nearby.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Action action;

        // Loop through exits from the actor's current location
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();

            // Check if the destination location contains an actor and if it's a player
            if (destination.containsAnActor() && destination.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)) {
                // Create an AttackAction to attack the player found
                action = new AttackAction(destination.getActor(), destination.toString());
                return action;
            }
        }

        // No player found nearby, return null
        return null;
    }

    /**
     * Gets the priority of this behaviour in the behaviour order.
     *
     * @return The priority of this behaviour for ordering.
     */
    @Override
    public int behaviourOrderRank() {
        return AttackingBehaviour.ATTACK_BEHAVIOUR_PRIORITY;
    }
}
