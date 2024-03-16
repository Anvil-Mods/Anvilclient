/*
 * Copyright (C) 2021-2024 Ambossmann <https://github.com/Ambossmann>
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
package anvilclient.features.graphic;

import anvilclient.AnvilclientCommon;
import anvilclient.features.Feature;
import anvilclient.features.FeatureCategory;
import anvilclient.features.components.FeatureToggleComponent;
import anvilclient.features.components.KeybindingComponent;
import anvilclient.settings.DoubleSetting;
import anvilclient.settings.Setting;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class Fullbright extends Feature {

	@Override
	public String getName() {
		return "fullbright";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.GRAPHIC;
	}

	private final FeatureToggleComponent toggleComponent =
			new FeatureToggleComponent(
					this,
					"",
					false,
					(b) -> Minecraft.getInstance().gameRenderer.lightTexture().updateLightTexture = true);
	private final KeybindingComponent keybindingComponent =
			new KeybindingComponent(
					this,
					new KeyMapping(
							"anvilclient.feature." + getName() + ".toggle",
							InputConstants.Type.KEYSYM,
							GLFW.GLFW_KEY_UNKNOWN,
							AnvilclientCommon.KEY_CATEGORY),
					toggleComponent::toggleEnabled);

	@Setting
	public DoubleSetting fullbrightLevel =
			new DoubleSetting(getName() + ".level", "", 12.0, 0.0, 12.0, 0.1F, 2);

	@Override
	public void register() {
		addComponents(toggleComponent, keybindingComponent);
		super.register();
	}
}
