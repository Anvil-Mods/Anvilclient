package anvilclient.anvilclient.util.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;

public class HudUtils {
	
	@SuppressWarnings("resource")
	public static boolean shouldRender() {
		Options options = getMinecraft().options;
		return !options.hideGui && !options.renderDebug;
	}
	
	@SuppressWarnings("resource")
	public static Font getFont() {
		return getMinecraft().font;
	}

	private static Minecraft getMinecraft() {
		return Minecraft.getInstance();
	}

}
