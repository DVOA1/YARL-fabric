package dev.dvoa.yarl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YARL implements ModInitializer {
    public static final String MOD_ID = "yarl";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static boolean started = false;

    @Override
    public void onInitialize() {
        Configs.load();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!started) {
                started = true;
                onStartup();
            }
        });
    }

    public void onStartup() {
        if (Configs.LOAD_RESOURCES) {
            Loader.loadResources();
            Configs.LOAD_RESOURCES = false;
            Configs.save();
        } else {
            Loader.addPackFinder();
        }
    }
}