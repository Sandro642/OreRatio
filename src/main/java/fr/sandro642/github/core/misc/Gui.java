package fr.sandro642.github.core.misc;

import com.google.j2objc.annotations.Property;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class Gui {

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

}
