/*
 * Copyright (C) 2021-2025 Ambossmann <https://github.com/Ambossmann>
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
 */
package de.ambossmann.anvilclient.util.utils;

import de.ambossmann.anvilclient.gui.config.ConfigScreen;
import de.ambossmann.anvilclient.gui.config.MainGuiCategory;
import de.ambossmann.anvilclient.gui.config.MainGuiPlain;

public class ScreenUtils {

	public static ConfigScreen getMainConfigGui(
			net.minecraft.client.gui.screens.Screen parentScreen) {
		switch (ConfigScreen.sortType.getValue()) {
			case PLAIN:
				return new MainGuiPlain(parentScreen);
			case CATEGORY:
			default:
				return new MainGuiCategory(parentScreen);
		}
	}
}
