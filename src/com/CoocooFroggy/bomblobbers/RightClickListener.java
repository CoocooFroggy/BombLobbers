package com.CoocooFroggy.bomblobbers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.CoocooFroggy.bomblobbers.Main.plugin;
import static org.bukkit.Bukkit.getLogger;

public class RightClickListener implements Listener {
    static Object[] onlinePlayers = null;
    static List<Player> cooldown = new ArrayList<>();

    @EventHandler
    public boolean onRightClick(PlayerInteractEvent event) {
        if (Main.mainSwitch) {
            Player player = event.getPlayer();
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.getInventory().getItemInMainHand().getType() == Material.TNT) {
                    event.setCancelled(true);

                    if (!cooldown.isEmpty()) {
                        int cooldownTime = plugin.getConfig().getInt("cooldown.current");
                        for (int i = 0; i < cooldown.size(); i++) {
                            if (cooldown.get(i).equals(player)) {
                                player.sendMessage(ChatColor.RED + "TNT is on cooldown for " + cooldownTime + " seconds.");
                                return true;
                            }
                        }
                    }

                    //List of players = online players
                    onlinePlayers = player.getServer().getOnlinePlayers().toArray();

                    //ItemStack of 1 tnt
                    ItemStack tntItemStack = new ItemStack(Material.TNT, 1);
                    //Remove 1 tnt
                    player.getInventory().removeItem(tntItemStack);
                    //Spawn TNT
                    Entity newTNT = player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.PRIMED_TNT);

                    //Make TNT go brrr
                    float throwVelocity = plugin.getConfig().getInt("throw-velocity.current");
                    newTNT.setVelocity(player.getEyeLocation().getDirection().multiply(throwVelocity));

                    //Start trackng the TNT
                    DirectHitChecker nonStaticDirectHitTracker = new DirectHitChecker();
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            nonStaticDirectHitTracker.trackTNT(newTNT, player);
                        }
                    });

                    //MARK: Cooldown
                    //Get cooldown
                    long cooldownTime = plugin.getConfig().getInt("cooldown.current") * 20;

                    cooldown.add(player);
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            cooldown.remove(player);
                        }
                    }, cooldownTime);

                    return true;
                }
            }
        }
        return true;
    }
}
