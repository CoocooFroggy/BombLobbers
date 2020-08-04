package com.CoocooFroggy.bomblobbers;

import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class WinDetector {
    static boolean winnerFound = false;

    public static boolean winDetector() {
        //Vars
        int zeroCounter = 0;

        //If debug is on:
        debugLabel:
        while (Main.debug) {
            //If debug is stopped, skip over the normal game
            zeroCounter = 999;

            if (Main.stopSwitch) {
                break debugLabel;
            }

            //Delay 1 tick
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                getLogger().info("Couldn't sleep: " + e);
            }
        }

        //Normal game
        whileLabel:
        while (zeroCounter < 2) {
            zeroCounter = 0;

            if (Main.stopSwitch) {
                break whileLabel;
            }

            //Check to see what teams have no players alive
            List<Player> bluePlayers = DeathListener.teamsAndAlive.get("blue");
            List<Player> redPlayers = DeathListener.teamsAndAlive.get("red");
            List<Player> greenPlayers = DeathListener.teamsAndAlive.get("green");

            if (bluePlayers.size() < 1) {
                zeroCounter++;
            }
            if (redPlayers.size() < 1) {
                zeroCounter++;
            }
            if (greenPlayers.size() < 1) {
                zeroCounter++;
            }

            //Wait 5 seconds
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                getLogger().info("Couldn't sleep: " + e);
            }
        }

        winnerFound = true;
        getLogger().info("Winner found!");
        return true;
    }
}
