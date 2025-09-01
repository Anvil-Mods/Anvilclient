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
package de.ambossmann.anvilclient.gui.config;

import de.ambossmann.anvilclient.features.Feature;
import de.ambossmann.anvilclient.features.Features;
import de.ambossmann.anvilclient.util.utils.SettingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class MainGuiPlain extends ConfigScreen {

	public MainGuiPlain(Screen parentScreen) {
		super("anvilclient", parentScreen);
	}

	@Override
	protected void addOptions() {
		for (Feature feature : Features.FEATURE_LIST) {
			if (feature.getFeatureToggle() != null) {
				this.list.addSmall(SettingUtils.getOptionListForFeature(feature, this));
			} else {
				this.list.addBig(
						SettingUtils.getClickOption(
								"anvilclient.feature." + feature.getName(),
								() -> Minecraft.getInstance().setScreen(new FeatureGui(feature, this))));
			}
		}
	}

	@Override
	protected void addFooter() {
		LinearLayout linearLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
		linearLayout.addChild(
				Button.builder(
								Component.translatable(SortType.PLAIN.getKey()), button -> this.changeScreen())
						.build());
		linearLayout.addChild(
				Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).build());
	}

	private void changeScreen() {
		ConfigScreen.sortType.setValue(SortType.CATEGORY);
		this.minecraft.setScreen(new MainGuiCategory(this.lastScreen));
	}
}
