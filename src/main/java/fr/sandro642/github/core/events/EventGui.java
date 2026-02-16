package fr.sandro642.github.core.events;

import fr.sandro642.github.core.misc.Gui;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class EventGui implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        String inventoryTitle = event.getView().getTitle();
        if (!inventoryTitle.contains("[OreRatio]")) {
            return;
        }

        event.setCancelled(true);

        if (itemStack == null || itemStack.getType() == Material.AIR) return;
        if (itemStack.getItemMeta() == null || itemStack.getItemMeta().getDisplayName() == null) return;

        String displayName = itemStack.getItemMeta().getDisplayName();

        if (player.hasPermission("oreratio.use")) {
            if (inventoryTitle.equalsIgnoreCase("[OreRatio] Setup Menu")) {
                if (itemStack.getType() == Material.PLAYER_HEAD && displayName.equalsIgnoreCase("§b➢ Add Ores")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().isAddOres = true;
                    Gui.getInstance().setupInventory(player, false);
                    Gui.getInstance().addOresMenu(player, true, Gui.getInstance().getCurrentPage(player));
                }
                else if (itemStack.getType() == Material.PLAYER_HEAD && displayName.equalsIgnoreCase("§b➢ Remove Ores")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().isAddOres = false;
                    Gui.getInstance().setupInventory(player, false);
                    Gui.getInstance().removeOresMenu(player, true, Gui.getInstance().getCurrentPage(player));
                }
                else if (itemStack.getType() == Material.BARRIER && displayName.equalsIgnoreCase("➢ Exit")) {
                    Gui.getInstance().setupInventory(player, false);
                }
                return;
            }

            if (inventoryTitle.equalsIgnoreCase("[OreRatio] AddOres Menu")) {
                if (itemStack.getType() == Material.BARRIER && displayName.equalsIgnoreCase("➢ Main Page")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().addOresMenu(player, false);
                    Gui.getInstance().setupInventory(player, true);
                }

                else if (displayName.equalsIgnoreCase("§f➢ §cBefore")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().previousPage(player);
                }

                else if (displayName.equalsIgnoreCase("§f➢ §cAfter")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().nextPage(player);
                }

                else if (displayName.equalsIgnoreCase("§f➢ §cReturn")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().addOresMenu(player, false);
                    Gui.getInstance().addOresMenu(player, true, 1);
                }

                else if (displayName.equalsIgnoreCase("§f➢ Select All")) {
                    if (!Gui.getInstance().listOfBlocks.equals(Gui.getInstance().getItems())) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        Gui.getInstance().listOfBlocks.addAll(Gui.getInstance().getItems());
                        Gui.getInstance().addOresMenu(player, false);
                        Gui.getInstance().addOresMenu(player, true, Gui.getInstance().getCurrentPage(player));
                    } else {
                        player.sendMessage("You can't select more than once time all items");
                    }
                }

                else if (itemStack.getType() != Material.WHITE_STAINED_GLASS_PANE && itemStack.getType() != Material.BLACK_STAINED_GLASS_PANE  && itemStack.getType() != Material.PLAYER_HEAD && itemStack.getType() != Material.AIR) {
                    ItemStack cleanItem = new ItemStack(itemStack.getType());
                    boolean itemExists = Gui.getInstance().listOfBlocks.stream()
                            .anyMatch(item -> item.getType() == cleanItem.getType());

                    if (!itemExists) {
                        Gui.getInstance().listOfBlocks.add(cleanItem);
                        Gui.getInstance().addOresMenu(player, false);
                        Gui.getInstance().addOresMenu(player, true, Gui.getInstance().getCurrentPage(player));
                    } else {
                        player.sendMessage("You can't select more than once time the same item");
                    }
                }
                return;
            }

            if (inventoryTitle.equalsIgnoreCase("[OreRatio] RemoveOres Menu")) {
                if (itemStack.getType() == Material.BARRIER && displayName.equalsIgnoreCase("➢ Main Page")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().removeOresMenu(player, false);
                    Gui.getInstance().setupInventory(player, true);
                }

                else if (displayName.equalsIgnoreCase("§f➢ §cBefore")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().previousPage(player);
                }

                else if (displayName.equalsIgnoreCase("§f➢ §cAfter")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().nextPage(player);
                }

                else if (displayName.equalsIgnoreCase("§f➢ §cReturn")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().removeOresMenu(player, false);
                    Gui.getInstance().removeOresMenu(player, true, 1);
                }

                else if (displayName.equalsIgnoreCase("§f➢ Deselect All")) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    Gui.getInstance().listOfBlocks.clear();
                    Gui.getInstance().removeOresMenu(player, false);
                    Gui.getInstance().removeOresMenu(player, true, Gui.getInstance().getCurrentPage(player));
                }

                else if (itemStack.getType() != Material.WHITE_STAINED_GLASS_PANE && itemStack.getType() != Material.BLACK_STAINED_GLASS_PANE && itemStack.getType() != Material.PLAYER_HEAD && itemStack.getType() != Material.AIR) {
                    ItemStack cleanItem = new ItemStack(itemStack.getType());
                    Gui.getInstance().listOfBlocks.removeIf(item -> item.getType() == cleanItem.getType());
                    Gui.getInstance().removeOresMenu(player, false);
                    Gui.getInstance().removeOresMenu(player, true, Gui.getInstance().getCurrentPage(player));
                }
                return;
            }

        } else {
            player.sendMessage("You are not allowed to use the menu");
        }
    }
}
