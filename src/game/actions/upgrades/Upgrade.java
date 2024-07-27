package game.actions.upgrades;

/**
 * Represents an upgradable item in the game.
 *
 * Any class that implements this interface should provide
 * the logic for how the item is upgraded.
 * Authored by:
 * @author Jun Hirano
 */
public interface Upgrade {

    /**
     * Upgrades the item. The specific logic for the upgrade should be
     * implemented by the classes that implement this interface.
     */
    void upgradeItem();
}
