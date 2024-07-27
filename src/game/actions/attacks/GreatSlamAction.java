package game.actions.attacks;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

import java.util.Random;

/**
 * Represents a special attack action in the game where an actor uses a weapon to deliver
 * a "Great Slam" attack to a primary target and also potentially harms other actors
 * surrounding the primary target. This action consumes stamina from the actor performing
 * the action.
 *
 * The primary target receives full damage from the weapon while the surrounding actors
 * receive half of that damage.
 *
 * The action will not be executed if the actor does not have sufficient stamina.
 *
 * Authored by:
 * @author Jun Hirano
 * @version 1.0
 */
public class GreatSlamAction extends Action {
    /**
     * The weapon to be used in the action.
     */
    WeaponItem weapon;
    /**
     * The primary target actor for the action.
     */
    Actor target;
    /**
     * The direction of the attack.
     */
    private String direction;
    /**
     * A random generator to simulate the randomness in the action's outcomes.
     */
    Random random  = new Random();

    /**
     * Initializes a new GreatSlamAction.
     *
     * @param weapon    The weapon to be used in the action
     * @param target    The primary target actor for the action
     * @param direction The direction of the attack
     */
    public GreatSlamAction(WeaponItem weapon, Actor target, String direction){
        this.weapon = weapon;
        this.target = target;
        this.direction = direction;
    }

    /**
     * Executes the Great Slam attack. The actor performs the attack on the primary target
     * and potentially harms surrounding actors. Stamina is consumed from the actor.
     *
     * @param actor The actor performing the action
     * @param map   The map that the actor and target are on
     * @return A string description of the action's outcome
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        Location enemyLocation = map.locationOf(this.target);
        int staminaDecrease = (int) (0.05f * actor.getAttributeMaximum(BaseActorAttributes.STAMINA));

        //Check user has enough Stamina for activating Skill
        if (actor.getAttribute(BaseActorAttributes.STAMINA) >= staminaDecrease) {
            //Decrease user stamina
            actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaDecrease);
        }
        else{
            return "Low stamina! Great Slam action can't be activated";
        }

        // Determine whether the attack hits based on the weapon's hit chance
        if (!(random.nextInt(100) <= weapon.chanceToHit())) {
            return actor + " misses " + target + ".";
        }

        //Attack to the main Target
        int damage = this.weapon.damage();
        String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
        target.hurt(damage);

        // If the target is unconscious (defeated), describe the outcome
        if (!target.isConscious()) {
            result += "\n" + target.unconscious(actor, map);
        }

        //Attack to surrounding actor
        for (Exit exit : enemyLocation.getExits()) {
            Location destination = exit.getDestination();
            if(destination.containsAnActor()){
                Actor surroundingActor = destination.getActor();

                // Calculate damage and describe the attack result
                int damageToSurrounding = weapon.damage() / 2;
                result += "\n" + actor + " " + weapon.verb() + " " + surroundingActor + " for " + damageToSurrounding + " damage.";
                surroundingActor.hurt(damageToSurrounding);

                // If the target is unconscious (defeated), describe the outcome
                if (!surroundingActor.isConscious()) {
                    result += "\n" + surroundingActor.unconscious(actor, map);
                }
            }
        }
        return result;
    }

    /**
     * Describes the Great Slam action in a format suitable for a menu.
     *
     * @param actor The actor performing the action
     * @return A string description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " activates " + weapon + "'s Great Slam skill to " + target + " at " + direction;
    }
}
