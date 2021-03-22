package anvilclient.anvilclient.util;

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.gui.config.MainConfigGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AnvilClient.MOD_ID, bus = Bus.FORGE)
public class KeyInputHandler{
	
	private static final ConfigManager configManager = ConfigManager.getInstance();
	private static final SettingManager settingManager = SettingManager.getInstance();

	@SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        if (Keybinds.openSettings.isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new MainConfigGui());
        }
        if (Keybinds.fullbright.isPressed()) {
        	configManager.toggleFullbright();
        	configManager.save();
        	settingManager.update();
        }
        if (Keybinds.coordinates.isPressed()) {
        	configManager.toggleCoordinates();
        	configManager.save();
        }
    }
}