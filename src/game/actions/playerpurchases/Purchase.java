package game.actions.playerpurchases;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A class representing an action where an actor purchases an item for a specified price.
 *
 * This action allows an actor to purchase an item, deducting the specified price from their balance.
 * Optionally, the action can involve a scam where the actor gets scammed and loses their money.
 * Extends the Action class.
 *
 * @author Sarviin Hari
 * @version 1.0
 */
public class Purchase extends Action {

    /** The price of the item to be purchased. */
    private int price;
    /** The item that the actor wants to purchase. */
    private Item item;
    /** Flag indicating whether the purchase action is a scam or not. */
    private boolean scam;

    /**
     * Constructs a Purchase action with the specified item, price, and scam flag.
     *
     * @param item The item to be purchased.
     * @param price The price of the item.
     * @param scam A boolean flag indicating whether the purchase is a scam.
     */
    public Purchase(Item item, int price,  boolean scam){
        this.price = price;
        this.item = item;
        this.scam = scam;
    }

    /**
     * Constructs a Purchase action with the specified item and price.
     * Defaults the scam flag to false.
     *
     * @param item The item to be purchased.
     * @param price The price of the item.
     */
    public Purchase(Item item, int price) {
        this(item, price, false);  // Defaults scam to false
    }

    /**
     * Perform the Purchase action.
     *
     * @param actor The actor performing the action.
     * @param map   The map where the action occurs.
     * @return A description of what happened as a result of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int actorBalance = actor.getBalance();
        if (actorBalance < price){
            return "Transaction failed! Insufficient Balance";
        }
        if (scam) {
            actor.deductBalance(price);
            return "Player got scammed! " + price + " runes deducted from Player balance";
        }
        actor.addItemToInventory(this.item);
        actor.deductBalance(price);
        return "Transaction success! " + price + " runes deducted from Player balance";
    }

    /**
     * Provide a menu description for the action.
     *
     * @param actor The actor performing the action.
     * @return A description to be displayed on the action menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " purchase " + item + " for " + price + " runes" ;
    }
}

