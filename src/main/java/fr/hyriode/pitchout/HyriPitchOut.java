package fr.hyriode.pitchout;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.hyrame.HyrameLoader;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.plugin.IPluginProvider;
import fr.hyriode.pitchout.config.POConfig;
import fr.hyriode.pitchout.dev.DevConfig;
import fr.hyriode.pitchout.game.POGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Created by AstFaster
 * on 19/12/2022 at 16:12
 */
public class HyriPitchOut extends JavaPlugin implements IPluginProvider {

    public static final String NAME = "PitchOut";
    public static final String ID = "pitchout";
    private static final String[] HEADER_LINES = new String[] {
            "  ___ _ _      _    ___       _   ",
            " | _ (_) |_ __| |_ / _ \\ _  _| |_ ",
            " |  _/ |  _/ _| ' \\ (_) | || |  _|",
            " |_| |_|\\__\\__|_||_\\___/ \\_,_|\\__|",
            "                                  "
    };
    private static final String PACKAGE = "fr.hyriode.pitchout";

    private static HyriPitchOut instance;

    private IHyrame hyrame;
    private POConfig config;
    private POGame game;

    @Override
    public void onEnable() {
        instance = this;

        for (String line : HEADER_LINES) {
            log(line);
        }

        log("Starting " + NAME + "...");

        this.hyrame = HyrameLoader.load(this);
        this.config = HyriAPI.get().getConfig().isDevEnvironment() ? new DevConfig() : HyriAPI.get().getServer().getConfig(POConfig.class);
        this.hyrame.getGameManager().registerGame(() -> this.game = new POGame());
    }

    @Override
    public void onDisable() {
        log("Stopping " + NAME + "...");

        this.hyrame.getGameManager().unregisterGame(this.game);
    }

    public static void log(Level level, String message) {
        String prefix = ChatColor.GOLD + "[" + NAME + "] ";

        if (level == Level.SEVERE) {
            prefix += ChatColor.RED;
        } else if (level == Level.WARNING) {
            prefix += ChatColor.YELLOW;
        } else {
            prefix += ChatColor.RESET;
        }

        Bukkit.getConsoleSender().sendMessage(prefix + message);
    }

    public static void log(String msg) {
        log(Level.INFO, msg);
    }

    public static HyriPitchOut get() {
        return instance;
    }

    public IHyrame getHyrame() {
        return this.hyrame;
    }

    public POConfig getConfiguration() {
        return this.config;
    }

    public POGame getGame() {
        return this.game;
    }

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }

    @Override
    public String getId() {
        return "pitchout";
    }

    @Override
    public String[] getCommandsPackages() {
        return new String[] {PACKAGE};
    }

    @Override
    public String[] getListenersPackages() {
        return new String[] {PACKAGE};
    }

    @Override
    public String[] getItemsPackages() {
        return new String[] {PACKAGE};
    }

    @Override
    public String getLanguagesPath() {
        return "/lang/";
    }

}
