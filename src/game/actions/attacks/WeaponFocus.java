package game.actions.attacks;

/**
 * An interface representing the focus behavior of a weapon.
 * Weapons implementing this interface can have their attack properties modified when the focus skill is activated.
 *
 * Authored by:
 * @author Sarviin Hari
 * @version 1.0
 */
public interface WeaponFocus {
    /**
     * Perform changes to the weapon when the focus skill is active.
     * This can include increasing attack damage or hit rate.
     */
    void activeWeaponChange();

    /**
     * Perform changes to the weapon when the focus skill is inactive.
     * This can include resetting attack properties to their default values.
     */
    void inactiveWeaponChange();
}

