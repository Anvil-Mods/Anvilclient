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
package anvilclient.anvilclient.features.info;

import com.mojang.blaze3d.vertex.PoseStack;

import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.GuiComponent;

public class Coordinates extends TogglableFeature {

	@Override
	public String getName() {
		return "coordinates";
	}
	
	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}
	
	private static final int TEXT_COLOR = 0xFFFFFF;
	private static final int LINE_HEIGHT = 10;

	public void render(int width, int height, PoseStack poseStack, Minecraft mc, LocalPlayer player) {
		if (isEnabled()) {
			int currentHeight = 0;
			int coordinatesX = (int) (width * 0.75);
			int coordinatesY = (int) (height * 0.75);
			GuiComponent.drawString(poseStack, mc.font, "X: " + player.getX(), coordinatesX, coordinatesY + currentHeight, TEXT_COLOR);
			currentHeight += LINE_HEIGHT + 1;
			GuiComponent.drawString(poseStack, mc.font, "Y: " + player.getY(), coordinatesX, coordinatesY + currentHeight, TEXT_COLOR);
			currentHeight += LINE_HEIGHT + 1;
			GuiComponent.drawString(poseStack, mc.font, "Z: " + player.getZ(), coordinatesX, coordinatesY + currentHeight, TEXT_COLOR);
		}
	}
}
