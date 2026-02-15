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

    private static final Map<Player, Integer> pagesSwap = new HashMap<>();

    public static ItemStack getCustomHead(String value) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta == null) return head;

        // 1. Créer un profil de joueur factice
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        try {
            // 2. Décoder la valeur Base64 pour obtenir l'URL de la texture
            String decoded = new String(Base64.getDecoder().decode(value));
            // On extrait l'URL du JSON (méthode rapide)
            String urlString = decoded.substring(decoded.indexOf("http"), decoded.lastIndexOf("\""));

            textures.setSkin(new URL(urlString));
            profile.setTextures(textures);

            // 3. Appliquer le profil au Meta
            meta.setOwnerProfile(profile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        head.setItemMeta(meta);
        return head;
    }

    public void setupInventory(Player player, boolean status) {
        Inventory setupInventory = Bukkit.createInventory(null, 9, "[OreRatio] ➢ Setup Menu");

        ItemStack glasswhite = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta glasswhitemeta = glasswhite.getItemMeta();
        glasswhitemeta.setDisplayName(" ");
        glasswhite.setItemMeta(glasswhitemeta);

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
        setupInventory.setItem(3, addOres);
        setupInventory.setItem(5, removeOres);
        setupInventory.setItem(8, exitItem);

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
        Inventory addOresInventory = Bukkit.createInventory(null, 54, "[OreRatio] ➢ AddOres Menu");

        ItemStack exitItem = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exitItem.getItemMeta();
        exitMeta.setDisplayName("➢ Exit");
        exitItem.setItemMeta(exitMeta);

        ItemStack returnleft = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZhNGM4MjcxMDgzNzQ4MGRmNTc1Y2EwZDY0Y2VmMmZjZGFkYWVjZTcwOTFiNzA3NmI5MjNjNjdlNWY0ZTg0OSJ9fX0=");
        ItemMeta returnleftmeta = returnleft.getItemMeta();
        returnleftmeta.setDisplayName("§f➢ §cAvant");
        returnleft.setItemMeta(returnleftmeta);


        ItemStack returnright = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjM5NTExOWRkNTIwMWEyNDJiODZiNDg2NmQ2ZjA0NTQxYjAwYjkyZWJkZDU3Y2UyNzkxOWZiNWYxMDJhNmRkZCJ9fX0=");
        ItemMeta returnrightmeta = returnright.getItemMeta();
        returnrightmeta.setDisplayName("§f➢ §cAprès");
        returnright.setItemMeta(returnrightmeta);


        ItemStack returnstaff = getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZkMjQwMDAwMmFkOWZiYmJkMDA2Njk0MWViNWIxYTM4NGFiOWIwZTQ4YTE3OGVlOTZlNGQxMjlhNTIwODY1NCJ9fX0=");
        ItemMeta returnstaffmeta = returnstaff.getItemMeta();
        returnstaffmeta.setDisplayName("§f➢ §cRetour");
        returnstaff.setItemMeta(returnstaffmeta);

        List<ItemStack> items = getItems();

        List<ItemStack> displayItems = items.stream().skip(10L * (currentPage - 1)).limit(10).collect(Collectors.toList());
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
            }

            addOresInventory.setItem(slot, item);

            slot++;
            check++;
        }

        addOresInventory.setItem(47, returnleft);
        addOresInventory.setItem(51, returnright);
        addOresInventory.setItem(53, exitItem);

        if (status) {
            player.openInventory(addOresInventory);
        } else {
            player.closeInventory();
        }

        player.updateInventory();
        return addOresInventory;
    }

    public void removeOresMenu(Player player, boolean status) {
        Inventory removeOresInventory = Bukkit.createInventory(null, 54, "[OreRatio] ➢ RemoveOres Menu");

        ItemStack exitItem = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exitItem.getItemMeta();
        exitMeta.setDisplayName("➢ Exit");
        exitItem.setItemMeta(exitMeta);

        removeOresInventory.setItem(53, exitItem);

        if (status) {
            player.openInventory(removeOresInventory);
        } else {
            player.closeInventory();
        }
    }

    public static Gui getInstance() {
        return INSTANCE;
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
                Material.REDSTONE_ORE
        };

        for (Material material : oreMaterials) {
            oreItems.add(new ItemStack(material));
        }

        return oreItems;
    }

    public int getPagesNumber(List<ItemStack> items) {
        int rest = items.size() % 10;
        int pages = (items.size() - rest) / 10;

        if (rest != 0) pages++;

        return pages;
    }

    public void nextPage(Player player) {
        int currentPage;

        if(!pagesSwap.containsKey(player)) {
            pagesSwap.put(player, 1);
            currentPage = 1;
        } else {
            int before = pagesSwap.get(player);

            if (before + 1 > getPagesNumber(getItems())) return;

            pagesSwap.put(player, before + 1);

            currentPage = pagesSwap.get(player);
        }
        Logger.getInstance().INFO(String.valueOf(currentPage));

        addOresMenu(player, true, currentPage);
    }

    public void previousPage(Player player) {
        int currentPage;

        if(!pagesSwap.containsKey(player)) {
            pagesSwap.put(player, 1);
            currentPage = 1;
        } else {
            int before = pagesSwap.get(player);

            if (before - 1 > 1) return;

            pagesSwap.put(player, before - 1);

            currentPage = pagesSwap.get(player);
        }
        Logger.getInstance().INFO(String.valueOf(currentPage));

        addOresMenu(player, true, currentPage);
    }

}
