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

import com.mojang.blaze3d.matrix.MatrixStack;

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.mixin.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class FPSDisplay extends TogglableFeature {

	@Override
	public String getName() {
		return "fpsDisplay";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}
	
	private static final int TEXT_COLOR = 0xFFFFFF;
	
	public void render(int width, int height, MatrixStack matrixStack, Minecraft mc) {
		if (isEnabled()) {
			int coordinatesX = (int) (width * 0.75);
			int coordinatesY = (int) (height * 0.25);
			int fps;
			try {
				fps = ((IMixinMinecraft) mc).getFPS();
				AbstractGui.drawString(matrixStack, mc.font, "FPS: " + fps, coordinatesX, coordinatesY,
						TEXT_COLOR);
			} catch (IllegalArgumentException e) {
				AnvilClient.LOGGER.catching(e);
			} 
		}
	}

}
