package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.conversations.ConversationAction;
import game.actions.conversations.MonologueAction;
import game.actions.playerpurchases.*;
import game.attributes.ProductsAccepted;
import game.attributes.Status;
import game.items.weapons.Broadsword;
import game.items.weapons.GreatKnife;
import game.items.consumables.HealingVial;
import game.items.consumables.RefreshingFlask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing an Isolated Traveller, which is a merchant actor in the game.
 * The Isolated Traveller can buy specific items from the player and sell various items to the player.
 * They have a collection of tradeable items and corresponding prices.
 * They also have specific capabilities to indicate the products they accept from the player.
 * They have a default hit point of 0 and can't engage in combat.
 *
 * Authored by:
 * @author Sarviin Hari
 * Modified by:
 * @author Jun Hirano
 * @author Choong Lee Ann
 * @version 1.0
 */
public class IsolatedTraveller extends Actor implements MonologueAction {

    /**
     * Constant for the standard price of Broadsword.
     */
    public static final int BROADSWORD_STANDARD_PRICE = 250;
    /**
     * Constant for the standard price of Great Knife.
     */
    public static final int GREAT_KNIFE_STANDARD_PRICE = 300;
    /**
     * Constant for the standard price of Healing Vial.
     */
    public static final int HEALING_VIAL_STANDARD_PRICE = 100;
    /**
     * Constant for the standard price of Refreshing Flask.
     */
    public static final int REFRESHING_FLASK_STANDARD_PRICE = 75;
    /**
     * Constant for the default hit points of Isolated Traveller.
     */
    public static final int DEFAULT_HIT_POINTS = 100;
    /**
     * Stores a list of items that Isolated Traveller can sell to Player.
     */
    private Map<PlayerPurchase, Integer> tradeableItems = new HashMap<>();
    /**
     * Stores a list of monologue options the Isolated Traveller can choose from
     */
    ArrayList<String> monologueList = new ArrayList<>();
    /**
     * Constructor for the Isolated Traveller class.
     */
    public IsolatedTraveller() {
        super("Isolated Traveller", 'ඞ', DEFAULT_HIT_POINTS);
        this.addCapability(Status.MERCHANT);

        // stores the item and its corresponding standard price
        this.tradeableItems.put(new Broadsword(), BROADSWORD_STANDARD_PRICE);
        this.tradeableItems.put(new GreatKnife(), GREAT_KNIFE_STANDARD_PRICE);
        this.tradeableItems.put(new HealingVial(), HEALING_VIAL_STANDARD_PRICE);
        this.tradeableItems.put(new RefreshingFlask(), REFRESHING_FLASK_STANDARD_PRICE);

        // Add item that IsolatedTraveller can buy from player
        this.addCapability(ProductsAccepted.Bloodberry);
        this.addCapability(ProductsAccepted.HealingVial);
        this.addCapability(ProductsAccepted.RefreshingFlask);
        this.addCapability(ProductsAccepted.Broadsword);
        this.addCapability(ProductsAccepted.GiantHammer);
        this.addCapability(ProductsAccepted.GreatKnife);
    }

    /**
     * Select and return an action to perform on the current turn.
     * In this case, it returns a DoNothingAction, indicating that the Isolated Traveller does not take any action on its turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Returns a new collection of the Actions that the otherActor can do to the current Actor.
     * It generates Purchase actions for each tradeable item in the Isolated Traveller's inventory.
     *
     * @param otherActor the Actor that might be performing an attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of Actions.
     */
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actionList = new ActionList();

        actionList.add(monologueList(otherActor));

        // for every tradeable item that can be purchased by the player,
        // get the Purchase Action for each item in inventory
        for (Map.Entry<PlayerPurchase, Integer> entry : tradeableItems.entrySet()) {
            PlayerPurchase tradeableItem = entry.getKey();
            int price = entry.getValue();
            actionList.add(tradeableItem.traderPurchaseItem(price));
        }

        return actionList;
    }

    /**
     * A method to provide a standard set of monologues for the Isolated Traveller.
     */
    @Override
    public void standardMonologue() {
        monologueList.add("Of course, I will never give you up, valuable customer!");
        monologueList.add("I promise I will never let you down with the quality of the items that I sell.");
        monologueList.add("You can always find me here. I'm never gonna run around and desert you, dear customer!");
        monologueList.add("I'm never gonna make you cry with unfair prices.");
        monologueList.add("Trust is essential in this business. I promise I’m never gonna say goodbye to a valuable customer like you.");
        monologueList.add("Don't worry, I’m never gonna tell a lie and hurt you.");
    }

    /**
     * Provides a list of monologues based on the state of the player and the Isolated Traveller.
     *
     * @param player The player actor.
     * @return A ConversationAction containing the monologues.
     */
    @Override
    public Action monologueList(Actor player) {
        monologueList.clear();

        String beforeBossDefeated = "You know the rules of this world, and so do I. Each area is ruled by a lord. Defeat the lord of this area, Abxervyer, and you may proceed to the next area.";
        String afterBossDefeated = "Congratulations on defeating the lord of this area. I noticed you still hold on to that hammer. Why don’t you sell it to me? We've known each other for so long. I can tell you probably don’t need that weapon any longer.";
        String withHammer = "Ooh, that’s a fascinating weapon you got there. I will pay a good price for it. You wouldn't get this price from any other guy.";

        standardMonologue();
        monologueList.add(beforeBossDefeated);

        // remove dialogue when Abxervyer has been defeated
        if (player.hasCapability(Status.BOSS_DEFEATED)){
            monologueList.remove(beforeBossDefeated);
        }

        // add dialogue if player is holding Giant Hammer
        if (player.hasCapability(Status.HOLDING_HAMMER)){
            monologueList.add(withHammer);
            // add dialogue if player defeated Abxervyer and holding Giant Hammer
            if (player.hasCapability(Status.BOSS_DEFEATED)){
                monologueList.add(afterBossDefeated);
            }
        }

        return new ConversationAction(this, monologueList);
    }
}
