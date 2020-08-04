package com.CoocooFroggy.bomblobbers;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class StartGame {
    static boolean gameStarted;

    public boolean startGame(Player player) {
        if (gameStarted) {
            player.sendMessage(ChatColor.RED + "Game already running! Stop the game by using " + ChatColor.GOLD + "/bomblobbers stop");
            return false;
        }
        gameStarted = true;

        //Make teamsAndAlive be teamsAndPlayers with a deep clone
//        DeathListener.teamsAndAlive = (HashMap<String, List<Player>>) DeathListener.teamsAndPlayers.clone();
        //FYI me of tomorrow, make this part below go through each list and add it to the new array
        DeathListener.teamsAndAlive.put("blue", DeathListener.teamsAndPlayers.get("blue"));
        DeathListener.teamsAndAlive.put("red", DeathListener.teamsAndPlayers.get("red"));
        DeathListener.teamsAndAlive.put("green", DeathListener.teamsAndPlayers.get("green"));

        List<Player> playerList = player.getWorld().getPlayers();

        //1 red stained glass pane
        ItemStack paneStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);

        //1 stack of scaffold
        ItemStack scaffoldStack = new ItemStack(Material.SCAFFOLDING, 64);

        //3 enderpearls
        ItemStack enderPearlItemStack = new ItemStack(Material.ENDER_PEARL, 3);

        //Give players the items
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).getInventory().addItem(paneStack);
            playerList.get(i).getInventory().addItem(scaffoldStack);
            playerList.get(i).getInventory().addItem(scaffoldStack);
            playerList.get(i).getInventory().addItem(enderPearlItemStack);
        }

        if (!Main.mainSwitch) {
            player.sendMessage(ChatColor.RED + "You need to enable the plugin first! " + ChatColor.GOLD + "/bomblobbers enable");
            return false;
        }

        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Bomb lobbers starting soon!");
        }
        for (int j = 5; j > 0; j--) {
            for (int i = 0; i < playerList.size(); i++) {
                if (j == 5) {
                    playerList.get(i).sendTitle("Game starting in", null, 10, 25, 0);
                    playerList.get(i).sendTitle(null, j + "", 10, 25, 0);
                } else {
                    playerList.get(i).sendTitle("Game starting in", null, 0, 25, 0);
                    playerList.get(i).sendTitle(null, j + "", 0, 25, 0);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                getLogger().info("Couldn't sleep: " + e);
            }
        }
        try {
            Thread.sleep(250);
        } catch (Exception e) {
            getLogger().info("Couldn't sleep: " + e);
        }
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).sendTitle(ChatColor.DARK_GREEN + "Start!", null, 4, 30, 7);
        }

        //1 tnt as an ItemStack
        ItemStack tntItemStack = new ItemStack(Material.TNT, 1);

        int zeroCounter = 0;

        //Debug
        debugLabel:
        while (Main.debug) {
            //Perform to all players
            for (int i = 0; i < playerList.size(); i++) {
                //Remove the pane placeholder
                playerList.get(i).getInventory().removeItem(paneStack);
                //Give all players a tnt
                playerList.get(i).getInventory().addItem(tntItemStack);
            }

            if (Main.stopSwitch) {
                break debugLabel;
            }

            //Wait 5 seconds
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                getLogger().info("Couldn't sleep: " + e);
            }

            //This is just here so that IntelliJ doesn't get mad at me
            if (false) {
                break;
            }
        }

        whileLabel:
        while (zeroCounter < 2) {
            zeroCounter = 0;

            //Perform to all players
            for (int i = 0; i < playerList.size(); i++) {
                //Remeove the pane placeholder
                playerList.get(i).getInventory().removeItem(paneStack);
                //Give all players a tnt
                playerList.get(i).getInventory().addItem(tntItemStack);
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

            if (Main.stopSwitch) {
                break whileLabel;
            }

            //Wait 5 seconds
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                getLogger().info("Couldn't sleep: " + e);
            }
        }

        //Determine winning team
        String winner = "No";
        ChatColor winningChatColor = ChatColor.GRAY;

//        //Debug
//        player.sendMessage("Players on blue team: " + DeathListener.teamsAndAliveCount.get("blue"));
//        player.sendMessage("Players on red team: " + DeathListener.teamsAndAliveCount.get("red"));
//        player.sendMessage("Players on green team: " + DeathListener.teamsAndAliveCount.get("green"));

        if (DeathListener.teamsAndAlive.get("blue").size() > 0) {
            winner = "Blue";
            winningChatColor = ChatColor.BLUE;
        }
        if (DeathListener.teamsAndAlive.get("red").size() > 0) {
            winner = "Red";
            winningChatColor = ChatColor.RED;
        }
        if (DeathListener.teamsAndAlive.get("green").size() > 0) {
            winner = "Green";
            winningChatColor = ChatColor.GREEN;
        }

        //Game over, when all players die
        for (Player currentPlayer : playerList) {
            currentPlayer.sendTitle(ChatColor.DARK_RED + "Game Over!", winningChatColor + winner + " team wins!", 10, 50, 10);
        }
        //Debug
//        player.sendMessage("Players on blue team: " + DeathListener.teamsAndAliveCount.get("blue"));
//        player.sendMessage("Players on red team" + DeathListener.teamsAndAliveCount.get("red"));
//        player.sendMessage("Players on green team" + DeathListener.teamsAndAliveCount.get("green"));

        //Reset the dictionary
        DeathListener.teamsAndAliveCount.put("blue", 0);
        DeathListener.teamsAndAliveCount.put("red", 0);
        DeathListener.teamsAndAliveCount.put("green", 0);

        //Put everyone in creative, clear their inventories (except for armor)
        Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                for (Player currentPlayer : playerList) {
                    //Put them in creative
                    currentPlayer.setGameMode(GameMode.CREATIVE);

                    //Save their armor when clearing inventory
                    ItemStack[] armorContents = currentPlayer.getInventory().getArmorContents().clone();
                    currentPlayer.getInventory().clear();
                    currentPlayer.getInventory().setArmorContents(armorContents);
                    currentPlayer.updateInventory();
                }
            }
        });

        //Turn off the game
        gameStarted = false;
        Main.stopSwitch = false;
        DeathListener.deathCount = 0;

        return true;
    }
}
