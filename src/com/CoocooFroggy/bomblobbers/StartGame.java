package com.CoocooFroggy.bomblobbers;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.CoocooFroggy.bomblobbers.Main.plugin;
import static org.bukkit.Bukkit.getLogger;

public class StartGame {
    static boolean gameStarted;
    static List<Player> playerList;

    public boolean startGame(Player player) {
        if (gameStarted) {
            player.sendMessage(ChatColor.RED + "Game already running! Stop the game by using " + ChatColor.GOLD + "/bomblobbers stop");
            return false;
        }
        if (!Main.mainSwitch) {
            player.sendMessage(ChatColor.RED + "You need to enable the plugin first! " + ChatColor.GOLD + "/bomblobbers enable");
            return false;
        }
        gameStarted = true;

        //Make teamsAndAlive be teamsAndPlayers with a deep clone
        DeathListener.teamsAndAlive.get("blue").clear();
        DeathListener.teamsAndAlive.get("red").clear();
        DeathListener.teamsAndAlive.get("green").clear();
        List<Player> blueList = DeathListener.teamsAndPlayers.get("blue");
        List<Player> redList = DeathListener.teamsAndPlayers.get("red");
        List<Player> greenList = DeathListener.teamsAndPlayers.get("green");
        for (Player currentPlayer :
                blueList) {
            DeathListener.teamsAndAlive.get("blue").add(currentPlayer);
        }
        for (Player currentPlayer :
                redList) {
            DeathListener.teamsAndAlive.get("red").add(currentPlayer);
        }
        for (Player currentPlayer :
                greenList) {
            DeathListener.teamsAndAlive.get("green").add(currentPlayer);
        }

        playerList = player.getWorld().getPlayers();

        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Bomb lobbers starting soon!");
        }

        //1 red stained glass pane
        ItemStack paneStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);

        //1 stack of scaffold
        ItemStack scaffoldStack = new ItemStack(Material.SCAFFOLDING, 64);

        //3 enderpearls
        ItemStack enderPearlItemStack = new ItemStack(Material.ENDER_PEARL, 3);

        //Put everyone in adventure, clear their inventories (except for armor), give them items
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player currentPlayer : playerList) {
                    //Put them in adventure
                    currentPlayer.setGameMode(GameMode.ADVENTURE);

                    //Save their armor when clearing inventory
                    ItemStack[] armorContents = currentPlayer.getInventory().getArmorContents().clone();
                    currentPlayer.getInventory().clear();
                    currentPlayer.getInventory().setArmorContents(armorContents);
                    currentPlayer.updateInventory();

                    //Give players the items
                    currentPlayer.getInventory().addItem(paneStack);
                    currentPlayer.getInventory().addItem(scaffoldStack);
                    currentPlayer.getInventory().addItem(scaffoldStack);
                    currentPlayer.getInventory().addItem(enderPearlItemStack);

                }
            }
        });

        //MARK: Countdown
        //Get countdown amount
        int countdown = plugin.getConfig().getInt("countdown.current");

        for (int j = countdown; j > 0; j--) {
            for (int i = 0; i < playerList.size(); i++) {
                if (j == countdown) {
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

        //Put everyone in survival
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player currentPlayer : playerList) {
                    //Put them in survival
                    currentPlayer.setGameMode(GameMode.SURVIVAL);
                }
            }
        });

        //Start message
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).sendTitle(ChatColor.DARK_GREEN + "Start!", null, 4, 30, 7);
        }

        //WinDetector
        WinDetector.winnerFound = false;

        //Start up WinDetector asynchronously
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                WinDetector.winDetector();
            }
        });

        //Perform to all players
        for (int i = 0; i < playerList.size(); i++) {
            //Remove the pane placeholder
            playerList.get(i).getInventory().removeItem(paneStack);
        }

        //Start giving TNT asynchronously
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                TNTDistributor.distribute();
            }
        });

        while (!WinDetector.winnerFound) {
            //Stall until winner is found
            try {
                Thread.sleep(100);
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
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
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
