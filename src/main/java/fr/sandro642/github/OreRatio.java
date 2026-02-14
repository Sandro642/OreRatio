package fr.sandro642.github;

import fr.sandro642.github.core.cmd.OreRatioCommands;
import fr.sandro642.github.db.DatabaseService;
import fr.sandro642.github.update.CheckUpdates;
import fr.sandro642.github.update.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class OreRatio extends JavaPlugin {

    // Logger constant
    private final String OreRatio = "\u001B[94m[OreRatio] \u001B[0m";

    // CheckUpdates instance to check for updates
    private final CheckUpdates checkUpdates = new CheckUpdates();

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(OreRatio + "OreRatio has been enabled!");
        Bukkit.getLogger().info(OreRatio + "Version : " + Version.VERSION);
        Bukkit.getLogger().info(OreRatio + checkUpdates.isLatestVersion());

        // Loaders
        initDB();
        initCommands();
    }

    private void initDB() {
        try {
            Bukkit.getLogger().info(OreRatio + "Initializing the database...");
            DatabaseService databaseService = DatabaseService.getInstance();
        } catch (Exception e) {
            Bukkit.getLogger().severe(OreRatio + "Failed to initialize the database: " + e.getMessage());
        }
    }

    private void initCommands() {
        try {
            Bukkit.getLogger().info(OreRatio + "Initializing commands...");
            getCommand("oreratio").setExecutor(new OreRatioCommands());

        } catch (Exception e) {
            Bukkit.getLogger().severe(OreRatio + "Failed to initialize commands: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(OreRatio + "OreRatio has been disabled!");
        Bukkit.getLogger().info(OreRatio + "Version : " + Version.VERSION);
        Bukkit.getLogger().info(OreRatio + checkUpdates.isLatestVersion());

        // Loaders
    }
}