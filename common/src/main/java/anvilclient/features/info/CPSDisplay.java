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
package anvilclient.features.info;

import anvilclient.AnvilclientCommon;
import anvilclient.features.Feature;
import anvilclient.features.FeatureCategory;
import anvilclient.features.components.FeatureToggleComponent;
import anvilclient.features.components.HudComponent;
import anvilclient.features.components.KeybindingComponent;
import anvilclient.settings.EnumSetting;
import anvilclient.settings.IntegerSetting;
import anvilclient.settings.Setting;
import anvilclient.util.utils.MathUtils;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import java.time.Instant;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.util.OptionEnum;
import org.lwjgl.glfw.GLFW;

public class CPSDisplay extends Feature {

	@Override
	public String getName() {
		return "cpsDisplay";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}

	private LongList clicks = new LongArrayList();

	@Setting
	public final EnumSetting<CPSMouseButton> mouseButton =
			new EnumSetting<CPSDisplay.CPSMouseButton>(
					getName() + ".mouseButton", "", CPSDisplay.CPSMouseButton.LEFT);

	@Setting
	public final IntegerSetting measuringSpan =
			new IntegerSetting(getName() + ".measuringSpan", "", 2, 1, 10, 1);

	private EventResult mouseClicked(Minecraft client, int button, int action, int mods) {
		if (toggleComponent.isEnabled()) {
			if (action == GLFW.GLFW_PRESS && button == mouseButton.getValue().getButton()) {
				clicks.add(Instant.now().toEpochMilli());
			}
		}
		return EventResult.pass();
	}

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
					new HudComponent.TextRenderFunction(
							() -> {
								double cps = ((double) clicks.size()) / ((double) measuringSpan.getValue());
								long lgt = Instant.now().toEpochMilli() - 1000L * measuringSpan.getValue();

								while (!clicks.isEmpty() && clicks.getLong(0) < lgt) {
									clicks.removeLong(0);
								}

								return "CPS: " + MathUtils.trimDouble(cps, 2);
							}),
					toggleComponent::isEnabled,
					0.75,
					0.3);

	@Override
	public void register() {
		addComponents(toggleComponent, keybindingComponent, hudComponent);
		super.register();
		ClientRawInputEvent.MOUSE_CLICKED_POST.register(this::mouseClicked);
	}

	public enum CPSMouseButton implements OptionEnum {
		LEFT(GLFW.GLFW_MOUSE_BUTTON_LEFT),
		RIGHT(GLFW.GLFW_MOUSE_BUTTON_RIGHT),
		MIDDLE(GLFW.GLFW_MOUSE_BUTTON_MIDDLE);

		private final String translationKey;

		private final int button;

		private CPSMouseButton(int button) {
			this.translationKey = "key.mouse." + this.toString().toLowerCase();
			this.button = button;
		}

		@Override
		public String getKey() {
			return translationKey;
		}

		@Override
		public int getId() {
			return button;
		}

		public int getButton() {
			return button;
		}
	}
}
