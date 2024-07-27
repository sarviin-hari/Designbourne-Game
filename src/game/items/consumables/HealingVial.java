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
import game.attributes.ProductsAccepted;
import game.attributes.Status;
import game.attributes.UpgradeStatus;

import java.util.Random;

/**
 * A class representing a Healing Vial item that can be dropped by an actor.
 * This item can be used to perform healing actions on actors.
 *
 * @author Sarviin Hari
 * Modified by:
 * @author Sarviin Hari
 * @author Jun Hirano
 * @author Choong Lee Ann
 * @author Raynen Jivash
 * @version 1.0
 */
public class HealingVial extends Item implements PlayerSell, PlayerPurchase, Upgrade, ConsumeAbility {
    /**
     * The buying price of a Healing Vial when sold to a merchant.
     * Merchants will buy Healing Vials at this price.
     */
    public static final int MERCHANT_HEALINGVIAL_BUYING_PRICE = 35;

    /**
     * The amount of health points that will be regained by an actor when they use a Healing Vial.
     * Using a Healing Vial restores this fraction of health to the actor.
     */
    private static double healthRegain = 0.1;

    /**
     * A multiplier used to calculate the bonus price of a Healing Vial.
     * The bonus price is applied under specific conditions.
     */
    public static final int BONUS_MULTIPLIER = 2;

    /**
     * The default bound value used for random calculations.
     */
    public static final int DEFAULT_BOUND = 100;

    /**
     * The probability that a traveller will ask for a higher price when purchasing a Healing Vial.
     */
    public static final int TRAVELLER_ASK_PROBABILITY = 25;

    /**
     * The probability that a traveller will take the Healing Vial at a bonus price.
     */
    public static final int TRAVELLER_TAKE_PROBABILITY = 10;

    /**
     * A multiplier used to calculate the ask price for a Healing Vial when sold by a traveller.
     */
    public static final int ASK_PRICE_MULTIPLIER = 2;

    /**
     * A random number generator used for various calculations.
     */
    Random random = new Random();

    /**
     * The standard selling price of a Healing Vial when not sold at a bonus price.
     */
    private static final int SELL_HEALING_VIAL_STANDARD_PRICE = 35;

    /**
     * The bonus price to be applied to a Healing Vial under specific conditions.
     */
    private int bonusPrice;

    /**
     * Constructor to create a Healing Vial.
     * Initializes the name, display character, and portability of the item.
     */
    public HealingVial() {
        super("Healing Vial", 'a', true);
        this.addCapability(UpgradeStatus.UPGRADABLE);
    }

    /**
     * Returns a list of allowable actions that the Healing Vial can perform for the owner actor.
     * It includes the HealAction to perform healing on the actor.
     *
     * @param owner The actor who owns the Healing Vial.
     * @return A list of allowable actions for the owner actor.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionList = new ActionList();
//        actionList.add(new HealAction(this, HEALTH_REGAIN));
        actionList.add(new ConsumeAction(this));
        return actionList;
    }

    /**
     * Returns a list of allowable actions for the HealingVial when it's in a location.
     * It includes the SellAction against other actors (who have MERCHANT status) at the location.
     *
     * @param otherActor The actor to perform actions on.
     * @param location   The location where the HealingVial is.
     * @return A list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();

//        System.out.println("\nHealth Regain: " + healthRegain);


        // If the other actor is a Merchant, add the option to sell the HealingVial
        if (otherActor.hasCapability(Status.MERCHANT) && otherActor.hasCapability(ProductsAccepted.HealingVial)) {
            actions.add(playerSellItem(HealingVial.MERCHANT_HEALINGVIAL_BUYING_PRICE));
            return actions;
        }
        if (otherActor.hasCapability(Status.SMITH) && !this.hasCapability(UpgradeStatus.UPGRADE_COMPLETE)){
            actions.add(new UpgradeItem(this, Blacksmith.UPGRADE_COST_HEALING_VIAL));
            return actions;
        }
        return actions;
    }

    /**
     * Create a Sell action for selling the HealingVial to another actor.
     *
     * @param sellingPrice The price at which the HealingVial is sold.
     * @return A Sell action.
     */
    @Override
    public Action playerSellItem(int sellingPrice) {
        if (random.nextInt(DEFAULT_BOUND) < TRAVELLER_TAKE_PROBABILITY) {
            bonusPrice = sellingPrice * BONUS_MULTIPLIER;
            return new Sell(this, bonusPrice);
        } else {
            return new Sell(this, sellingPrice);
        }
    }

    /**
     * Create a Purchase action for buying the HealingVial from a trader.
     *
     * @param standardPrice The standard price at which the HealingVial is purchased.
     * @return A Purchase action.
     */
    @Override
    public Action traderPurchaseItem(int standardPrice) {
        // Using ternary operator to determine the askPrice
        int askPrice = (random.nextInt(DEFAULT_BOUND) <= TRAVELLER_ASK_PROBABILITY) ? (standardPrice * ASK_PRICE_MULTIPLIER) : standardPrice;
        return new Purchase(new HealingVial(), askPrice);
    }

    /**
     * Upgrades the Healing Vial by increasing the health regain percentage and marking it as upgraded.
     * The health regain percentage is set to 80% after upgrading, and the Healing Vial is marked as upgraded.
     */
    @Override
    public void upgradeItem() {
        healthRegain = 0.8;
        this.addCapability(UpgradeStatus.UPGRADE_COMPLETE);
    }

    /**
     * Represents the action of an actor consuming a Healing Vial.
     *
     * @param actor the actor that is consuming the item.
     * @param map   the GameMap on which the action is performed.
     * @return a message or description of the consumption action.
     */
    @Override
    public String consumeItem(Actor actor, GameMap map) {

        int healthIncrease = (int) (healthRegain * actor.getAttributeMaximum(BaseActorAttributes.HEALTH));
        if (actor.getAttribute(BaseActorAttributes.HEALTH)  < actor.getAttributeMaximum(BaseActorAttributes.HEALTH)){
            actor.heal(healthIncrease);
            actor.removeItemFromInventory(this);
            return "Actor's health increased by " + healthRegain * 100 + "%";
        }
        return "Actor's health is at maximum";
    }
}
