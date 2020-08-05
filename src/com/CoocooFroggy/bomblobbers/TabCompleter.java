package com.CoocooFroggy.bomblobbers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("bomblobbers") || label.equalsIgnoreCase("bl")) {
            if (args.length == 1) {
                return Arrays.asList("enable", "disable", "start", "stop", "changeteam", "config");
            } else if (args.length == 2) {
                if (args[0].contains("config")) {
                    return Arrays.asList("velocity", "givetime", "cooldown");
                }
            }
        }
        return null;
    }
}
