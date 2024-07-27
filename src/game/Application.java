package game;

import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.*;
import game.actors.abilities.WeatherControl;
import game.actors.enemies.*;
import game.actors.enemies.spawnableenemies.*;
import game.behaviours.Follow;
import game.grounds.*;
import game.grounds.Void;
import game.grounds.spawninggrounds.Bush;
import game.grounds.spawninggrounds.EmptyHut;
import game.grounds.spawninggrounds.Graveyard;
import game.items.OldKey;
import game.items.consumables.Bloodberry;
import game.items.consumables.HealingVial;
import game.items.consumables.RefreshingFlask;
import game.items.weapons.Broadsword;
import game.items.weapons.GiantHammer;
import game.items.weapons.GreatKnife;

import java.util.Arrays;  // Import the java.util package to use Arrays

import java.util.ArrayList;
import java.util.Collections;

/**
 * The main class to start the game.
 * This class initializes and configures the game world, maps, actors, and items, then starts the game loop.
 * @author Adrian Kristanto
 * Modified by:
 * @author Sarviin Hari
 */
public class Application {
    /**
     * The main entry point for starting the game.
     * Initializes and configures the game world, maps, actors, and items, then starts the game loop.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {

        World world = new World(new Display());

    // CREATE MAP
        // ground factory for abandoned village and burial grounds
        FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(),
                new Wall(), new Floor(), new Puddle(), new Void(), new LockedGate());

        // create an abandoned village game map and add in the world
        GameMap abandonedVillage = new GameMap(groundFactory, Maps.villageMap);
        world.addGameMap(abandonedVillage);

        // create a burial ground game map and add in the world
        GameMap burialGround = new GameMap(groundFactory, Maps.burialMap);
        world.addGameMap(burialGround);

        // create an ancient woods game map and add in the world
        GameMap ancientWoods = new GameMap(groundFactory, Maps.forestMap);
        world.addGameMap(ancientWoods);

        // create a battle map game map and add in the world
        GameMap battlingMap = new GameMap(groundFactory, Maps.battleMap);
        world.addGameMap(battlingMap);

        // create a battle map game map and add in the world
        GameMap overgrownSanctuary = new GameMap(groundFactory, Maps.overgrownSanctuary);
        world.addGameMap(overgrownSanctuary);

    // CREATE MAP LIST
        //ArrayList of Maps that consists of all Maps
        ArrayList<GameMap> allMaps = new ArrayList<>();
        allMaps.add(abandonedVillage);
        allMaps.add(burialGround);
        allMaps.add(ancientWoods);
        allMaps.add(battlingMap);
        allMaps.add(overgrownSanctuary);

        //Making an ArrayList that contains the maps in which the weather would affect
        ArrayList<GameMap> weatherMaps = new ArrayList<>();
        weatherMaps.add(ancientWoods);
        weatherMaps.add(battlingMap);


    // ADD MAP WEATHER
        WeatherControl weather = new WeatherControl(weatherMaps);
        weather.isSunny();

    // ADD ACTOR

        // Making an ArrayList that contains the destinations that the ForestWatcher's gate could lead you to
        ArrayList<LocationMap> destinationList = new ArrayList<>();
        destinationList.add(new LocationMap(ancientWoods.at(22, 8), "Ancient Woods!"));
        destinationList.add(new LocationMap(overgrownSanctuary.at(35, 9), "OvergrownSanctuary"));


        // add forest watcher instance in battle map
        battlingMap.at(13, 10).addActor(new ForestWatcher(destinationList, weatherMaps));

        // add isolated traveller instance in ancient woods
        ancientWoods.at(20, 3).addActor(new IsolatedTraveller());

        MapReset mapReset = new MapReset(allMaps);

        // instantiate a player and add to the world
        Player player = new Player("The Abstracted One", '@', 150, 200, new MoveActorAction(abandonedVillage.at(29, 5), "Start of the Game"), mapReset);
        world.addPlayer(player, abandonedVillage.at(29, 5));

        // instantiates a Blacksmith
        Blacksmith blacksmith = new Blacksmith();
        abandonedVillage.at(27,5).addActor(blacksmith);

    // ADD SPAWNING GROUND
        // instantiate Graveyard in abandoned village with WanderingUndead
        abandonedVillage.at(34, 9).setGround(new Graveyard(new WanderingUndead(), 25)); // 25 is the percentage to spawn a WanderingUndead

        // instantiate Graveyard in burial ground with HollowSoldier
        burialGround.at(34, 9).setGround(new Graveyard(new HollowSoldier(), 10));   // 10 is the percentage to spawn a HollowSoldier

        // instantiate empty huts and bush  at Ancient Woods with ForestKeeper and RedWolf respectively
        ancientWoods.at(34, 11).setGround(new EmptyHut(new ForestKeeper(), 15));
        ancientWoods.at(35,11).setGround(new Bush(new RedWolf(), 30));

        // instantiate empty huts and bush  at Battling Map with ForestKeeper and RedWolf respectively
        battlingMap.at(27, 3).setGround(new EmptyHut(new ForestKeeper(), 15));
        battlingMap.at(15,8).setGround(new Bush(new RedWolf(), 30));

        //instantiate Graveyard with HollowSoldier, empty hut with ForestKeeper and bush with RedWolf in overgrownSanc
        overgrownSanctuary.at(5, 1).setGround(new Graveyard(new HollowSoldier(), 10));   // 10 is the percentage to spawn a HollowSoldier
        overgrownSanctuary.at(34, 13).setGround(new Graveyard(new HollowSoldier(), 10));   // 10 is the percentage to spawn a HollowSoldier
        overgrownSanctuary.at(27, 3).setGround(new EmptyHut(new EldentreeGuardian(), 20));
        overgrownSanctuary.at(10, 10).setGround(new EmptyHut(new EldentreeGuardian(), 20));
        overgrownSanctuary.at(1,5).setGround(new Bush(new LivingBranch(), 90));
        overgrownSanctuary.at(15,8).setGround(new Bush(new LivingBranch(), 90));

    // ADD GATES
        // Locked Gate to and from Abandoned Village and Burial Ground
        abandonedVillage.at(22, 7).setGround(new LockedGate((new LocationMap(burialGround.at(23, 7), "Burial Ground!"))));
        burialGround.at(23, 7).setGround(new LockedGate((new LocationMap(abandonedVillage.at(22, 7), "Abandoned Village!"))));

        // Locked Gate to and from Ancient Woods and Burial Ground
        burialGround.at(26, 7).setGround(new LockedGate((new LocationMap(ancientWoods.at(22, 6), "Ancient Woods!"))));
        ancientWoods.at(22, 6).setGround(new LockedGate((new LocationMap(burialGround.at(26, 7), "Burial Ground!"))));

        // Locked Gate from Ancient Woods and Battle Map
        ancientWoods.at(22, 8).setGround(new LockedGate((new LocationMap(battlingMap.at(8, 10), "Battle Map!"))));

        // Locked Gate from overgrownSanctuary to Battle Map
        overgrownSanctuary.at(35, 9).setGround(new LockedGate((new LocationMap(battlingMap.at(8, 10), "Battle Map!"))));

    // ADD ITEM
        // instantiate a broadsword and add to the map
        ancientWoods.at(20, 6).addItem(new Bloodberry());

        // instantiate a broadsword and add to the map
        Broadsword broadsword = new Broadsword();
        abandonedVillage.at(30,5).addItem(broadsword);

        // instantiate a giantHammer and add to the map
        GiantHammer giantHammer = new GiantHammer();
        battlingMap.at(7,7).addItem(giantHammer);

    // ADD item and actor for testing
//        RefreshingFlask refreshingFlask = new RefreshingFlask();
//        abandonedVillage.at(31,5).addItem(refreshingFlask);

//        HealingVial healingVial = new HealingVial();
//        abandonedVillage.at(31,6).addItem(healingVial);

//        GiantHammer giantHammer_test = new GiantHammer();
//        abandonedVillage.at(30,6).addItem(giantHammer_test);

//        GreatKnife greatKnife_test = new GreatKnife();
//        abandonedVillage.at(28,6).addItem(greatKnife_test);

//        IsolatedTraveller isolatedTraveller = new IsolatedTraveller();
//        abandonedVillage.at(31, 6).addActor(isolatedTraveller);



        //TESTING
//        ArrayList<LocationMap> arrayList = new ArrayList<>();
//        arrayList.add(new LocationMap(overgrownSanctuary.at(35, 9), "Burial Ground!"));
//        arrayList.add(new LocationMap(battlingMap.at(8,10),"to Battle Map!"));
//        abandonedVillage.at(22, 9).setGround(new LockedGate(arrayList));


        // ADD MAP WEATHER
//        WeatherControl weather = new WeatherControl(maps);
//        weather.isSunny();


    // TITLE DISPLAY
        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }


        world.run();

    }
}
