package game.actions.playerpurchases;

import edu.monash.fit2099.engine.actions.Action;

/**
 * An interface representing the ability of a trader to purchase an item.
 *
 * Implementing classes must provide a method to facilitate the purchase of an item by a trader.
 * This interface is designed to be used in the context of a game where actors (such as players) can interact with traders.
 * The interaction typically involves the purchase of items.
 *
 * Implementing classes should define the behavior for a trader to purchase an item, including checking the player's balance,
 * deducting the cost of the item, and adding the item to the trader's inventory.
 *
 * Created by:
 * @author Jun Hirano
 * Modified by:
 * @author Choong Lee Ann
 * @version 1.0
 */
public interface PlayerPurchase {

        /**
         * Represents the action of a trader purchasing an item from a player or another source.
         *
         * Implementing classes should define the logic for a trader to purchase an item.
         * This typically includes checking the player's balance, deducting the cost of the item, and adding the item to the trader's inventory.
         *
         * @return The action representing the purchase of an item.
         */
        Action traderPurchaseItem(int itemSetPrice);

}

