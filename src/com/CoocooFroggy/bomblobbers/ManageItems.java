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

import static com.CoocooFroggy.bomblobbers.Main.plugin;

public class ManageItems {
    public static Inventory inv;
    static ItemStack tntPlaceholder;

    public static boolean manageItems(Player player) {
        createInv();
        player.openInventory(inv);
        return true;
    }

    public static void createInv() {
        inv = Bukkit.createInventory(null, 9, ChatColor.BOLD + "Starting Items");

        //Get starting items from config
        List<ItemStack> startingItems = (List<ItemStack>) plugin.getConfig().getList("startingItems");

        //If it's empty, make an empty list
        if (startingItems == null) {
            startingItems = new ArrayList<>();
        }

        //TNT placeholder itemstack
        tntPlaceholder = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        //TNT placeholder meta
        ItemMeta tntPlaceholderMeta = tntPlaceholder.getItemMeta();
        //TNT Display name
        tntPlaceholderMeta.setDisplayName(ChatColor.RED + "TNT");
        //TNT placeholder lore
        List<String> tntPlaceholderLore = new ArrayList<>();
        tntPlaceholderLore.add("TNT will always go in the first slot.");
        //Set the lore to the Meta
        tntPlaceholderMeta.setLore(tntPlaceholderLore);
        //Set the Meta to the ItemStack
        tntPlaceholder.setItemMeta(tntPlaceholderMeta);

        //Repeat for every item in list
        for (int i = 0; i < startingItems.size(); i++) {
            inv.setItem(i, startingItems.get(i));
        }

        //Make first slot TNT placeholder
        inv.setItem(0, tntPlaceholder);
    }
}
