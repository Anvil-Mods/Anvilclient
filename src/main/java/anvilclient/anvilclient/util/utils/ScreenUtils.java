package anvilclient.anvilclient.util.utils;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;

public class ScreenUtils {
	
	public static anvilclient.anvilclient.gui.config.ConfigScreen getMainConfigGui(net.minecraft.client.gui.screen.Screen parentScreen) {
		switch (anvilclient.anvilclient.gui.config.ConfigScreen.sortType.getValue()) {
		case PLAIN:
			return new anvilclient.anvilclient.gui.config.MainGuiPlain(parentScreen);
		case CATEGORY:
		default:
			return new anvilclient.anvilclient.gui.config.MainGuiCategory(parentScreen);
		}
	}
	
	public static void registerForgeConfig() {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
				() -> (mc, screen) -> getMainConfigGui(screen)));
	}

}
