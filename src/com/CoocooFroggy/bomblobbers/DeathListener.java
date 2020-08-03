package com.CoocooFroggy.bomblobbers;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;

public class DeathListener implements Listener {
    static int deathCount = 0;
    static HashMap<String, Integer> teamsAndAliveCount = new HashMap<String, Integer>();
    //New teamsAndPlayers
    static HashMap<String, List<Player>> teamsAndPlayers = new HashMap<String, List<Player>>();
    static HashMap<String, List<Player>> teamsAndAlive = new HashMap<String, List<Player>>();
    static HashMap<Player, String> playersAndTeams = new HashMap<Player, String>();

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent event) {
        if (StartGame.gameStarted) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (event.getDamage() >= player.getHealth() - 1) {
                    deathCount++;

                    //Get the team the dead player was on
                    String currentTeam = playersAndTeams.get(player);

                    //Subtract 1 from the team alive count
                    teamsAndAliveCount.put(currentTeam, teamsAndAliveCount.get(currentTeam) - 1);

                    //Remove player from team
                    List<Player> teamList = teamsAndAlive.get(currentTeam);
                    teamList.remove(player);
                    teamsAndAlive.put(currentTeam, teamList);

                    //Puts the player in spectator
                    player.setGameMode(GameMode.SPECTATOR);

                    //Heals them to full
                    player.setHealth(20);

                    //Respawns them
                    Location spawn = player.getWorld().getSpawnLocation();
                    player.teleport(spawn);

                    //Makes them not die (by not taking damage)
                    event.setCancelled(true);

                    //Tells them they died
                    player.sendTitle(ChatColor.RED + "You died!", null, 8, 30, 8);
                }
            }
        }
    }
}
