package game.actions.playersells;

import edu.monash.fit2099.engine.actions.Action;

/**
 * An interface representing the ability of a player to sell an item.
 *
 * Implementing classes must provide a method to facilitate the selling of an item by a player.
 * This interface is designed to be used in the context of a game where players can sell items to various entities.
 * The interaction typically involves the player specifying a selling price for the item.
 *
 * @author Jun Hirano
 * @version 1.0
 */
public interface PlayerSell {
    /**
     * Represents the action of a player selling an item at a specified selling price.
     *
     * Implementing classes should define the logic for a player to sell an item, including specifying the selling price.
     *
     * @param SellingPrice The price at which the player intends to sell the item.
     * @return The action representing the sale of an item by the player.
     */
    Action playerSellItem(int SellingPrice);

}
