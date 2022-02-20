/*******************************************************************************
 * Copyright (C) 2021, 2022 Anvil-Mods
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package anvilclient.anvilclient.util.utils;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigGuiHandler.ConfigGuiFactory;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;

public class ScreenUtils {

	public static anvilclient.anvilclient.gui.config.ConfigScreen getMainConfigGui(
			net.minecraft.client.gui.screens.Screen parentScreen) {
		switch (anvilclient.anvilclient.gui.config.ConfigScreen.sortType.getValue()) {
			case PLAIN:
				return new anvilclient.anvilclient.gui.config.MainGuiPlain(parentScreen);
			case CATEGORY:
			default:
				return new anvilclient.anvilclient.gui.config.MainGuiCategory(parentScreen);
		}
	}

	public static void registerForgeConfig() {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
				() -> () -> ModLoadingContext.get().registerExtensionPoint(ConfigGuiFactory.class,
						() -> new ConfigGuiFactory((mc, screen) -> getMainConfigGui(screen))));
	}

}
