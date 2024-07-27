package game.actions.conversations;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.Random;

/**
 * An action representing a conversation with an actor.
 * This action allows one actor to listen to a monologue provided by another actor.
 *
 * Authored by:
 * @author Choong Lee Ann
 * Modified by:
 * @author Sarviin Hari
 * @version 1.0
 */
public class ConversationAction extends Action {

    /**
     * The actor performing the conversation action
     */
    private Actor actor;
    /**
     * Stores a list of dialogue options for a given actor
     */
    private ArrayList<String> monologueList;

    private Random random = new Random();

    /**
     * Constructs a new ConversationAction.
     *
     * @param actor The actor delivering the monologue.
     * @param monologueList A list of strings representing the monologues to choose from.
     */
    public ConversationAction(Actor actor, ArrayList<String> monologueList){
        this.actor = actor;
        this.monologueList = monologueList;
    }

    /**
     * Executes the conversation action.
     * Selects a random monologue from the list and returns it.
     *
     * @param actor The actor initiating the conversation.
     * @param map The game map where the conversation occurs.
     * @return A randomly selected monologue from the list.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int rand = random.nextInt(monologueList.size());

        return monologueList.get(rand);
    }

    /**
     * Returns a description of the action to be displayed in the game's menu.
     *
     * @param actor The actor initiating the conversation.
     * @return A description of the conversation action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Listen to " + this.actor + "'s monologue.";
    }
}
