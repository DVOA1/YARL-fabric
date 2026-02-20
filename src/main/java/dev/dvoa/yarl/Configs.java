package dev.dvoa.yarl;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class Configs {

    private static CommentedFileConfig CONFIG;

    public static String IMPORTANT;
    public static boolean LOAD_RESOURCES;

    public static void load() {
        Path path = FabricLoader.getInstance()
                .getConfigDir()
                .resolve("yarl.toml");

        CONFIG = CommentedFileConfig.builder(path)
                .autosave()
                .sync()
                .build();

        CONFIG.load();

        // Default values
        IMPORTANT = CONFIG.getOrElse("IMPORTANT",
                "DO NOT SHIP THIS CONFIG FILE IN A MODPACK!");

        LOAD_RESOURCES = CONFIG.getOrElse("loadResources", true);

        // Write defaults if missing
        CONFIG.set("IMPORTANT", IMPORTANT);
        CONFIG.set("loadResources", LOAD_RESOURCES);

        CONFIG.setComment("loadResources",
                "- USED INTERNALLY! Stores whether is the first time opening the game.");

        CONFIG.save();
    }

    public static void save() {
        CONFIG.set("IMPORTANT", IMPORTANT);
        CONFIG.set("loadResources", LOAD_RESOURCES);
        CONFIG.save();
    }
}