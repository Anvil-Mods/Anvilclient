package anvilclient.forge;

import anvilclient.AnvilclientCommon;
import anvilclient.util.utils.ScreenUtils;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.fml.ModLoadingContext;

public class ForgeClientSetup {

    public static void setup() {
        EventBuses.registerModEventBus(AnvilclientCommon.MOD_ID, AnvilClientForge.MOD_EVENT_BUS);
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenFactory.class,
                () -> new ConfigScreenFactory((mc, screen) -> ScreenUtils.getMainConfigGui(screen)));
        AnvilclientCommon.init();
    }

}
