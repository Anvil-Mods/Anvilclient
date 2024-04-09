/*
 * Copyright (C) 2024 Ambossmann <https://github.com/Ambossmann>
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
package anvilclient.features.info;

import anvilclient.AnvilclientCommon;
import anvilclient.features.Feature;
import anvilclient.features.FeatureCategory;
import anvilclient.features.components.FeatureToggleComponent;
import anvilclient.features.components.HudComponent;
import anvilclient.features.components.KeybindingComponent;
import anvilclient.settings.EnumSetting;
import anvilclient.settings.Setting;
import anvilclient.util.utils.HudUtils;
import anvilclient.util.utils.LocalPlayerUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.util.OptionEnum;
import org.lwjgl.glfw.GLFW;

public class ArmorDurability extends Feature {

	public static final String NAME = "armorDurability";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}

	@Setting
	public final EnumSetting<DisplayDirection> displayDirection =
			new EnumSetting<>(getName() + ".displayDirection", "", DisplayDirection.HORIZONTAL);

	private final FeatureToggleComponent toggleComponent =
			new FeatureToggleComponent(this, "", false);
	private final KeybindingComponent keybindingComponent =
			new KeybindingComponent(
					this,
					new KeyMapping(
							"anvilclient.feature." + getName() + ".toggle",
							InputConstants.Type.KEYSYM,
							GLFW.GLFW_KEY_UNKNOWN,
							AnvilclientCommon.KEY_CATEGORY),
					toggleComponent::toggleEnabled);

	private final HudComponent hudComponent =
			new HudComponent(
					this,
					(new HudComponent.ItemRenderFunction(
							() ->
									LocalPlayerUtils.getItem(
											LocalPlayerUtils.getLocalPlayer(), LocalPlayerUtils.SLOT_ARMOR_HEAD))),
					toggleComponent::isEnabled,
					0.25,
					0.25);

	{
		hudComponent.addRenderFunction(
				new HudComponent.ItemRenderFunction(
						() ->
								LocalPlayerUtils.getItem(
										LocalPlayerUtils.getLocalPlayer(), LocalPlayerUtils.SLOT_ARMOR_CHEST)),
				0,
				(HudUtils.ITEM_ICON_SIZE + 1));
		hudComponent.addRenderFunction(
				new HudComponent.ItemRenderFunction(
						() ->
								LocalPlayerUtils.getItem(
										LocalPlayerUtils.getLocalPlayer(), LocalPlayerUtils.SLOT_ARMOR_LEGS)),
				0,
				(HudUtils.ITEM_ICON_SIZE + 1) * 2);
		hudComponent.addRenderFunction(
				new HudComponent.ItemRenderFunction(
						() ->
								LocalPlayerUtils.getItem(
										LocalPlayerUtils.getLocalPlayer(), LocalPlayerUtils.SLOT_ARMOR_FEET)),
				0,
				(HudUtils.ITEM_ICON_SIZE + 1) * 3);
	}

	@Override
	public void register() {
		addComponents(toggleComponent, keybindingComponent, hudComponent);
		super.register();
	}

	public enum DisplayDirection implements OptionEnum {
		HORIZONTAL,
		VERTICAL;

		private final String translationKey;

		DisplayDirection() {
			this.translationKey =
					"anvilclient.feature.autoTool.armorDurability." + this.toString().toLowerCase();
		}

		@Override
		public int getId() {
			return this.ordinal();
		}

		@Override
		public String getKey() {
			return translationKey;
		}
	}
}
