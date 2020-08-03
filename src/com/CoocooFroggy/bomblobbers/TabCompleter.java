package com.CoocooFroggy.bomblobbers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("bomblobbers") || label.equalsIgnoreCase("bl")) {
            if (args.length > 0) {
                return Arrays.asList("enable", "disable", "start", "stop", "changeteam");
            }
        }
        return null;
    }
}
