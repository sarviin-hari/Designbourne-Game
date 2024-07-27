package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.conversations.ConversationAction;
import game.actions.conversations.MonologueAction;
import game.attributes.Status;

import java.util.ArrayList;

/**
 * Represents a Blacksmith character in the game.
 * The Blacksmith provides various functionalities like upgrading the player's items.
 *
 * @author Jun Hirano
 * Modified by:
 * @author Choong Lee Ann
 */
public class Blacksmith extends Actor implements MonologueAction {
    /**
     * Default hit points for the Blacksmith character.
     */
    public static final int DEFAULT_HIT_POINTS = 100;
    /**
     * Cost of upgrading the Healing Vial.
     */
    public static final int UPGRADE_COST_HEALING_VIAL = 250;
    /**
     * Cost of upgrading the Refreshing Flask.
     */
    public static final int UPGRADE_COST_REFRESHING_FLASK = 175;
    /**
     * Cost of upgrading the Broadsword.
     */
    public static final int UPGRADE_COST_BROADSWORD = 1000;
    /**
     * Cost of upgrading the Great Knife.
     */
    public static final int UPGRADE_COST_GREAT_KNIFE = 2000;

    /**
     * Stores a list of monologue options the Blacksmith can choose from
     */
    ArrayList<String> monologueList = new ArrayList<>();

    /**
     * Constructor for Blacksmith character, initializes with default hit points and the SMITH capability.
     */
    public Blacksmith(){
        super("Blacksmith", 'B', DEFAULT_HIT_POINTS);
        this.addCapability(Status.SMITH);
    }

    /**
     * Allows the Blacksmith to take a turn, but by default, Blacksmith doesn't do any action.
     *
     * @param actions list of available actions
     * @param lastAction the last action taken
     * @param map the game map
     * @param display the display in which the game is shown
     * @return a new DoNothingAction
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Generates the actions the Blacksmith can perform on their turn.
     *
     * @param otherActor The actor that is in the proximity of the Blacksmith.
     * @param direction The direction from the Blacksmith to the otherActor.
     * @param map The map where the Blacksmith resides.
     * @return The list of allowable actions for the Blacksmith.
     */
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map){
        ActionList actionList = new ActionList();
        actionList.add(monologueList(otherActor));
        return actionList;
    }

    /**
     * A method to provide a standard set of monologues for the Blacksmith.
     */
    @Override
    public void standardMonologue() {
        this.monologueList.add("I used to be an adventurer like you, but then …. Nevermind, let’s get back to smithing.");
        this.monologueList.add("It’s dangerous to go alone. Take my creation with you on your adventure!");
        this.monologueList.add("Ah, it’s you. Let’s get back to make your weapons stronger.");
    }

    /**
     * Provides a list of monologues based on the state of the player and the Blacksmith.
     *
     * @param player The player actor.
     * @return A ConversationAction containing the monologues.
     */
    @Override
    public Action monologueList(Actor player){
        monologueList.clear();

        String beforeBossDefeated = "Beyond the burial ground, you’ll come across the ancient woods ruled by Abxervyer. Use my creation to slay them and proceed further!";
        String afterBossDefeated = "Somebody once told me that a sacred tree rules the land beyond the ancient woods until this day.";
        String withKnife = "Hey now, that’s a weapon from a foreign land that I have not seen for so long. I can upgrade it for you if you wish.";

        standardMonologue();
        this.monologueList.add(beforeBossDefeated);

        // add dialogue if player is holding the Great Knife
        if (player.hasCapability(Status.HOLDING_KNIFE)){
            this.monologueList.add(withKnife);
        }

        // add dialogue if player has defeated Abxervyer
        if (player.hasCapability(Status.BOSS_DEFEATED)){
            this.monologueList.remove(beforeBossDefeated);
            this.monologueList.add(afterBossDefeated);
        }

        return new ConversationAction(this, monologueList);
    }



}
