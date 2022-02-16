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
package anvilclient.anvilclient.gui.util;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.OptionsList;

public class ExtendedOptionsRowList extends OptionsList {

	public ExtendedOptionsRowList(Minecraft mc, int width, int height, int topHeight, int bottomHeight, int itemHeight) {
		super(mc, width, height, topHeight, bottomHeight, itemHeight);
	}

	@Override
	protected void renderBackground(PoseStack p_230433_1_) {
		super.renderBackground(p_230433_1_);
	}
}
