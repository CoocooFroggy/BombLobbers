package com.CoocooFroggy.bomblobbers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WaterListener implements Listener {
    @EventHandler
    public boolean onWater(PlayerMoveEvent event) {
        //If the plugin is off
        if (!Main.mainSwitch) {
            //Don't do anything and just return
            return false;
        }
        //If no game has started yet
        if (!StartGame.gameStarted) {
            //Don't do anything and just return
            return false;
        }
        //Get current block player is on
        Material currentBlock = event.getPlayer().getLocation().getBlock().getType();
        //If that block is water
        if (currentBlock == Material.WATER) {
            //Damage then for 1 1/2 hearts (3 hp)
            event.getPlayer().damage(3);
        }
        return true;
    }
}
