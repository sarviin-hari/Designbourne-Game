package game.actors.abilities;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Weather;

import java.util.ArrayList;

/**
 * A class representing the control of weather in the game.
 *
 * Authored by:
 * @author Raynen
 * Modified by:
 * @author Jun Hirano
 * @author Sarviin Hari
 * @version 1.0
 */
public class WeatherControl {

    /** Current weather in the game
     * Required to set the starting weather in Application
     * To indicate the current weather to the Spawning Enemies
     * Default is SUNNY. */
    public static Weather WEATHER_CURR = Weather.SUNNY;

    /** The current weather. */
    private Weather weather;

    /** Counter for weather switch rounds. */
    private int counter = 1;

    /** The actor controlling the weather. */
    private Actor actor;

    /** Number of rounds before switching weather. */
    private int switchCounter;

    /** A factor based on switchCounter. */
    private int secondSwitchCounter;

    /** List of game maps affected by the weather. */
    private ArrayList<GameMap> gameMaps;

    /** Flag indicating if this instance is controlling the weather. */
    private boolean controller;

    /**
     * Constructor used in Application to set the initial weather as SUNNY.
     *
     * @param gameMaps List of game maps affected by the weather.
     */
    public WeatherControl(ArrayList<GameMap> gameMaps) {
        this.weather = Weather.SUNNY;  // Weather starts off SUNNY
        this.gameMaps = gameMaps;
        this.controller = false;
    }

    /**
     * Constructor used by the Actor that controls the weather.
     *
     * @param actor         The actor controlling the weather.
     * @param switchCounter Number of rounds before switching weather.
     * @param gameMaps      List of game maps affected by the weather.
     */
    public WeatherControl(Actor actor, int switchCounter, ArrayList<GameMap> gameMaps) {
        this.actor = actor;
        this.actor.addCapability(Weather.SUNNY);
        this.switchCounter = switchCounter; // counter at which the first weather must switch
        this.secondSwitchCounter = switchCounter * 2; // counter at which the second weather must switch
        this.gameMaps = gameMaps;
        this.controller = true;
    }

    /**
     * Switches the weather between SUNNY and RAINY based on the set counter and switchCounter.
     */
    public void switchWeather() {
        // Only allows this method to run when controlled by an actor
        if (this.controller) {
            // Switch to sunny weather if counter is less than switchCounter
            if (counter < switchCounter) {
                // Switch to sunny weather
                WeatherControl.WEATHER_CURR = Weather.SUNNY;
                actor.addCapability(Weather.SUNNY);
                actor.removeCapability(Weather.RAINY);
                System.out.println("Current Weather is Sunny");

            }
            // Switch to rainy weather if counter is equal to or greater than switchCounter
            else {
                // Switch to rainy weather
                WeatherControl.WEATHER_CURR = Weather.RAINY;
                actor.addCapability(Weather.RAINY);
                actor.removeCapability(Weather.SUNNY);
                System.out.println("Current Weather is Rainy");

//                System.out.println("Switching to rainy weather.");
            }

            // Resets the counter after the (secondSwitchCounter)
            counter++;
            if (counter == secondSwitchCounter) {
                counter = 0;  // Reset the counter after 6 rounds
            }
        }
    }

    /**
     * Updates the weather on a specific game map.
     *
     * @param map     The game map to update weather for.
     * @param weather The new weather to set.
     */
    private void updateWeather(GameMap map, Weather weather) {
        // Update weather on actors and ground in the given map for every x, y coordinate
        for (int y : map.getYRange()) {
            for (int x : map.getXRange()) {
                Location location = map.at(x, y);
                Actor actor = location.getActor();
                if (actor != null) {
                    actor.removeCapability(Weather.RAINY);
                    actor.removeCapability(Weather.SUNNY);
                    actor.addCapability(weather);
                }

                Ground ground = location.getGround();
                if (ground != null) {
                    ground.removeCapability(Weather.RAINY);
                    ground.removeCapability(Weather.SUNNY);
                    ground.addCapability(weather);
                }
            }
        }
    }

    /**
     * Sets the weather to SUNNY and updates it across all game maps.
     */
    public void isSunny() {
        WeatherControl.WEATHER_CURR = Weather.SUNNY;
        updateWeatherForAllMaps(Weather.SUNNY);
    }

    /**
     * Sets the weather to NORMAL and updates it across all game maps.
     */
    public void isNormal() {
        WeatherControl.WEATHER_CURR = Weather.NORMAL;
        updateWeatherForAllMaps(Weather.NORMAL);
    }

    /**
     * Sets the weather to RAINY and updates it across all game maps.
     */
    public void isRainy() {
        WeatherControl.WEATHER_CURR = Weather.RAINY;
        updateWeatherForAllMaps(Weather.RAINY);
    }


    /**
     * Loops and updates the weather for all game maps.
     *
     * @param weather The new weather to set.
     */
    private void updateWeatherForAllMaps(Weather weather) {
        for (GameMap map : gameMaps) {
            updateWeather(map, weather);
        }
    }
}