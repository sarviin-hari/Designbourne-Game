package game.actions.attacks;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action class representing the activation of a focus skill using a specific weapon.
 *
 * Authored by:
 * @author Sarviin Hari
 * @version  1.0
 */
public class FocusAction extends Action {


    private WeaponFocus weapon;
    private int counter;
    private boolean isActive;
    /**
     * Constant for the probability of increase in stamina
     */
    public static final float STAMINA_INCREASE_PROBABILITY = 0.2f;

    /**
     * Constructor for the FocusAction class.
     *
     * @param initWeapon The weapon with the focus skill to be activated.
     */
    public FocusAction(WeaponFocus initWeapon) {
        this.weapon = initWeapon;
        this.counter = 0;
        this.isActive = false;
    }

    /**
     * Perform the focus action, increasing the weapon's attack and consuming stamina.
     *
     * @param actor The actor performing the action.
     * @param map   The game map where the action occurs.
     * @return A description of the outcome of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        // Calculate the stamina decrease based on a percentage of maximum stamina
        int staminaDecrease = (int) (FocusAction.STAMINA_INCREASE_PROBABILITY * actor.getAttributeMaximum(BaseActorAttributes.STAMINA));

        // only occurs if the STAMINA is within the range of decrease
        if (actor.getAttribute(BaseActorAttributes.STAMINA) >= staminaDecrease) {
            // Decrease actor's stamina
            actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaDecrease);

            // Reset the counter every time the item is called
            this.counter = 0;

            // Weapon's action when focus action is already active
            if (this.isActive) {
                weapon.activeWeaponChange();
                return "Focus skill was re-activated. Focus skill is now active for 5 more turns";
            }

            // Weapon's action when focus action is not active
            this.isActive = true;
            weapon.inactiveWeaponChange();
            return "Focus skill activated for 5 turns";
        }
        // error message
        return "Low stamina! Focus action can't be activated";
    }

    /**
     * Provide a menu description for the action.
     *
     * @param actor The actor performing the action.
     * @return A description to be displayed on the action menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " activates " + weapon + "'s Focus skill";
    }

    /**
     * Check if the maximum number of focus turns has been reached.
     *
     * @param maxTurns The maximum number of turns for the focus skill.
     * @return True if the maximum turns have been reached, false otherwise.
     */
    public boolean maxFocusTurn(int maxTurns) {
        // Check if the focus action is activated
        if (this.isActive) {
            // Increase the counter by 1
            this.counter += 1;

            // If the counter exceeds Maximum Turns, set the focus action to inactive
            if (this.counter > maxTurns) {
                this.isActive = false;
                return true;
            }
        }
        return false;
    }

    /**
     * Reset the counter and deactivate the focus action.
     */
    public void resetFocusState() {
        this.counter = 0;
        this.isActive = false;
    }

    /**
     * Gets the current active status of the object.
     *
     * @return {@code true} if the object is active with focus action, {@code false} otherwise.
     */
    public boolean getIsActive() {
        return this.isActive;
    }
}
