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
package anvilclient.features.info;

import anvilclient.AnvilclientCommon;
import anvilclient.features.Feature;
import anvilclient.features.FeatureCategory;
import anvilclient.features.components.FeatureToggleComponent;
import anvilclient.features.components.HudComponent;
import anvilclient.features.components.KeybindingComponent;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class FPSDisplay extends Feature {

	@Override
	public String getName() {
		return "fpsDisplay";
	}
	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}
	private static final int TEXT_COLOR = 0xFFFFFF;
	private final FeatureToggleComponent toggleComponent = new FeatureToggleComponent(this, "", false);
	private final KeybindingComponent keybindingComponent = new KeybindingComponent(this, new KeyMapping("anvilclient.feature." + getName() + ".toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, AnvilclientCommon.KEY_CATEGORY), toggleComponent::toggleEnabled);
	private final HudComponent hudComponent = new HudComponent(this, () -> "FPS: " + Minecraft.getInstance().getFps(), toggleComponent::isEnabled, (width) -> (int) (width * 0.75), (height) -> (int) (height * 0.25), TEXT_COLOR);

	@Override
	public void register() {
		addComponents(toggleComponent, keybindingComponent, hudComponent);
		super.register();
	}

}
