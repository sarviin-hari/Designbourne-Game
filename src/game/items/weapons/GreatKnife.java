package game.items.weapons;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.attacks.AttackAction;
import game.actions.attacks.StabAndStep;
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
 * A class representing a GreatKnife, which is a type of WeaponItem.
 * It is a versatile weapon that allows stabbing and stepping back from the target.
 * It can also be bought and sold by players and traders.
 *
 * Authored by:
 * @author Choong Lee Ann
 * Modified by:
 * @author Sarviin Hari
 * @author Jun Hirano
 * @author Raynen Jivash
 * @version 1.0
 */
public class GreatKnife extends WeaponItem implements PlayerSell, PlayerPurchase, Upgrade {
    /**
     * The buying price of the Great Knife for the merchant.
     */
    public static final int MERCHANT_GREAT_KNIFE_BUYING_PRICE = 175;

    /**
     * The default bound of probability.
     */
    public static final int DEFAULT_BOUND = 100;

    /**
     * The probability that a traveller will ask a question when interacting.
     */
    public static final int TRAVELLER_ASK_PROBABILITY = 5;

    /**
     * The probability that a traveller will take an action when interacting.
     */
    public static final int TRAVELLER_TAKE_PROBABILITY = 10;

    /**
     * The multiplier used to calculate the asking price.
     */
    public static final int ASK_PRICE_MULTIPLIER = 3;

    /**
     * The default damage value.
     */
    public static final int DEFAULT_DAMAGE = 75;

    /**
     * The default hit rate value.
     */
    public static final int DEFAULT_HIT_RATE = 70;

    /**
     * The initial hit rate for an actor.
     */
    private int initialHitRate;

    /**
     * Random number generator for various random operations.
     */
    private Random random = new Random();

    /**
     * Constructor to create a GreatKnife.
     * Initializes the name, display character, default damage, and initial hit rate of the GreatKnife.
     */
    public GreatKnife() {
        super("Great Knife", '>', DEFAULT_DAMAGE, "stab", DEFAULT_HIT_RATE);
        initialHitRate = DEFAULT_HIT_RATE;
        this.addCapability(UpgradeStatus.UPGRADABLE);
        this.addCapability(Status.HOLDING_KNIFE);
    }

    /**
     * List of allowable actions that the item allows its owner do to other actor.
     * It includes the option to sell the GreatKnife to other actors with MERCHANT status.
     * It also includes the option to attack other actors and perform a stab and step action.
     *
     * @param otherActor The actor to perform actions on.
     * @param location   The location where the GreatKnife is.
     * @return A list of allowable actions.
     */
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();

        //Upgrade Item if possible(when Item has capability UpgradeStatus.UPGRADED)
//        upgradeItem();

        // If the other actor is a Merchant, add the option to sell the GreatKnife
        if (otherActor.hasCapability(Status.MERCHANT) && otherActor.hasCapability(ProductsAccepted.GreatKnife)) {
            actions.add(playerSellItem(GreatKnife.MERCHANT_GREAT_KNIFE_BUYING_PRICE));
            return actions;
        }
        if (otherActor.hasCapability(Status.SMITH)){
            actions.add(new UpgradeItem(this, Blacksmith.UPGRADE_COST_GREAT_KNIFE));
            return actions;
        }

        // Add the options to attack or perform a stab and step action
        actions.add(new AttackAction(otherActor, location.toString(), this));
        actions.add(new StabAndStep(this, otherActor));
        return actions;

    }

    /**
     * Create a Sell action for selling the GreatKnife to another actor.
     *
     * @param sellingPrice The price at which the GreatKnife is sold.
     * @return A Sell action.
     */
    @Override
    public Action playerSellItem(int sellingPrice) {
        if (random.nextInt(DEFAULT_BOUND) < TRAVELLER_TAKE_PROBABILITY) {
            return new Sell(this, sellingPrice, true);
        } else {
            return new Sell(this, sellingPrice);
        }
    }

    /**
     * Create a Purchase action for buying the GreatKnife from a trader.
     *
     * @param standardPrice The standard price at which the GreatKnife is purchased.
     * @return A Purchase action.
     */
    @Override
    public Action traderPurchaseItem(int standardPrice) {
        //Using ternary operator to determine the askPrice
        int askPrice = (random.nextInt(DEFAULT_BOUND) <= TRAVELLER_ASK_PROBABILITY) ? (standardPrice * ASK_PRICE_MULTIPLIER) : standardPrice;
        return new Purchase(new GreatKnife(), askPrice);
    }


    /**
     * Upgrades the GreatKnife by increasing its hit rate.
     * This method is used to enhance the performance of the GreatKnife by incrementing its hit rate.
     * Once the upgrade is complete, the associated capabilities and statuses of the knife may also be modified.
     */
    @Override
    public void upgradeItem() {
        increaseHitRate(1);
    }
}
