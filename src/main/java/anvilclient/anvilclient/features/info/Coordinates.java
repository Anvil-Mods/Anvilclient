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
package anvilclient.anvilclient.features.info;

import com.mojang.blaze3d.vertex.PoseStack;

import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.util.utils.HudUtils;
import anvilclient.anvilclient.util.utils.LocalPlayerUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;

public class Coordinates extends TogglableFeature implements IGuiOverlay {

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

	@Override
	public void register() {
		super.register();
		GuiOverlayManager.registerOverlayAbove(ForgeGui.HUD_TEXT_ELEMENT, "Coordinates", this);
	}

	@Override
	public void render(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
		if (isEnabled() && HudUtils.shouldRender()) {
			int currentHeight = 0;
			int coordinatesX = (int) (width * 0.75);
			int coordinatesY = (int) (height * 0.75);
			Font font = HudUtils.getFont();
			GuiComponent.drawString(poseStack, font, "X: " + LocalPlayerUtils.getX(), coordinatesX,
					coordinatesY + currentHeight, TEXT_COLOR);
			currentHeight += LINE_HEIGHT + 1;
			GuiComponent.drawString(poseStack, font, "Y: " + LocalPlayerUtils.getY(), coordinatesX,
					coordinatesY + currentHeight, TEXT_COLOR);
			currentHeight += LINE_HEIGHT + 1;
			GuiComponent.drawString(poseStack, font, "Z: " + LocalPlayerUtils.getZ(), coordinatesX,
					coordinatesY + currentHeight, TEXT_COLOR);
		}
	}
}
