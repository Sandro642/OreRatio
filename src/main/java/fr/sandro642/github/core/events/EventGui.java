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

    @EventHandler
    public void onIventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        InventoryAction action = event.getAction();

        event.setCancelled(true);

        if (itemStack == null) return;

        if (player.hasPermission("oreratio.use")) {
            if (itemStack.getType() == Material.PLAYER_HEAD && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§b➢ Add Ores")) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                Gui.getInstance().isAddOres = true;

                Gui.getInstance().setupInventory(player, false);
                Gui.getInstance().addOresMenu(player, true);
            }

            else if (itemStack.getType() == Material.PLAYER_HEAD && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§b➢ Remove Ores")) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                Gui.getInstance().isAddOres = false;

                Gui.getInstance().setupInventory(player, false);
                Gui.getInstance().removeOresMenu(player, true);
            }

            else if (itemStack.getType() == Material.BARRIER && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("➢ Exit")) {
                Gui.getInstance().setupInventory(player, false);
            }

            else if (itemStack.getType() == Material.BARRIER && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("➢ Main Page")) {
                if (Gui.getInstance().isAddOres) {
                    Gui.getInstance().addOresMenu(player, false);
                    Gui.getInstance().setupInventory(player, true);
                } else {
                    Gui.getInstance().removeOresMenu(player, false);
                    Gui.getInstance().setupInventory(player, true);
                }
            }

            else if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§f➢ §cBefore")) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                Gui.getInstance().previousPage(player);
                event.setCancelled(true);
            }

            else if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§f➢ §cAfter")) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                Gui.getInstance().nextPage(player);
                event.setCancelled(true);
            }

            else if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§f➢ §cReturn")) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                if (Gui.getInstance().isAddOres) {
                    Gui.getInstance().addOresMenu(player, false);
                    Gui.getInstance().addOresMenu(player, true, 1);
                } else {
                    Gui.getInstance().removeOresMenu(player, false);
                    Gui.getInstance().removeOresMenu(player, true, 1);
                }
            }

            else {
                Material clickedMaterial = itemStack.getType();

                if (clickedMaterial != null && clickedMaterial != Material.AIR) {
                    event.setCancelled(true);
                    ItemStack cleanItem = new ItemStack(clickedMaterial);

                    if (Gui.getInstance().isAddOres) {
                        boolean itemExists = Gui.getInstance().listOfBlocks.stream()
                                .anyMatch(item -> item.getType() == cleanItem.getType());

                        if (!itemExists) {
                            Gui.getInstance().listOfBlocks.add(cleanItem);
                            Gui.getInstance().addOresMenu(player, false);
                            Gui.getInstance().addOresMenu(player, true, Gui.getInstance().getCurrentPage(player));
                        } else {
                            player.sendMessage("You can't select more than once time the same item");
                        }

                    } else {
                        Gui.getInstance().listOfBlocks.removeIf(item -> item.getType() == cleanItem.getType());
                        Gui.getInstance().removeOresMenu(player, false);
                        Gui.getInstance().removeOresMenu(player, true, Gui.getInstance().getCurrentPage(player));
                    }
                }
            }

        } else {
            player.sendMessage("You are not allowed to use the menu");
        }
    }
}
