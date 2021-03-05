package anvilclient.anvilclient.util;

import anvilclient.anvilclient.AnvilClient;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;

public class SettingManager {

	private static final SettingManager INSTANCE = new SettingManager();
	
	private GameSettings gameSettings = Minecraft.getInstance().gameSettings;
	
	private boolean vanillaGammaInitialized = true;
	
	public SettingManager() {
	}

	public static SettingManager getInstance() {
		return INSTANCE;
	}
	
	public void update() {
		updateFullbright();
	}

	public void updateFullbright() {
		if (ConfigManager.getInstance().getFullbright()) {
			if (!vanillaGammaInitialized && AbstractOption.GAMMA.get(gameSettings) <= 1.0) {
				ConfigManager.getInstance().setVanillaGamma(AbstractOption.GAMMA.get(gameSettings));
			}
			AbstractOption.GAMMA.set(gameSettings, ConfigManager.getInstance().getFullbrightLevel());
		} else if (!ConfigManager.getInstance().getFullbright()){
			AbstractOption.GAMMA.set(gameSettings, ConfigManager.getInstance().getVanillaGamma());
			AnvilClient.LOGGER.info("Gamma set to VanillaGammma " + AbstractOption.GAMMA.get(gameSettings));
			vanillaGammaInitialized = false;
		}
	}

}
