package game.items.consumables;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.consumeitems.ConsumeAbility;
import game.actions.consumeitems.ConsumeAction;
import game.actions.playerpurchases.Purchase;
import game.actions.playerpurchases.PlayerPurchase;
import game.actions.playersells.Sell;
import game.actions.playersells.PlayerSell;
import game.actions.upgrades.UpgradeItem;
import game.actions.upgrades.Upgrade;
import game.actors.Blacksmith;
import game.actors.UpdateStamina;
import game.attributes.ProductsAccepted;
import game.attributes.Status;
import game.attributes.UpgradeStatus;

import java.util.Random;

/**
 * A class representing a refreshing flask item that can be dropped by an actor.
 * This item has the capability to refresh the actor.
 *
 * @author Sarviin Hari
 * Modified by:
 * @author Choong Lee Ann
 * @author Jun Hirano
 * @version 1.0
 */
public class RefreshingFlask extends Item implements PlayerSell, PlayerPurchase, Upgrade, ConsumeAbility {
    /**
     * The buying price of a Refreshing Flask when sold to a merchant.
     * Merchants will buy Refreshing Flasks at this price.
     */
    static final int MERCHANT_REFRESHING_FLASK_BUYING_PRICE = 25;

    /**
     * The amount of stamina points that will be regained by an actor when they use a Refreshing Flask.
     * Using a Refreshing Flask restores this fraction of stamina to the actor.
     */
    private static double staminaRegain = 0.2;

    /**
     * A multiplier used to calculate the bonus price of a Refreshing Flask.
     * The bonus price is applied under specific conditions.
     */
    public static final int BONUS_MULTIPLIER = 2;

    /**
     * The default bound value used for random calculations.
     */
    public static final int DEFAULT_BOUND = 100;

    /**
     * The probability that a traveller will ask for a lower price when purchasing a Refreshing Flask.
     */
    public static final int TRAVELLER_ASK_PROBABILITY = 10;

    /**
     * The probability that a traveller will take the Refreshing Flask at a bonus price.
     */
    public static final int TRAVELLER_TAKE_PROBABILITY = 50;

    /**
     * A multiplier used to calculate the ask price for a Refreshing Flask when sold by a traveller.
     */
    public static final double ASK_PRICE_MULTIPLIER = 0.8;

    /**
     * A random number generator used for various calculations.
     */
    Random random = new Random();
    private boolean upgraded = false;


    /**
     * Constructor to create a RefreshingFlask.
     * Initializes the name, display character, portability, and adds the capability to refresh the actor.
     */
    public RefreshingFlask() {
        super("Refreshing Flask", 'u', true);
        this.addCapability(UpgradeStatus.UPGRADABLE);
    }

    /**
     * Returns a list of allowable actions for the owner actor.
     * It includes the RefreshAction to perform a health regain action on the actor.
     *
     * @param owner The actor who owns the RefreshingFlask.
     * @return A list of allowable actions for the owner actor.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionList = new ActionList();
        actionList.add(new ConsumeAction(this));
        return actionList;
    }

    /**
     * Returns a list of allowable actions for the RefreshingFlask when it's in a location.
     * It includes the SellAction against other actors (who have MERCHANT status) at the location.
     *
     * @param otherActor The actor to perform actions on.
     * @param location   The location where the RefreshingFlask is.
     * @return A list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();
//        System.out.println("\nStamina Regain : " + staminaRegain);

        // If the other actor is a Merchant, add the option to sell the RefreshingFlask
        if (otherActor.hasCapability(Status.MERCHANT) && otherActor.hasCapability(ProductsAccepted.RefreshingFlask)) {
            actions.add(playerSellItem(RefreshingFlask.MERCHANT_REFRESHING_FLASK_BUYING_PRICE));
        }

        //If other actor is SMITH and also this item have upgraded yet, this Item doesn't have UPGRADED capability.
        if (otherActor.hasCapability(Status.SMITH) && !this.hasCapability(UpgradeStatus.UPGRADE_COMPLETE)){
            actions.add(new UpgradeItem(this, Blacksmith.UPGRADE_COST_REFRESHING_FLASK));
            return actions;
        }

        return actions;
    }

    /**
     * Create a Sell action for selling the RefreshingFlask to another actor.
     *
     * @param sellingPrice The price at which the RefreshingFlask is sold.
     * @return A Sell action.
     */
    @Override
    public Action playerSellItem(int sellingPrice) {
        if (random.nextInt(DEFAULT_BOUND) < TRAVELLER_TAKE_PROBABILITY) {
            return new Sell(this, sellingPrice);
        } else {
            // if it's a scam
            return new Sell(this, sellingPrice, false);
        }
    }

    /**
     * Create a Purchase action for the trader to purchase the RefreshingFlask.
     *
     * @param standardPrice The standard price at which the trader purchases the item.
     * @return A Purchase action.
     */
    @Override
    public Action traderPurchaseItem(int standardPrice) {
        //Using ternary operator to determine askPrice
        int askPrice = (random.nextInt(DEFAULT_BOUND) <= TRAVELLER_ASK_PROBABILITY) ? ((int) (standardPrice * ASK_PRICE_MULTIPLIER)) : standardPrice;
        return new Purchase(new RefreshingFlask(), askPrice);
    }

    /**
     * Upgrades the item by increasing the stamina regain attribute and marking it as upgraded.
     * After the upgrade, the stamina regain is set to 1, and the item is marked as upgraded with the UPGRADED capability.
     */
    @Override
    public void upgradeItem() {
        // Once this item is upgraded, the UPGRADED capability is assigned to the item. Now we can update the attribute.
        staminaRegain = 1;
        this.addCapability(UpgradeStatus.UPGRADE_COMPLETE);
    }


    /**
     * Represents the action of an actor consuming a Refreshing Flask.
     *
     * @param actor the actor that is consuming the Refreshing Flask.
     * @param map   the GameMap on which the action is performed.
     * @return a message or description of the consumption action.
     */
    @Override
    public String consumeItem(Actor actor, GameMap map) {
        if (actor.getAttribute(BaseActorAttributes.STAMINA) < actor.getAttributeMaximum(BaseActorAttributes.STAMINA)) {
            actor.removeItemFromInventory(this);
            return (new UpdateStamina()).increaseStamina(actor, staminaRegain);
        }
        return "Actor stamina is at maximum";    }
}
