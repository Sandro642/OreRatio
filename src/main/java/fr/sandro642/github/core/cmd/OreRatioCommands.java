package fr.sandro642.github.core.cmd;

import fr.sandro642.github.core.misc.Gui;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

public class OreRatioCommands implements CommandExecutor {
    // Gui Class Instance
    private Gui gui = new Gui();

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
                            gui.setupInventory(player, true);
                            break;

                        case "admin":
                            break;

                        default:
                            player.sendMessage("Sous-commande inconnue : " + args[0]);
                    }
                }
            }

        }

        return false;
    }
}
