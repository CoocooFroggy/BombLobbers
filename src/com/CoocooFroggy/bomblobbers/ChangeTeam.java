package com.CoocooFroggy.bomblobbers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChangeTeam {
    public static Inventory inv;

    public static boolean changeTeam(Player player) {
        createInv();
        player.openInventory(inv);
        return true;
    }

    public static void createInv() {
        inv = Bukkit.createInventory(null, 9, ChatColor.BOLD + "Select a Team");

        //Vars
        ItemStack item = new ItemStack(Material.GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        //Blue Team
        item.setType(Material.BLUE_WOOL);
        meta.setDisplayName(ChatColor.DARK_BLUE + "Blue Team");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Click to join team!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(0, item);

        //Red Team
        item.setType(Material.RED_WOOL);
        meta.setDisplayName(ChatColor.DARK_RED + "Red Team");
        lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Click to join team!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(1, item);

        //Green Team
        item.setType(Material.LIME_WOOL);
        meta.setDisplayName(ChatColor.GREEN + "Green Team");
        lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Click to join team!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(2, item);

        //Close button
        item.setType(Material.BARRIER);
        meta.setDisplayName(ChatColor.RED + "Close Menu");
        lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Click to close the change team menu");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(8, item);
    }
}
