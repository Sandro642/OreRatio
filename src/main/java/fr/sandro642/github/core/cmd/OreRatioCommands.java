package fr.sandro642.github.core.cmd;

import fr.sandro642.github.core.misc.Gui;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OreRatioCommands implements TabExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String msg, @NonNull String @NonNull [] args) {
        if (sender instanceof Player player) {

            if(!player.hasPermission("oreratio")) {
                player.sendMessage("You are not allowed to use this command !");
                return false;
            }

            if (msg.equalsIgnoreCase("oreratio")) {
                if (args.length == 0) {
                    player.sendMessage("Usage : '/oreratio <subCommand>'. Type '/oreratio help' for more information");
                } else {
                    switch (args[0].toLowerCase()) {
                        case "help":
                            break;

                        case "setup":
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                            player.sendMessage("You've opened Setup Menu.");
                            Gui.getInstance().setupInventory(player, true);
                            break;

                        case "admin":
                            break;

                        default:
                            player.sendMessage("unknown sub-command : " + args[0]);
                    }
                }
            }

        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NonNull CommandSender sender,
                                                @NonNull Command cmd,
                                                @NonNull String msg,
                                                @NonNull String[] args) {

        final List<String> validArguments = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], List.of("help", "setup", "admin"), validArguments);
            return  validArguments;
        }

        return List.of();
    }
}
