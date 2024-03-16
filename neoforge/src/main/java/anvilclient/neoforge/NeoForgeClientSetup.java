package anvilclient.neoforge;

import anvilclient.AnvilclientCommon;
import anvilclient.util.utils.ScreenUtils;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.ConfigScreenHandler;

public class NeoForgeClientSetup {

    public static void setup() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> ScreenUtils.getMainConfigGui(screen)));
        AnvilclientCommon.init();
    }

}
