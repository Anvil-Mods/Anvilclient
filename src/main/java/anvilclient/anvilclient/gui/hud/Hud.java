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
package anvilclient.anvilclient.gui.hud;

import com.mojang.blaze3d.matrix.MatrixStack;

import anvilclient.anvilclient.features.Features;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Hud extends AbstractGui {
	
	private static final Hud INSTANCE = new Hud();
	
	public static Hud getInstance() {
		return INSTANCE;
	}

	private final MatrixStack matrixStack;

	private final Minecraft mc;

	private int width;
	private int height;

	private Hud() {
		this.mc = Minecraft.getInstance();
		this.matrixStack = new MatrixStack();
		updateScaledWidthAndHeight();
	}

	public void render(RenderGameOverlayEvent.Post event) {
		if (shouldRender() && event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
			Features.COORDINATES.render(width, height, matrixStack, mc);
		}
	}

	public void updateScaledWidthAndHeight() {
		this.width = mc.getMainWindow().getScaledWidth();
        this.height = mc.getMainWindow().getScaledHeight();
	}

	private boolean shouldRender() {
		return !(mc.currentScreen instanceof ChatScreen) && !mc.gameSettings.showDebugInfo;
	}
	
}