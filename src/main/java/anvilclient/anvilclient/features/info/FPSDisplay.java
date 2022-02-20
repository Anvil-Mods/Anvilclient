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

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.mixin.IMixinMinecraft;
import anvilclient.anvilclient.util.utils.HudUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;

public class FPSDisplay extends TogglableFeature implements IIngameOverlay {

	@Override
	public String getName() {
		return "fpsDisplay";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}

	private static final int TEXT_COLOR = 0xFFFFFF;

	@Override
	public void register() {
		super.register();
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HUD_TEXT_ELEMENT, "FPS Display", this);
	}

	@Override
	public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
		if (isEnabled() && HudUtils.shouldRender()) {
			int coordinatesX = (int) (width * 0.75);
			int coordinatesY = (int) (height * 0.25);
			int fps;
			try {
				fps = ((IMixinMinecraft) Minecraft.getInstance()).getFPS();
				GuiComponent.drawString(poseStack, HudUtils.getFont(), "FPS: " + fps, coordinatesX, coordinatesY,
						TEXT_COLOR);
			} catch (IllegalArgumentException e) {
				AnvilClient.LOGGER.catching(e);
			}
		}
	}

}
