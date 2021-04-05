/*******************************************************************************
 * Copyright (C) 2021  Anvilclient and Contributors
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package anvilclient.anvilclient.features;

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.settings.ConfigManager;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;

public class Fullbright {
	
	private static GameSettings gameSettings = Minecraft.getInstance().gameSettings;
	
	private static boolean vanillaGammaInitialized = true;
	
	public static void update() {
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
