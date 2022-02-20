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
package anvilclient.anvilclient.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.OptionsList;

public class ExtendedOptionsList extends OptionsList {

	public ExtendedOptionsList(Minecraft mc, int width, int height, int topHeight, int bottomHeight, int itemHeight) {
		super(mc, width, height, topHeight, bottomHeight, itemHeight);
	}
}
