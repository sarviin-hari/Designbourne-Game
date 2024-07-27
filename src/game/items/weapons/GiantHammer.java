package game.items.weapons;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.attacks.AttackAction;
import game.actions.attacks.GreatSlamAction;
import game.actions.playersells.Sell;
import game.actions.playersells.PlayerSell;
import game.attributes.ProductsAccepted;
import game.attributes.Status;

/**
 * A class representing a Giant Hammer, which is a type of WeaponItem.
 * It has the capability to perform powerful attacks.
 *
 * @author Jun Hirano
 * Modified by:
 * @author Sarviin Hari
 * @version 1.0
 */
public class GiantHammer extends WeaponItem implements PlayerSell {
    /**
     * The buying price of a Giant Hammer when sold to a merchant.
     * Merchants will buy Giant Hammers at this price.
     */
    public static final int MERCHANT_GIANT_HAMMER_BUYING_PRICE = 250;

    /**
     * The initial hit rate value for a Giant Hammer.
     */
    public static final int INITIAL_HIT_RATE = 90;

    /**
     * The default damage value for a Giant Hammer.
     */
    public static final int DEFAULT_DAMAGE = 160;


    /**
     * Constructor to create a Giant Hammer.
     * Initializes the name, display character, default damage, and initial hit rate of the Giant Hammer.
     */
    public GiantHammer() {
        super("Giant Hammer", 'P', DEFAULT_DAMAGE, "hit", GiantHammer.INITIAL_HIT_RATE);
        this.addCapability(Status.HOLDING_HAMMER);
    }

    /**
     * Returns a list of allowable actions for the Giant Hammer when it's in a location.
     * It includes the SellAction against other actors(Who has MERCHANT status) at the location.
     * It includes the AttackAction against other actors at the location.
     *
     * @param otherActor The actor to perform actions on.
     * @param location   The location where the Giant Hammer is.
     * @return A list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();

        // If the other actor is a Merchant, add the option to sell the Giant Hammer
        if (otherActor.hasCapability(Status.MERCHANT) && otherActor.hasCapability(ProductsAccepted.GiantHammer)) {
            actions.add(playerSellItem(GiantHammer.MERCHANT_GIANT_HAMMER_BUYING_PRICE));
            return actions;
        }

        if (otherActor.hasCapability(Status.SMITH)){
            return actions;
        }

        // Add the options to attack or perform a Great Slam attack
        actions.add(new AttackAction(otherActor, location.toString(), this));
        actions.add(new GreatSlamAction(this, otherActor, location.toString()));
        return actions;
    }

    /**
     * Create a Sell action for selling the Giant Hammer to another actor.
     *
     * @param sellingPrice The price at which the Giant Hammer is sold.
     * @return A Sell action.
     */
    @Override
    public Action playerSellItem(int sellingPrice) {
        return new Sell(this, sellingPrice);
    }
}
