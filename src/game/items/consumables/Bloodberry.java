package game.items.consumables;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.consumeitems.ConsumeAbility;
import game.actions.consumeitems.ConsumeAction;
import game.actions.playersells.Sell;
import game.actions.playersells.PlayerSell;
import game.attributes.ProductsAccepted;
import game.attributes.Status;

/**
 * A class representing a Bloodberry item that can be consumed by an actor.
 * This item can be eaten to restore the actor's health.
 *
 * @author Jun Hirano
 * Modified by:
 * @author Sarviin Hari
 * @author Jun Hirano
 * @version 1.0
 */
public class Bloodberry extends Item implements PlayerSell, ConsumeAbility {
    // Constants for buying price and health restoration
    /**
     * The buying price of a Bloodberry when sold to a merchant.
     */
    public static final int MERCHANT_BLOODBERRY_BUYING_PRICE = 10;

    /**
     * The amount of health points that will be added to an actor's health when they consume a Bloodberry.
     * Consuming a Bloodberry restores this amount of health to the actor.
     */
    public static final int HEALTH_ADD = 5;


    /**
     * Constructor to create a Bloodberry.
     * Initializes the name, display character, and portability of the item.
     */
    public Bloodberry() {
        super("Bloodberry", '*', true);
    }

    /**
     * Returns a list of allowable actions that the Bloodberry can perform for the owner actor.
     * It includes the EatBerryAction to consume the Bloodberry and restore health.
     *
     * @param owner The actor who owns the Bloodberry.
     * @return A list of allowable actions for the owner actor.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();

        // Allow the owner actor to eat the Bloodberry and restore health
//        actions.add(new EatBerryAction(this, HEALTH_ADD));
        actions.add(new ConsumeAction(this));

        return actions;
    }

    /**
     * Returns a list of allowable actions for the Bloodberry when it's in a location.
     * It includes the SellAction against other actors (who have MERCHANT status) at the location.
     *
     * @param otherActor The actor to perform actions on.
     * @param location   The location where the Bloodberry is.
     * @return A list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();

        // If the other actor is a Merchant, add the option to sell the Bloodberry
        if (otherActor.hasCapability(Status.MERCHANT) && otherActor.hasCapability(ProductsAccepted.Bloodberry)) {
            actions.add(playerSellItem(Bloodberry.MERCHANT_BLOODBERRY_BUYING_PRICE));
        }

        return actions;
    }

    /**
     * Create a Sell action for selling the Bloodberry to another actor.
     *
     * @param sellingPrice The price at which the Bloodberry is sold.
     * @return A Sell action.
     */
    @Override
    public Action playerSellItem(int sellingPrice) {
        return new Sell(this, sellingPrice);
    }

    /**
     * Represents the action of an actor consuming Bloodberry.
     *
     * @param actor the actor that is consuming the Bloodberry.
     * @param map   the GameMap on which the action is performed.
     * @return a message or description of the consumption action.
     */
    @Override
    public String consumeItem(Actor actor, GameMap map) {
        actor.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, HEALTH_ADD);
        actor.removeItemFromInventory(this);
        return "Player eats berry. Maximum player health increased by a total of 5 points";    }
}
