package game.actions.attacks;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actors.abilities.SafeStepMovement;
import game.actors.abilities.SafeStepMovement;

import java.util.Random;

/**
 * Represents a special attack action in the game where an actor uses a weapon to "Stab"
 * a target and then uses the "Safe Step" ability to move away. This action consumes stamina
 * from the actor performing the action.
 *
 * The attack will only be executed if the actor has sufficient stamina. Otherwise, an error
 * message will be returned indicating low stamina.
 *
 * If the attack hits the target, the actor then makes use of the "Safe Step" ability to move.
 *
 * Authored by:
 * @author Sarviin Hari
 * @version 1.0
 */

public class StabAndStep extends Action {
    /**
     * The weapon used in the stab action.
     */
    WeaponItem weapon;
    /**
     * The target actor for the stab action.
     */
    Actor target;
    /**
     * A random generator to simulate randomness in the action's outcomes.
     */
    Random random = new Random();

    /**
     * Initializes a new StabAndStep action.
     *
     * @param weapon The weapon to be used for the stab action.
     * @param target The target actor for the stab action.
     */
    public StabAndStep(WeaponItem weapon, Actor target){
        this.weapon = weapon;
        this.target = target;
    }
    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String str = "";
        // Calculate the stamina decrease based on a percentage of maximum stamina
        int staminaDecrease = (int) (0.25f * actor.getAttributeMaximum(BaseActorAttributes.STAMINA));

        // only occurs if the STAMINA is within the range of decrease
        if (actor.getAttribute(BaseActorAttributes.STAMINA) >= staminaDecrease) {
            // Decrease actor's stamina
            actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaDecrease);

            str += (new AttackAction(target, map.locationOf(target).toString(), weapon)).execute(actor, map);
            str += (new SafeStepMovement(target)).getMovementAction(actor, map).execute(actor, map);
            return "Stab and Step skill activated\n" + str;
        }
        // error message
        return "Low stamina! Stab and Step action can't be activated";
    }

    /**
     * Describe what action will be performed if this Action is chosen in the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " activates the Stab and Step skill of " + weapon + " on " + target;

    }

}
