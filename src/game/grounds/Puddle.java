package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.consumeitems.ConsumeAbility;
import game.actions.consumeitems.ConsumeAction;
import game.actors.UpdateStamina;
import game.attributes.Ability;

/**
 * A class representing a puddle on a game map. Puddles allow Actors to drink water, replenishing stamina and health directly from the ground.
 *
 * Authored by:
 * @author Sarviin Hari
 * Modified by:
 * @author Raynen
 * @version 1.0
 */
public class Puddle extends Ground implements ConsumeAbility {

    /**
     * Percentage of stamina increase when drinking water from puddle.
     */
    private static final double STAMINA_INCREASE_PERCENTAGE = 0.01;

    /**
     * Amount of health increase when drinking water from puddle.
     */
    private static final int HEALTH_INCREASE = 1;

    /**
     * Constructor for the Puddle class.
     */
    public Puddle() {
        super('~');
    }

    /**
     * Returns a list of allowable actions that an actor can perform when interacting with the puddle.
     *
     * @param actor The actor interacting with the puddle.
     * @param location The location of the puddle on the game map.
     * @param direction The direction from which the actor is interacting with the puddle.
     * @return An ActionList containing allowable actions for the actor.
     */
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        // if an actor is on the ground and has the ability to drink water, add DrinkWaterAction to actionList
        if (location.getActor() == actor) {
            if (actor.hasCapability(Ability.DRINK_WATER_FROM_GROUND)) {
                actions.add(new ConsumeAction(this));
            }
        }

        return actions;
    }

    /**
     * Represents the action of an actor consuming the water from puddle.
     *
     * @param actor the actor that is consuming the water from puddle.
     * @param map   the GameMap on which the action is performed.
     * @return a message or description of the consumption action.
     */
    @Override
    public String consumeItem(Actor actor, GameMap map) {
        String str = actor.toString() + " ";

        str += (new UpdateStamina()).increaseStamina(actor, STAMINA_INCREASE_PERCENTAGE);

        actor.heal(HEALTH_INCREASE);
        str += ", Health increased by " + HEALTH_INCREASE + " point";

        return str;
    }

    /**
     * Returns a string representation of the puddle.
     *
     * @return A string representing the puddle.
     */
    @Override
    public String toString() {
        return "Puddle";
    }
}
