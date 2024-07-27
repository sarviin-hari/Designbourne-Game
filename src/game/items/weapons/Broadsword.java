package game.items.weapons;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.attacks.AttackAction;
import game.actions.attacks.FocusAction;
import game.actions.attacks.WeaponFocus;
import game.actions.playerpurchases.Purchase;
import game.actions.playerpurchases.PlayerPurchase;
import game.actions.playersells.Sell;
import game.actions.playersells.PlayerSell;
import game.actions.upgrades.Upgrade;
import game.actions.upgrades.UpgradeItem;
import game.actors.Blacksmith;
import game.attributes.ProductsAccepted;
import game.attributes.Status;
import game.attributes.UpgradeStatus;

import java.util.Random;

/**
 * A class representing a Broadsword, which is a type of WeaponItem.
 * It can focus for a limited number of turns to increase its hit rate and damage multiplier.
 *
 * @author Sarviin Hari
 * Modified by:
 * @author Jun Hirano
 * @author Raynen Jivash
 * @version 1.0
 */
public class Broadsword extends WeaponItem implements WeaponFocus, PlayerSell, PlayerPurchase, Upgrade {
    /**
     * The buying price of a Broadsword when sold to a merchant.
     * Merchants will buy Broadswords at this price.
     */
    public static final int MERCHANT_BROADSWORD_BUYING_PRICE = 100;

    /**
     * The maximum number of turns for which the Broadsword's focus action can be active.
     */
    private static final int MAX_TURNS = 5;

    /**
     * The default damage value for a Broadsword.
     */
    public static final int DEFAULT_DAMAGE = 110;

    /**
     * The default bound value used for random calculations.
     */
    public static final int DEFAULT_BOUND = 100;

    /**
     * The probability that a traveller will ask for a lower price when purchasing a Broadsword.
     */
    public static final int TRAVELLER_ASK_PROBABILITY = 5;

    /**
     * The hit rate value when the Broadsword is actively focused.
     */
    public static final int FOCUS_HIT_RATE = 90;

    /**
     * The amount by which the damage multiplier increases when the Broadsword is actively focused.
     */
    public static final float DAMAGE_MULTIPLIER_INCREASE = 0.1f;

    /**
     * The action used to activate the Broadsword's focus ability.
     */
    private FocusAction focusAction;

    /**
     * The display object used for rendering messages.
     */
    private Display display = new Display();

    /**
     * The default hit rate value for a Broadsword.
     */
    public static final int DEFAULT_HIT_RATE = 80;

    /**
     * The default damage multiplier value for a Broadsword.
     */
    public static final float DEFAULT_DAMAGE_MULTIPLIER = 1.0f;

    /**
     * A random number generator used for various calculations.
     */
    private Random random = new Random();

    private int add_damage = 0;


    /**
     * Constructor to create a Broadsword.
     * Initializes the name, display character, default damage, and initial hit rate of the Broadsword.
     */
    public Broadsword() {
        super("Broadsword", '1', DEFAULT_DAMAGE, "hit", Broadsword.DEFAULT_HIT_RATE);
        focusAction = new FocusAction(this);
        this.addCapability(UpgradeStatus.UPGRADABLE);
    }

    /**
     * Resets the Broadsword to its initial state, including hit rate and damage multiplier.
     */
    public void resetBroadsword() {
        // Reset the Hit Rate to the initial value and reset the Damage Multiplier
        this.updateHitRate(DEFAULT_HIT_RATE);
        this.updateDamageMultiplier(DEFAULT_DAMAGE_MULTIPLIER);
    }

