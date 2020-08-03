package com.CoocooFroggy.bomblobbers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    static boolean mainSwitch;
    static boolean debug;
    static boolean stopSwitch;
    static Plugin plugin;
    BukkitTask gameTask;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new RightClickListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new WaterListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getCommand("bomblobbers").setTabCompleter(new TabCompleter());

        //Set default values for teamsAndAliveCount
        DeathListener.teamsAndAliveCount.put("blue", 0);
        DeathListener.teamsAndAliveCount.put("red", 0);
        DeathListener.teamsAndAliveCount.put("green", 0);

        //Set default values for teamsAndPlayers
        List<Player> blueList = new ArrayList<>();
        List<Player> redList = new ArrayList<>();
        List<Player> greenList = new ArrayList<>();
        DeathListener.teamsAndPlayers.put("blue", blueList);
        DeathListener.teamsAndPlayers.put("red", redList);
        DeathListener.teamsAndPlayers.put("green", greenList);

        //Set plugin
        plugin = this;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        super.onDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("bomblobbers") || label.equalsIgnoreCase("bl")) {
            if (!(args.length < 1)) {
                if (args[0].equalsIgnoreCase("enable")) {
                    // /bomblobbers enable
                    mainSwitch = true;
                    sender.sendMessage(ChatColor.GREEN + "Enabled Bomb Lobbers plugin!");
                    return true;
                } else if (args[0].equalsIgnoreCase("disable")) {
                    // /bomblobbers disable
                    if (StartGame.gameStarted) {
                        sender.sendMessage(ChatColor.RED + "Game in progress! Stop the game first by using " + ChatColor.GOLD + "/bomblobbers stop");
                        return false;
                    }
                    mainSwitch = false;
                    sender.sendMessage("Disabled Bomb Lobbers plugin!");
                    return true;
                } else if (args[0].equalsIgnoreCase("start")) {
                    //Command only runs for players
                    if (sender instanceof Player) {
                        // /bomblobbers start
                        //Gamemode adventure
                        List<Player> playerList = ((Player) sender).getWorld().getPlayers();
                        for (int i = 0; i < playerList.size(); i++) {
                            playerList.get(i).setGameMode(GameMode.SURVIVAL);
                        }

                        //Runs startGame asynchronously (bad idea?)
                        StartGame nonStaticStartGame = new StartGame();
                        gameTask = Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
                            @Override
                            public void run() {
                                nonStaticStartGame.startGame((Player) sender);
                            }
                        });
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("stop")) {
                    stopSwitch = true;
                    sender.sendMessage("Stopped the game.");
                    return true;
                } else if (args[0].equalsIgnoreCase("changeteam")) {
                    // /bomblobbers changeteam
                    if (!mainSwitch) {
                        sender.sendMessage(ChatColor.RED + "You need to enable the plugin first! " + ChatColor.GOLD + "/bomblobbers enable");
                        return true;
                    }
                    if (StartGame.gameStarted) {
                        sender.sendMessage(ChatColor.RED + "Game in progress! Stop the game first by using " + ChatColor.GOLD + "/bomblobbers stop");
                        return false;
                    }
                    if (sender instanceof Player) {
                        ChangeTeam.changeTeam((Player) sender);
                        return true;
                    } else {
                        sender.sendMessage("Sorry, you have to be a player to change teams.");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("debug")) {
                    debug = true;
                    mainSwitch = true;
                    sender.sendMessage(ChatColor.GREEN + "Enabled Bomb Lobbers debug plugin!");
                    return true;
                } else {
                    //Sender didn't type "enable" or "disable"
                    sender.sendMessage(ChatColor.RED + "Syntax: /bomblobbers [enable/disable, start/stop]");
                    return true;
                }
            } else {
                mainSwitch = !mainSwitch;
                if (mainSwitch) {
                    sender.sendMessage(ChatColor.GREEN + "Enabled Bomb Lobbers plugin!");
                    return true;
                } else {
                    sender.sendMessage("Disabled Bomb Lobbers plugin!");
                    return true;
                }
            }
        }
        return false;
    }
}