package com.CoocooFroggy.bomblobbers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getLogger;

public class TNTDistributor {
    public static boolean distribute() {

        //1 tnt as an ItemStack
        ItemStack tntItemStack = new ItemStack(Material.TNT, 1);

        while (!WinDetector.winnerFound) {
            //Perform to all players
            for (int i = 0; i < StartGame.playerList.size(); i++) {
                //Give all players a tnt
                StartGame.playerList.get(i).getInventory().addItem(tntItemStack);
            }

            //Wait 5 seconds
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                getLogger().info("Couldn't sleep: " + e);
            }
        }
        return true;
    }
}
