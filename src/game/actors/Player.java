package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.FancyMessage;
import game.MapReset;
import game.attributes.Ability;
import game.attributes.Status;
import game.items.OldKey;
import game.items.consumables.Bloodberry;
import game.items.consumables.HealingVial;
import game.items.consumables.RefreshingFlask;
import game.items.consumables.Runes;
import game.items.weapons.Broadsword;
import game.items.weapons.GiantHammer;
import game.items.weapons.GreatKnife;

/**
 * Class representing the player character in the game.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 * @author Sarviin Hari
 * @author Jun Hirano
 * @author Choong Lee Ann
 * @author Raynen Jivash
 * @version 1.0
 */
public class Player extends Actor {
    /**
     * The action used to move the player to another game map.
     */
    private MoveActorAction moveActorAction;

    /**
     * The instance of MapReset used to reset maps.
     */
    private MapReset mapReset;

    /**
     * The rate at which the player's stamina increases each turn (1% increase).
     */
    private static final double STAMINA_INCREASE = 0.01;

    /**
     * Constructor for the Player class.
     *
     * @param name        The name to call the player in the UI.
     * @param displayChar The character to represent the player in the UI.
     * @param hitPoints   The player's starting number of hit points.
     * @param initStamina The player's initial stamina attribute.
     * @param action      The MoveActorAction for the player.
     * @param mapReset    The MapReset instance for resetting maps.
     */
    public Player(String name, char displayChar, int hitPoints, int initStamina, MoveActorAction action, MapReset mapReset) {
        super(name, displayChar, hitPoints);
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(initStamina));
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Ability.ENTER_FLOOR); // Add enter floor capability
        this.addCapability(Ability.INTERACT_WITH_GATE);
        this.addCapability(Ability.DRINK_WATER_FROM_GROUND);

//        this.addItemToInventory(new HealingVial());
//        this.addItemToInventory(new RefreshingFlask());
//        this.addItemToInventory(new Bloodberry());
//        this.addItemToInventory(new Runes(99999999));
//        this.addItemToInventory(new Broadsword());
//        this.addItemToInventory(new GreatKnife());

        this.moveActorAction = action;
        this.mapReset = mapReset;
//        this.addItemToInventory(new OldKey());
//        this.addBalance(100000);
//        this.addItemToInventory(new GiantHammer());
    }

    /**
     * Get the player's intrinsic weapon.
     *
     * @return The player's intrinsic weapon.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(15, "limbs", 80);
    }

    /**
     * Determine the player's actions for the current turn.
     *
     * @param actions    Collection of possible Actions for the player.
     * @param lastAction The Action the player took last turn.
     * @param map        The game map containing the player.
     * @param display    The display used to show messages and menus.
     * @return The Action to be performed.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // increase player stamina by 1% each turn
        new UpdateStamina().increaseStamina(this, STAMINA_INCREASE);

        // display players current attributes
        display.println(this.name + "\nHealth: " + this.getAttribute(BaseActorAttributes.HEALTH) + " / " + this.getAttributeMaximum(BaseActorAttributes.HEALTH) +
                "\nStamina: " + this.getAttribute(BaseActorAttributes.STAMINA) + " / " + this.getAttributeMaximum(BaseActorAttributes.STAMINA) +
                "\nTotal Currency: " + this.getBalance());

        // Return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    /**
     * Get a string representation of the player.
     *
     * @return A string representing the player including health and stamina.
     */
    @Override
    public String toString() {
        return super.toString() + " (" +
                this.getAttribute(BaseActorAttributes.STAMINA) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.STAMINA) +
                ")";
    }

    /**
     * Method to execute when the player becomes unconscious due to the action of another actor.
     *
     * @param actor The perpetrator of the action.
     * @param map   The game map where the player fell unconscious.
     * @return A string describing what happened when the player is unconscious.
     */
    public String unconscious(Actor actor, GameMap map) {
        this.reset();   // reset state of Player
        this.mapReset.resetAllMaps();   // reset all Maps
        this.dropRunes(map);    // drop Runes
        this.moveMap(map);    // move to initial position
        return super.toString() + "\n" + FancyMessage.YOU_DIED;

    }


    /**
     * Method to execute when the player becomes unconscious due to natural causes or accidents.
     *
     * @param map The game map where the player fell unconscious.
     * @return A string describing what happened when the player is unconscious.
     */
    public String unconscious(GameMap map) {
        this.reset();   // reset state of Player
        this.mapReset.resetAllMaps();   // reset all Maps
        this.dropRunes(map);    // drop Runes
        this.moveMap(map);    // move to initial position
        return super.toString() + "\n" + FancyMessage.YOU_DIED;


    }

    /**
     * Move the player to the specified game map using the provided MoveActorAction.
     *
     * @param map The destination GameMap to move the player to.
     * @return A message or description of the movement action.
     */
    public String moveMap(GameMap map) {
        return this.moveActorAction.execute(this, map);
    }

    /**
     * Reset the player's attributes, including stamina and health, to their maximum values.
     */
    public void reset() {
        this.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, this.getAttributeMaximum(BaseActorAttributes.STAMINA));
        this.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, this.getAttributeMaximum(BaseActorAttributes.HEALTH));
    }

    /**
     * Drop Runes on the specified game map based on the player's balance.
     *
     * @param map The GameMap where the Runes will be dropped.
     */
    public void dropRunes(GameMap map) {
        Runes runes = new Runes(this.getBalance());
        this.deductBalance(this.getBalance());
        Location location = map.locationOf(this);
        location.addItem(runes);
    }
}
