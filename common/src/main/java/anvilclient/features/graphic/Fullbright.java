/*
 * Copyright (C) 2021-2023 Ambossmann <https://github.com/Ambossmann>
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
import anvilclient.settings.IgnoreAsOption;
import anvilclient.settings.Setting;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.lwjgl.glfw.GLFW;

//TODO reimplement to directly affect renderingg instead of changing game options
public class Fullbright extends Feature {

	@Override
	public String getName() {
		return "fullbright";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.GRAPHIC;
	}

	private Options gameSettings = Minecraft.getInstance().options;

	private boolean vanillaGammaInitialized = true;

	private final FeatureToggleComponent toggleComponent = new FeatureToggleComponent(this, "", false, this::update);
	private final KeybindingComponent keybindingComponent = new KeybindingComponent(this, new KeyMapping("anvilclient.feature." + getName() + ".toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, AnvilclientCommon.KEY_CATEGORY), toggleComponent::toggleEnabled);


	@Setting
	public DoubleSetting fullbrightLevel = new DoubleSetting(getName() + ".level", "", 12.0, 0.0, 12.0, 0.1F, 1);

	@Setting
	@IgnoreAsOption
	public DoubleSetting vanillaGamma = new DoubleSetting(getName() + ".vanillaGamma", "", 1.0, 0.0, 1.0, 0.01F, 2);

	@Override
	public void register() {
		addComponents(toggleComponent, keybindingComponent);
		super.register();
	}

	public void update(Boolean enabled) {
		if (enabled) {
			if (!vanillaGammaInitialized && gameSettings.gamma().get() <= 1.0) {
				vanillaGamma.setValue(gameSettings.gamma().get());
			}
			gameSettings.gamma().set(fullbrightLevel.getValue());
		} else {
			gameSettings.gamma().set(vanillaGamma.getValue());
			AnvilclientCommon.LOGGER.debug("Gamma set to Vanilla Gamma " + gameSettings.gamma().get());
			vanillaGammaInitialized = false;
		}
	}
}
