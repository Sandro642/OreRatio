package fr.sandro642.github.core.events;

import fr.sandro642.github.core.misc.Gui;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class EventGui implements Listener {

    private Gui gui = new Gui();

    String headBookKnowledge = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBjOTE2MTgwZTMxMmJmNzkzZTYwZjE4MThlMWUxZDQzNmEyZDU5ZDNhZDdmYzNlN2E1ZDQ0YjQyYjg5Nzg2NCJ9fX0=";

    @EventHandler
    public void onIventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        InventoryAction action = event.getAction();

        if (itemStack == null) return;

        if (player.hasPermission("oreratio.use")) {
            if (itemStack.getType() == Material.PLAYER_HEAD && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§b➢ Add Ores")) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                Gui.getInstance().setupInventory(player, false);
                Gui.getInstance().addOresMenu(player, true);
            }

            else if (itemStack.getType() == Material.PLAYER_HEAD && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§b➢ Remove Ores")) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                Gui.getInstance().setupInventory(player, false);
                Gui.getInstance().removeOresMenu(player, true);
            }

            else if (itemStack.getType() == Material.BARRIER && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("➢ Exit")) {
                Gui.getInstance().setupInventory(player, false);
            }

            else if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§f➢ §cAvant")) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                gui.previousPage(player);
                event.setCancelled(true);
            }

            else if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§f➢ §cAprès")) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                gui.nextPage(player);
                event.setCancelled(true);
            }

        } else {
            player.sendMessage("You are not allowed to use the menu");
        }
    }
}
