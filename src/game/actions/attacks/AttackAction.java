package game.actions.attacks;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;

import java.util.Random;

/**
 * An action class representing an attack action by one actor on another.
 *
 * Authored by
 * @author Adrian Kristiano
 * @version  1.0
 */
public class AttackAction extends Action {

    /**
     * The Actor that is the target of the attack.
     */
    private Actor target;

    /**
     * The direction from which the attack is coming (used for display purposes).
     */
    private String direction;

    /**
     * Random number generator used for determining hit success.
     */
    private Random rand = new Random();

    /**
     * The weapon used for the attack.
     */
    private Weapon weapon;

    /**
     * Constructor for the AttackAction class.
     *
     * @param target     The Actor to be attacked.
     * @param direction  The direction from which the attack is initiated (used for display).
     * @param weapon     The weapon used for the attack.
     */
    public AttackAction(Actor target, String direction, Weapon weapon) {
        this.target = target;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Constructor for the AttackAction class when no specific weapon is provided.
     *
     * @param target     The Actor to be attacked.
     * @param direction  The direction from which the attack is initiated (used for display).
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    /**
     * Perform the attack action.
     *
     * @param actor The actor performing the attack.
     * @param map   The game map where the attack occurs.
     * @return A description of the outcome of the attack action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // If no specific weapon is provided, use the actor's intrinsic weapon
        if (weapon == null) {
            weapon = actor.getIntrinsicWeapon();
        }

        // Determine whether the attack hits based on the weapon's hit chance
        if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
            return actor + " misses " + target + ".";
        }

        // Calculate damage and describe the attack result
        int damage = weapon.damage();
        String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
        target.hurt(damage);

        // If the target is unconscious (defeated), describe the outcome
        if (!target.isConscious()) {
            result += "\n" + target.unconscious(actor, map);
        }

        return result;
    }

    /**
     * Provide a menu description for the action.
     *
     * @param actor The actor performing the attack.
     * @return A description to be displayed on the action menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction + " with " + (weapon != null ? weapon : "Intrinsic Weapon");
    }
}
