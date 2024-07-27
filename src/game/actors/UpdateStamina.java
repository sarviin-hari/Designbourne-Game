package game.actors;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;

/**
 * Utility class that provides functionality for increasing and decreasing an {@link Actor}'s stamina.
 * The change in stamina is determined by a percentage of the actor's maximum stamina value.
 * This percentage can be passed as an argument to either increase or decrease the actor's current stamina.
 *
 * @author Raynen Jivash
 * @version 1.0
 */
public class UpdateStamina {

    /**
     * Increases the stamina of the specified actor by a certain percentage.
     *
     * @param actor The actor whose stamina will be increased.
     * @param percentage The percentage of the maximum stamina to be increased.
     * @return A string message indicating the amount of stamina increased.
     */
    public String increaseStamina(Actor actor, double percentage){
        String str = "";
        int staminaIncrease = (int) (percentage * actor.getAttributeMaximum(BaseActorAttributes.STAMINA));
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, staminaIncrease);
        str += "Stamina increased by " + percentage*100 + "%";

        return str;
    }

    /**
     * Decreases the stamina of the specified actor by a certain percentage.
     *
     * @param actor The actor whose stamina will be decreased.
     * @param percentage The percentage of the maximum stamina to be decreased.
     * @return A string message indicating the amount of stamina decreased.
     */
    public String decreaseStamina(Actor actor, double percentage){
        String str = "";
        int staminaDecrease = (int) (percentage * actor.getAttributeMaximum(BaseActorAttributes.STAMINA));
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaDecrease);
        str += "Stamina decreased by " + percentage*100 + "%";

        return str;
    }
}