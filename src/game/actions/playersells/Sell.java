package game.actions.playersells;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A class representing an action where an actor sells an item to another entity.
 *
 * This action allows an actor to sell an item, with the option to simulate a scam in which the item may not be paid for.
 * The action can involve selling an item at a specified price or a scam where the item may not be paid for or paid less.
 * Extends the Action class.
 *
 * @author Jun Hirano
 * @version 1.0
 */
public class Sell extends Action {

    /** The item that the actor wants to sell. */
    private Item sellItem;
    /** The price at which the item is to be sold. */
    private int price;
    /** Flag indicating whether the selling process is a scam or not. */
    private boolean scamItem;
    /** Flag indicating whether the price is to be scammed or not. */
    private boolean scamPrice = false;

    /**
     * Constructs a Sell action for selling an item at a specified price.
     *
     * @param item The item to be sold.
     * @param price The selling price of the item.
     */
    public Sell(Item item, int price){
        this.sellItem = item;
        this.price = price;
        this.scamItem = false;
    }

    /**
     * Constructs a Sell action for selling an item, with the option to simulate a scam on the item or its price.
     *
     * @param item The item to be sold.
     * @param price The selling price of the item.
     * @param scamPrice A boolean flag indicating whether a scam on the price should be simulated.
     */
    public Sell(Item item, int price, boolean scamPrice){
        this.sellItem = item;
        this.price = price;
        this.scamItem = true;
        this.scamPrice = scamPrice;
    }

    /**
     * Perform the Sell action, including handling the sale and potential scams.
     *
     * @param actor The actor performing the action.
     * @param map The map where the action occurs.
     * @return A description of what happened as a result of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.removeItemFromInventory(this.sellItem);

        if (this.scamItem) {

            if (!this.scamPrice) {
                return "Player got scammed! Traveller takes the item without paying the player!";
            }
            else {
                int actorBalance = actor.getBalance();
                if (actorBalance <= this.price) {
                    this.setPrice(actorBalance);
                }
                actor.deductBalance(this.price);
                return "Player got scammed! Traveller takes the item and " + this.price + " Runes";
            }
        }
        else {
            actor.addBalance(this.price);
            return "Transaction success! " + this.price + " runes added to Player balance\n Current balance: " + actor.getBalance();
        }
    }

    /**
     * Set the selling price of the item.
     *
     * @param price The new selling price for the item.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Provide a menu description for the action.
     *
     * @param actor The actor performing the action.
     * @return A description to be displayed on the action menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor +" sell " + sellItem + " for " + price + " runes";
}
}