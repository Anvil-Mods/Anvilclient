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
package de.ambossmann.anvilclient.features.info;

import de.ambossmann.anvilclient.AnvilclientCommon;
import de.ambossmann.anvilclient.features.Feature;
import de.ambossmann.anvilclient.features.FeatureCategory;
import de.ambossmann.anvilclient.features.components.FeatureToggleComponent;
import de.ambossmann.anvilclient.features.components.HudComponent;
import de.ambossmann.anvilclient.features.components.KeybindingComponent;
import de.ambossmann.anvilclient.util.utils.HudUtils;
import de.ambossmann.anvilclient.util.utils.LocalPlayerUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class Coordinates extends Feature {

	@Override
	public String getName() {
		return "coordinates";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}

	private final FeatureToggleComponent toggleComponent =
			new FeatureToggleComponent(this, "", false);
	private final KeybindingComponent keybindingComponent =
			new KeybindingComponent(
					this,
					new KeyMapping(
							"de/ambossmann/anvilclient" + ".feature." + getName() + ".toggle",
							InputConstants.Type.KEYSYM,
							GLFW.GLFW_KEY_UNKNOWN,
							AnvilclientCommon.KEY_CATEGORY),
					toggleComponent::toggleEnabled);

	private final HudComponent hudComponent =
			new HudComponent(
					this,
					new HudComponent.TextRenderFunction(() -> "X: " + LocalPlayerUtils.getX()),
					toggleComponent::isEnabled,
					0.75,
					0.75);

	{
		hudComponent.addRenderFunction(
				new HudComponent.TextRenderFunction(() -> "Y: " + LocalPlayerUtils.getY()),
				0,
				HudUtils.DEFAULT_LINE_HEIGHT);
		hudComponent.addRenderFunction(
				new HudComponent.TextRenderFunction(() -> "Z: " + LocalPlayerUtils.getZ()),
				0,
				HudUtils.DEFAULT_LINE_HEIGHT * 2);
	}

	@Override
	public void register() {
		addComponents(toggleComponent, keybindingComponent, hudComponent);
		super.register();
	}
}
