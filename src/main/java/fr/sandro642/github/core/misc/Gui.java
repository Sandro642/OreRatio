package fr.sandro642.github.core.misc;

import fr.sandro642.github.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Gui {

    private static final Gui INSTANCE = new Gui();

    private final Map<Player, Integer> pagesSwap = new HashMap<>();

    public ArrayList<ItemStack> listOfBlocks = new ArrayList<>();

    public boolean isAddOres;

    public static ItemStack getCustomHead(String value) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta == null) return head;

        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        try {
            String decoded = new String(Base64.getDecoder().decode(value));
            String urlString = decoded.substring(decoded.indexOf("http"), decoded.lastIndexOf("\""));

            textures.setSkin(new URL(urlString));
            profile.setTextures(textures);

            meta.setOwnerProfile(profile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        head.setItemMeta(meta);
        return head;
    }

    public void setupInventory(Player player, boolean status) {
        Inventory setupInventory = Bukkit.createInventory(null, 9, "[OreRatio] Setup Menu");

        ItemStack glasswhite = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta glasswhitemeta = glasswhite.getItemMeta();
        glasswhitemeta.setDisplayName(" ");
        glasswhite.setItemMeta(glasswhitemeta);

        ItemStack glassblack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassblackmeta = glassblack.getItemMeta();
        glassblackmeta.setDisplayName(" ");
        glassblack.setItemMeta(glasswhitemeta);

        ItemStack settings = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWMyZmYyNDRkZmM5ZGQzYTJjZWY2MzExMmU3NTAyZGM2MzY3YjBkMDIxMzI5NTAzNDdiMmI0NzlhNzIzNjZkZCJ9fX0=");
        SkullMeta settingsMeta = (SkullMeta) settings.getItemMeta();
        settingsMeta.setDisplayName("➢ Settings");
        List<String> loreSettings = List.of("", "You can enable and disable", "functionalities");
        settingsMeta.setLore(loreSettings);
        settings.setItemMeta(settingsMeta);

        ItemStack exitItem = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exitItem.getItemMeta();
        exitMeta.setDisplayName("➢ Exit");
        exitItem.setItemMeta(exitMeta);

        String headBookKnowledge = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBjOTE2MTgwZTMxMmJmNzkzZTYwZjE4MThlMWUxZDQzNmEyZDU5ZDNhZDdmYzNlN2E1ZDQ0YjQyYjg5Nzg2NCJ9fX0=";

        ItemStack addOres = getCustomHead(headBookKnowledge);
        SkullMeta addOresMeta = (SkullMeta) addOres.getItemMeta();

        if (addOresMeta != null) {
            addOresMeta.setDisplayName("§b➢ Add Ores");

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§7Click on it to open the menu");
            lore.add("§7You can add ores");
            addOresMeta.setLore(lore);

            addOresMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            addOres.setItemMeta(addOresMeta);
        }

        String headBookWritten = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNhNjM4YTIwZTE1MmE4ZjY5NTg2NDA5NDhlOWZiZmEzOWFhNTMxMDA1Yjc3MDA1NWY5ODIzNjMyMmIwMDgifX19";

        ItemStack removeOres = getCustomHead(headBookWritten);
        SkullMeta removeOresMeta = (SkullMeta) removeOres.getItemMeta();

        if (removeOresMeta != null) {
            removeOresMeta.setDisplayName("§b➢ Remove Ores");

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§7Click on it to open the menu");
            lore.add("§7You can remove ores");
            removeOresMeta.setLore(lore);

            removeOresMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            removeOres.setItemMeta(removeOresMeta);
        }

        setupInventory.setItem(0, glasswhite);
        setupInventory.setItem(1, settings);
        setupInventory.setItem(2, glassblack);
        setupInventory.setItem(3, addOres);
        setupInventory.setItem(4, glasswhite);
        setupInventory.setItem(5, removeOres);
        setupInventory.setItem(6, glassblack);
        setupInventory.setItem(7, exitItem);
        setupInventory.setItem(8, glasswhite);

        if (status) {
            player.openInventory(setupInventory);
        } else {
            player.closeInventory();
        }
    }

    public Inventory addOresMenu(Player player, boolean status) {
        return addOresMenu(player, status, 1);
    }

    public Inventory addOresMenu(Player player, boolean status, int currentPage) {
        Inventory addOresInventory = Bukkit.createInventory(null, 54, "[OreRatio] AddOres Menu");

        ItemStack information = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTFjNDEzYThkMjZkNDY1NDFmYmVhZTRkM2U4MzBiYzRmYjE3YWIwNDAzODZkMTJjYzQzMDE0ZGE4N2VkOGFhZiJ9fX0=");
        ItemMeta infoMeta = information.getItemMeta();
        infoMeta.setDisplayName("You are on page(s) : " + getCurrentPage(player) + " / " + getTotalPages());
        information.setItemMeta(infoMeta);

        ItemStack selectAll = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZhMWM2YzdlYWQ3NWEwNDU4NTM5NWY2MzEzNWRjOTZmYTA3OGZiOTIwNDg0Njk5ZWY4ZTU2NGUxNDJkNjRjYiJ9fX0=");
        ItemMeta selectAllMeta = selectAll.getItemMeta();
        selectAllMeta.setDisplayName("§f➢ Select All");
        selectAll.setItemMeta(selectAllMeta);

        ItemStack glasswhite = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta glasswhitemeta = glasswhite.getItemMeta();
        glasswhitemeta.setDisplayName(" ");
        glasswhite.setItemMeta(glasswhitemeta);

        ItemStack glassblack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassblackmeta = glassblack.getItemMeta();
        glassblackmeta.setDisplayName(" ");
        glassblack.setItemMeta(glasswhitemeta);

        ItemStack exitItem = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exitItem.getItemMeta();
        exitMeta.setDisplayName("➢ Main Page");
        exitItem.setItemMeta(exitMeta);

        ItemStack returnleft = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZhNGM4MjcxMDgzNzQ4MGRmNTc1Y2EwZDY0Y2VmMmZjZGFkYWVjZTcwOTFiNzA3NmI5MjNjNjdlNWY0ZTg0OSJ9fX0=");
        ItemMeta returnleftmeta = returnleft.getItemMeta();
        returnleftmeta.setDisplayName("§f➢ §cBefore");
        returnleft.setItemMeta(returnleftmeta);


        ItemStack returnright = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjM5NTExOWRkNTIwMWEyNDJiODZiNDg2NmQ2ZjA0NTQxYjAwYjkyZWJkZDU3Y2UyNzkxOWZiNWYxMDJhNmRkZCJ9fX0=");
        ItemMeta returnrightmeta = returnright.getItemMeta();
        returnrightmeta.setDisplayName("§f➢ §cAfter");
        returnright.setItemMeta(returnrightmeta);


        ItemStack returnstaff = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZkMjQwMDAwMmFkOWZiYmJkMDA2Njk0MWViNWIxYTM4NGFiOWIwZTQ4YTE3OGVlOTZlNGQxMjlhNTIwODY1NCJ9fX0=");
        ItemMeta returnstaffmeta = returnstaff.getItemMeta();
        returnstaffmeta.setDisplayName("§f➢ §cReturn");
        returnstaff.setItemMeta(returnstaffmeta);

        List<ItemStack> items = getItems();

        List<ItemStack> displayItems = items.stream().skip(21L * (currentPage - 1)).limit(21).collect(Collectors.toList());
        Logger.getInstance().INFO(String.valueOf(displayItems.size()));

        int slot = 10;
        int check = 0;
        for(int i = 0; i < 21; i++) {
            if (check == 7) {
                slot+= 2;
                check = 0;
            }

            ItemStack item;

            if (i > displayItems.size() - 1) {
                item = new ItemStack(Material.AIR);
            } else {
                item = displayItems.get(i);
                ItemMeta itemMeta = item.getItemMeta();

                if (itemMeta != null) {
                    boolean isSelected = listOfBlocks.stream()
                            .anyMatch(block -> block.getType() == item.getType());

                    String color = isSelected ? "§e" : "§f";
                    itemMeta.setDisplayName(color + item.getType().name());

                    if (!isSelected) {
                        List<String> lore = new ArrayList<>();
                        lore.add("");
                        lore.add("§7Click on it to add ore");
                        lore.add("§7in the list of blocks scanned");
                        itemMeta.setLore(lore);
                    } else {
                        List<String> lore = new ArrayList<>();
                        lore.add("");
                        lore.add("§7Selected !");
                        itemMeta.setLore(lore);
                    }

                    item.setItemMeta(itemMeta);
                }
            }

            addOresInventory.setItem(slot, item);

            slot++;
            check++;
        }

        // Glass
        addOresInventory.setItem(0, glasswhite);
        addOresInventory.setItem(1, glassblack);
        addOresInventory.setItem(2, glasswhite);
        addOresInventory.setItem(3, glassblack);
        addOresInventory.setItem(5, glassblack);
        addOresInventory.setItem(6, glasswhite);
        addOresInventory.setItem(7, glassblack);
        addOresInventory.setItem(8, glasswhite);
        addOresInventory.setItem(9, glassblack);
        addOresInventory.setItem(17, glassblack);
        addOresInventory.setItem(18, glasswhite);
        addOresInventory.setItem(26, glasswhite);
        addOresInventory.setItem(27, glassblack);
        addOresInventory.setItem(35, glassblack);
        addOresInventory.setItem(36, glasswhite);
        addOresInventory.setItem(37, glassblack);
        addOresInventory.setItem(38, glasswhite);
        addOresInventory.setItem(39, glassblack);
        addOresInventory.setItem(40, glasswhite);
        addOresInventory.setItem(41, glassblack);
        addOresInventory.setItem(42, glasswhite);
        addOresInventory.setItem(43, glassblack);
        addOresInventory.setItem(44, glasswhite);
        addOresInventory.setItem(45, selectAll);
        addOresInventory.setItem(46, glasswhite);
        addOresInventory.setItem(48, glasswhite);
        addOresInventory.setItem(50, glasswhite);


        addOresInventory.setItem(4, information);

        if (!(getTotalPages() <= 1)) {
            addOresInventory.setItem(47, returnleft);
            addOresInventory.setItem(49, returnstaff);
            addOresInventory.setItem(51, returnright);
        } else {
            addOresInventory.setItem(47, glassblack);
            addOresInventory.setItem(49, glassblack);
            addOresInventory.setItem(51, glassblack);
        }

        addOresInventory.setItem(52, glasswhite);
        addOresInventory.setItem(53, exitItem);

        if (status) {
            player.openInventory(addOresInventory);
        } else {
            player.closeInventory();
        }

        player.updateInventory();
        return addOresInventory;
    }

    public Inventory removeOresMenu(Player player, boolean status) {
        return removeOresMenu(player, status, 1);
    }

    public Inventory removeOresMenu(Player player, boolean status, int currentPage) {
        Inventory removeOresInventory = Bukkit.createInventory(null, 54, "[OreRatio] RemoveOres Menu");

        ItemStack information = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTFjNDEzYThkMjZkNDY1NDFmYmVhZTRkM2U4MzBiYzRmYjE3YWIwNDAzODZkMTJjYzQzMDE0ZGE4N2VkOGFhZiJ9fX0=");
        ItemMeta infoMeta = information.getItemMeta();
        infoMeta.setDisplayName("You are on page(s) : " + getCurrentPage(player) + " / " + getTotalPages());
        information.setItemMeta(infoMeta);

        ItemStack deselectAll = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQwMDJkYTU4NzJmNDZhY2FlNDI3Y2U3N2I0NzQ4MjQ3ZDJkYzBkOWYyMzdhN2MwY2MyYTdmZDEwY2Q1ZWVjNSJ9fX0=");
        ItemMeta deselectAllMeta = deselectAll.getItemMeta();
        deselectAllMeta.setDisplayName("§f➢ Deselect All");
        deselectAll.setItemMeta(deselectAllMeta);

        ItemStack glasswhite = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta glasswhitemeta = glasswhite.getItemMeta();
        glasswhitemeta.setDisplayName(" ");
        glasswhite.setItemMeta(glasswhitemeta);

        ItemStack glassblack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassblackmeta = glassblack.getItemMeta();
        glassblackmeta.setDisplayName(" ");
        glassblack.setItemMeta(glasswhitemeta);

        ItemStack exitItem = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exitItem.getItemMeta();
        exitMeta.setDisplayName("➢ Main Page");
        exitItem.setItemMeta(exitMeta);

        ItemStack returnleft = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZhNGM4MjcxMDgzNzQ4MGRmNTc1Y2EwZDY0Y2VmMmZjZGFkYWVjZTcwOTFiNzA3NmI5MjNjNjdlNWY0ZTg0OSJ9fX0=");
        ItemMeta returnleftmeta = returnleft.getItemMeta();
        returnleftmeta.setDisplayName("§f➢ §cBefore");
        returnleft.setItemMeta(returnleftmeta);


        ItemStack returnright = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjM5NTExOWRkNTIwMWEyNDJiODZiNDg2NmQ2ZjA0NTQxYjAwYjkyZWJkZDU3Y2UyNzkxOWZiNWYxMDJhNmRkZCJ9fX0=");
        ItemMeta returnrightmeta = returnright.getItemMeta();
        returnrightmeta.setDisplayName("§f➢ §cAfter");
        returnright.setItemMeta(returnrightmeta);


        ItemStack returnstaff = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZkMjQwMDAwMmFkOWZiYmJkMDA2Njk0MWViNWIxYTM4NGFiOWIwZTQ4YTE3OGVlOTZlNGQxMjlhNTIwODY1NCJ9fX0=");
        ItemMeta returnstaffmeta = returnstaff.getItemMeta();
        returnstaffmeta.setDisplayName("§f➢ §cReturn");
        returnstaff.setItemMeta(returnstaffmeta);

        List<ItemStack> displayItems = listOfBlocks.stream().skip(21L * (currentPage - 1)).limit(21).collect(Collectors.toList());
        Logger.getInstance().INFO(String.valueOf(displayItems.size()));

        int slot = 10;
        int check = 0;
        for(int i = 0; i < 21; i++) {
            if (check == 7) {
                slot+= 2;
                check = 0;
            }

            ItemStack item;

            if (i > displayItems.size() - 1) {
                item = new ItemStack(Material.AIR);
            } else {
                item = displayItems.get(i);
                ItemMeta itemMeta = item.getItemMeta();

                if (itemMeta != null) {
                    boolean isSelected = listOfBlocks.stream()
                            .anyMatch(block -> block.getType() == item.getType());

                    String color = isSelected ? "§e" : "§f";
                    itemMeta.setDisplayName(color + item.getType().name());
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add("§7Click on it to remove ore");
                    itemMeta.setLore(lore);

                    item.setItemMeta(itemMeta);
                }
            }

            removeOresInventory.setItem(slot, item);

            slot++;
            check++;
        }

        // Glass
        removeOresInventory.setItem(0, glasswhite);
        removeOresInventory.setItem(1, glassblack);
        removeOresInventory.setItem(2, glasswhite);
        removeOresInventory.setItem(3, glassblack);
        removeOresInventory.setItem(5, glassblack);
        removeOresInventory.setItem(6, glasswhite);
        removeOresInventory.setItem(7, glassblack);
        removeOresInventory.setItem(8, glasswhite);
        removeOresInventory.setItem(9, glassblack);
        removeOresInventory.setItem(17, glassblack);
        removeOresInventory.setItem(18, glasswhite);
        removeOresInventory.setItem(26, glasswhite);
        removeOresInventory.setItem(27, glassblack);
        removeOresInventory.setItem(35, glassblack);
        removeOresInventory.setItem(36, glasswhite);
        removeOresInventory.setItem(37, glassblack);
        removeOresInventory.setItem(38, glasswhite);
        removeOresInventory.setItem(39, glassblack);
        removeOresInventory.setItem(40, glasswhite);
        removeOresInventory.setItem(41, glassblack);
        removeOresInventory.setItem(42, glasswhite);
        removeOresInventory.setItem(43, glassblack);
        removeOresInventory.setItem(44, glasswhite);
        removeOresInventory.setItem(45, deselectAll);
        removeOresInventory.setItem(46, glasswhite);
        removeOresInventory.setItem(48, glasswhite);
        removeOresInventory.setItem(50, glasswhite);


        removeOresInventory.setItem(4, information);

        if (!(getTotalPages() <= 1)) {
            removeOresInventory.setItem(47, returnleft);
            removeOresInventory.setItem(49, returnstaff);
            removeOresInventory.setItem(51, returnright);
        } else {
            removeOresInventory.setItem(47, glassblack);
            removeOresInventory.setItem(49, glassblack);
            removeOresInventory.setItem(51, glassblack);
        }

        removeOresInventory.setItem(52, glasswhite);
        removeOresInventory.setItem(53, exitItem);

        if (status) {
            player.openInventory(removeOresInventory);
        } else {
            player.closeInventory();
        }
        return removeOresInventory;
    }

    public List<ItemStack> getItems() {
        List<ItemStack> oreItems = new ArrayList<>();
        Material[] oreMaterials = {
                Material.DIAMOND_ORE,
                Material.GOLD_ORE,
                Material.COAL_ORE,
                Material.COPPER_ORE,
                Material.IRON_ORE,
                Material.LAPIS_ORE,
                Material.NETHER_QUARTZ_ORE,
                Material.REDSTONE_ORE,
                //
                Material.ACACIA_BOAT,
                Material.ACACIA_CHEST_BOAT,
                Material.EMERALD_ORE,
                Material.DEEPSLATE_DIAMOND_ORE,
                Material.DEEPSLATE_GOLD_ORE,
                Material.DEEPSLATE_COAL_ORE,
                Material.DEEPSLATE_COPPER_ORE,
                Material.DEEPSLATE_IRON_ORE,
                Material.DEEPSLATE_LAPIS_ORE,
                Material.DEEPSLATE_REDSTONE_ORE,
                Material.ANCIENT_DEBRIS,
                Material.NETHER_GOLD_ORE,
                Material.AMETHYST_CLUSTER,
                Material.BIRCH_BOAT,
                Material.CHERRY_BOAT,
                Material.DARK_OAK_BOAT,
                Material.JUNGLE_BOAT,
                Material.MANGROVE_BOAT,
                Material.OAK_BOAT,
                Material.SPRUCE_BOAT,
                Material.BAMBOO_BLOCK,
                Material.WARPED_NYLIUM,
                Material.CRIMSON_NYLIUM,
                Material.BLACKSTONE,
                Material.BASALT
        };

        for (Material material : oreMaterials) {
            oreItems.add(new ItemStack(material));
        }

        return oreItems;
    }

    public int getPagesNumber(List<ItemStack> items) {
        int rest = items.size() % 21;
        int pages = (items.size() - rest) / 21;

        if (rest != 0) pages++;

        return pages;
    }

    public void nextPage(Player player) {
        int currentPage = pagesSwap.getOrDefault(player, 1);

        List<ItemStack> itemsToCheck = isAddOres ? getItems() : listOfBlocks;
        if (currentPage + 1 > getPagesNumber(itemsToCheck)) return;

        currentPage++;
        pagesSwap.put(player, currentPage);

        if (isAddOres) {
            addOresMenu(player, true, currentPage);
        } else {
            removeOresMenu(player, true, currentPage);
        }
    }

    public void previousPage(Player player) {
        int currentPage = pagesSwap.getOrDefault(player, 1);

        if (currentPage - 1 < 1) return;

        currentPage--;
        pagesSwap.put(player, currentPage);

        if (isAddOres) {
            addOresMenu(player, true, currentPage);
        } else {
            removeOresMenu(player, true, currentPage);
        }
    }

    public int getCurrentPage(Player player) {
        int currentPageNum = pagesSwap.getOrDefault(player, 1);
        int totalPages = Gui.getInstance().getTotalPages();

        if (totalPages == 0 || currentPageNum > totalPages) {
            return 1;
        }

        return currentPageNum;
    }

    public int getTotalPages() {
        List<ItemStack> itemsToCheck = isAddOres ? getItems() : listOfBlocks;
        return getPagesNumber(itemsToCheck);
    }

    public static Gui getInstance() {
        return INSTANCE;
    }
}