    /**
     * Inform a carried Broadsword of the passage of time.
     * This method is called once per turn if the Broadsword is being carried by an actor.
     *
     * @param currentLocation The location of the actor carrying this Broadsword.
     * @param actor           The actor carrying this Broadsword.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        // If the maximum number of turns for focus action is reached, reset the Broadsword
        if (focusAction.maxFocusTurn(Broadsword.MAX_TURNS)) {
            this.resetBroadsword();
            display.println("Focus skill has been reset. Broadsword default settings applied");
        }
    }

    /**
     * Inform an Item on the ground of the passage of time.
     * This method is called once per turn if the item rests on the ground.
     *
     * @param currentLocation The location of the ground on which the Broadsword lies.
     */
    public void tick(Location currentLocation) {
        // If the item is on the ground, and it has Focus Action skill, set its state to its initial state
        if (focusAction.getIsActive()) {
            this.resetBroadsword();
            focusAction.resetFocusState();
        }
    }

    /**
     * Returns a list of allowable actions for the Broadsword when it's in a location.
     * It includes the SellAction against other actors(Who has MERCHANT status) at the location.
     * It includes the AttackAction against other actors at the location.
     *
     * @param otherActor The actor to perform actions on.
     * @param location   The location where the Broadsword is.
     * @return A list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = new ActionList();

//        upgradeItem();

        // If the other actor is a Merchant, add the option to sell the Broadsword
        if (otherActor.hasCapability(Status.MERCHANT) && otherActor.hasCapability(ProductsAccepted.Broadsword)) {
            actions.add(playerSellItem(Broadsword.MERCHANT_BROADSWORD_BUYING_PRICE));
            return actions;
        }
        if (otherActor.hasCapability(Status.SMITH)){
            actions.add(new UpgradeItem(this, Blacksmith.UPGRADE_COST_BROADSWORD));
            return actions;
        }

        // Add the options to attack or perform a Focus action
        actions.add(new AttackAction(otherActor, location.toString(), this));
        return actions;

    }

    /**
     * Returns a list of allowable actions for the owner of the Broadsword.
     * It includes the FocusAction to activate the Broadsword's special focus ability.
     *
     * @param owner The actor who owns the Broadsword.
     * @return A list of allowable actions for the owner.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actions = new ActionList();
        actions.add(focusAction);
        return actions;
    }

    /**
     * Increase the damage multiplier by 10% when the weapon is actively focused.
     */
    @Override
    public void activeWeaponChange() {
        this.increaseDamageMultiplier(DAMAGE_MULTIPLIER_INCREASE);
    }

    /**
     * Increase the damage multiplier by 10% and set the hit rate to 90% when the weapon is not actively focused.
     */
    @Override
    public void inactiveWeaponChange() {
        this.increaseDamageMultiplier(DAMAGE_MULTIPLIER_INCREASE);
        this.updateHitRate(FOCUS_HIT_RATE);
    }

    /**
     * Create a Sell action for selling the Broadsword to another actor.
     *
     * @param sellingPrice The price at which the Broadsword is sold.
     * @return A Sell action.
     */
    @Override
    public Action playerSellItem(int sellingPrice) {
        return new Sell(this, sellingPrice);
    }

    /**
     * Create a Purchase action for buying the Broadsword from a trader.
     *
     * @param standardPrice The standard price at which the Broadsword is purchased.
     * @return A Purchase action.
     */
    @Override
    public Action traderPurchaseItem(int standardPrice) {
        return new Purchase(new Broadsword(), standardPrice, random.nextInt(DEFAULT_BOUND) <= TRAVELLER_ASK_PROBABILITY);
    }

    /**
     * Upgrades the Broadsword's damage by a set amount.
     * This method increments the additional damage of the Broadsword.
     */
    public void upgradeItem() {
        add_damage += 10;
    }

    /**
     * Calculates the total damage dealt by the Broadsword.
     * This method combines the base damage of the Broadsword with any additional damage from upgrades.
     *
     * @return The total damage value of the Broadsword.
     */
    @Override
    public int damage(){
        int baseDamage = super.damage();
//        System.out.println(baseDamage + add_damage);
        return baseDamage + add_damage;
    }



}
