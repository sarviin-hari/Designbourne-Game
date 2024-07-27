package game.actions.upgrades;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Represents an action to upgrade an item in the game.
 *
 * Authored by:
 * @author Jun Hirano
 *
 * Modified by:
 * @author Sarviin Hari
 * @version 1.0
 */
public class UpgradeItem extends Action {
    /**
     * The item that will be upgraded.
     */
    private Upgrade upgradeItem;
    /**
     * The cost required to upgrade the item.
     */
    private int price;

    /**
     * Constructor to initialize the upgrade item and its price.
     *
     * @param item The item to be upgraded.
     * @param price The cost to upgrade the item.
     */
    public UpgradeItem(Upgrade item, int price){
        this.upgradeItem = item;
        this.price = price;
    }

    /**
     * Executes the action to upgrade the item if the actor has enough balance.
     *
     * @param actor The actor performing the action.
     * @param map The current game map.
     * @return A string indicating the result of the upgrade action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int actorBalance = actor.getBalance();
        if (actorBalance < this.price){
            return "Transaction failed! Insufficient Balance";
        }
        else {
            actor.deductBalance(this.price);
        }

        upgradeItem.upgradeItem();

        //Add upgradeStatus.UPGRADED to Item. This Capability allow item upgrade the attribute of each Item class.
//        this.upgradeItem.addCapability(UpgradeStatus.UPGRADED);
        return actor + " upgraded " + this.upgradeItem + " !: " + this.price + " runes deducted from Player balance";
    }

    /**
     * Describes this upgrade action in the menu.
     *
     * @param actor The actor performing the action.
     * @return A string representation of the upgrade action for the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " upgrade " + this.upgradeItem;
    }
}
