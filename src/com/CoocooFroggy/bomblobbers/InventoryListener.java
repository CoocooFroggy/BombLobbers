package com.CoocooFroggy.bomblobbers;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class InventoryListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(ChangeTeam.inv))
            return;
        if (event.getCurrentItem() == null)
            return;
        if (event.getCurrentItem().getItemMeta() == null)
            return;
        if (event.getCurrentItem().getItemMeta().getDisplayName() == null)
            return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        if (event.getSlot() == 0) {
            //Blue team
            //Vars
            LeatherArmorMeta meta;

            //Make leather ItemStacks
            ItemStack leatherHelmetStack = new ItemStack(Material.LEATHER_HELMET);
                meta = (LeatherArmorMeta) leatherHelmetStack.getItemMeta();
                meta.setColor(Color.BLUE);
                leatherHelmetStack.setItemMeta(meta);
            ItemStack leatherChestplateStack = new ItemStack(Material.LEATHER_CHESTPLATE);
                meta = (LeatherArmorMeta) leatherChestplateStack.getItemMeta();
                meta.setColor(Color.BLUE);
                leatherChestplateStack.setItemMeta(meta);
            ItemStack leatherLeggingsStack = new ItemStack(Material.LEATHER_LEGGINGS);
                meta = (LeatherArmorMeta) leatherLeggingsStack.getItemMeta();
                meta.setColor(Color.BLUE);
                leatherLeggingsStack.setItemMeta(meta);
            ItemStack leatherBootsStack = new ItemStack(Material.LEATHER_BOOTS);
                meta = (LeatherArmorMeta) leatherBootsStack.getItemMeta();
                meta.setColor(Color.BLUE);
                leatherBootsStack.setItemMeta(meta);

            //Make a leather armor ItemStack array
            ItemStack[] leatherStackArray = {leatherBootsStack, leatherLeggingsStack, leatherChestplateStack, leatherHelmetStack};

            //Put it on player
            player.getInventory().setArmorContents(leatherStackArray);

            //Add 1 count to teamsAndAliveCount for team
            DeathListener.teamsAndAliveCount.put("blue", DeathListener.teamsAndAliveCount.get("blue") + 1);

            //Remove player from other teams
            String currentOtherTeam = "red";
            for (int i = 0; i < 2; i++) {
                List<Player> otherTeamList = DeathListener.teamsAndPlayers.get(currentOtherTeam);
                otherTeamList.remove(player);
                DeathListener.teamsAndPlayers.put(currentOtherTeam, otherTeamList);
                currentOtherTeam = "green";
            }

//            //Debug
//            List<Player> bluePlayers = DeathListener.teamsAndPlayers.get("blue");
//            List<Player> redPlayers = DeathListener.teamsAndPlayers.get("red");
//            List<Player> greenPlayers = DeathListener.teamsAndPlayers.get("green");
//            getLogger().info("Blue players: " + bluePlayers.size());
//            getLogger().info("Players: " + bluePlayers);
//            getLogger().info("Red players: " + redPlayers.size());
//            getLogger().info("Players: " + redPlayers);
//            getLogger().info("Green players: " + greenPlayers.size());
//            getLogger().info("Players: " + greenPlayers);

            //Add player to team dictionary
            DeathListener.teamsAndPlayers.get("blue").add(player);

            //Associate player with team
            DeathListener.playersAndTeams.put(player, "blue");

            player.closeInventory();
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }
        if (event.getSlot() == 1) {
            //Red team
            //Vars
            LeatherArmorMeta meta;

            //Make leather ItemStacks
            ItemStack leatherHelmetStack = new ItemStack(Material.LEATHER_HELMET);
            meta = (LeatherArmorMeta) leatherHelmetStack.getItemMeta();
            meta.setColor(Color.RED);
            leatherHelmetStack.setItemMeta(meta);
            ItemStack leatherChestplateStack = new ItemStack(Material.LEATHER_CHESTPLATE);
            meta = (LeatherArmorMeta) leatherChestplateStack.getItemMeta();
            meta.setColor(Color.RED);
            leatherChestplateStack.setItemMeta(meta);
            ItemStack leatherLeggingsStack = new ItemStack(Material.LEATHER_LEGGINGS);
            meta = (LeatherArmorMeta) leatherLeggingsStack.getItemMeta();
            meta.setColor(Color.RED);
            leatherLeggingsStack.setItemMeta(meta);
            ItemStack leatherBootsStack = new ItemStack(Material.LEATHER_BOOTS);
            meta = (LeatherArmorMeta) leatherBootsStack.getItemMeta();
            meta.setColor(Color.RED);
            leatherBootsStack.setItemMeta(meta);

            //Make a leather armor ItemStack array
            ItemStack[] leatherStackArray = {leatherBootsStack, leatherLeggingsStack, leatherChestplateStack, leatherHelmetStack};

            //Put it on player
            player.getInventory().setArmorContents(leatherStackArray);

            //Add 1 count to teamsAndAliveCount for team
            DeathListener.teamsAndAliveCount.put("red", DeathListener.teamsAndAliveCount.get("red") + 1);

            //Remove player from other teams
            String currentOtherTeam = "blue";
            for (int i = 0; i < 2; i++) {
                List<Player> otherTeamList = DeathListener.teamsAndPlayers.get(currentOtherTeam);
                otherTeamList.remove(player);
                DeathListener.teamsAndPlayers.put(currentOtherTeam, otherTeamList);
                currentOtherTeam = "green";
            }

            //Add player to team dictionary
            DeathListener.teamsAndPlayers.get("red").add(player);

            //Associate player with team
            DeathListener.playersAndTeams.put(player, "red");

            player.closeInventory();
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }

        if (event.getSlot() == 2) {
            //Green team
            //Vars
            LeatherArmorMeta meta;

            //Make leather ItemStacks
            ItemStack leatherHelmetStack = new ItemStack(Material.LEATHER_HELMET);
            meta = (LeatherArmorMeta) leatherHelmetStack.getItemMeta();
            meta.setColor(Color.LIME);
            leatherHelmetStack.setItemMeta(meta);
            ItemStack leatherChestplateStack = new ItemStack(Material.LEATHER_CHESTPLATE);
            meta = (LeatherArmorMeta) leatherChestplateStack.getItemMeta();
            meta.setColor(Color.LIME);
            leatherChestplateStack.setItemMeta(meta);
            ItemStack leatherLeggingsStack = new ItemStack(Material.LEATHER_LEGGINGS);
            meta = (LeatherArmorMeta) leatherLeggingsStack.getItemMeta();
            meta.setColor(Color.LIME);
            leatherLeggingsStack.setItemMeta(meta);
            ItemStack leatherBootsStack = new ItemStack(Material.LEATHER_BOOTS);
            meta = (LeatherArmorMeta) leatherBootsStack.getItemMeta();
            meta.setColor(Color.LIME);
            leatherBootsStack.setItemMeta(meta);

            //Make a leather armor ItemStack array
            ItemStack[] leatherStackArray = {leatherBootsStack, leatherLeggingsStack, leatherChestplateStack, leatherHelmetStack};

            //Put it on player
            player.getInventory().setArmorContents(leatherStackArray);

            //Add 1 count to teamsAndAliveCount for team
            DeathListener.teamsAndAliveCount.put("green", DeathListener.teamsAndAliveCount.get("green") + 1);

            //Remove player from other teams
            String currentOtherTeam = "blue";
            for (int i = 0; i < 2; i++) {
                List<Player> otherTeamList = DeathListener.teamsAndPlayers.get(currentOtherTeam);
                otherTeamList.remove(player);
                DeathListener.teamsAndPlayers.put(currentOtherTeam, otherTeamList);
                currentOtherTeam = "red";
            }

            //Add player to team dictionary
            DeathListener.teamsAndPlayers.get("green").add(player);

            //Associate player with team
            DeathListener.playersAndTeams.put(player, "green");

            player.closeInventory();
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }

        //If the barrier in the index 8th slot was clicked
        if (event.getSlot() == 8) {
            //Close inv
            player.closeInventory();
        }

        return;
    }


}
