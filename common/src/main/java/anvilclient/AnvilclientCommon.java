package anvilclient;

import anvilclient.features.Features;
import anvilclient.settings.ConfigManager;
import anvilclient.settings.SettingRegister;
import anvilclient.util.Keybinds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilclientCommon {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "anvilclient";
    public static final String KEY_CATEGORY = "anvilclient.key.categories.anvilclient";

    public static void init() {
        Keybinds.register();
        Features.init();
        Features.register();
        SettingRegister.registerFeatures();
        SettingRegister.registerClass(anvilclient.gui.config.ConfigScreen.class);
        ConfigManager.getInstance().loadProperties();
        ConfigManager.getInstance().cleanupConfig();
    }
}
