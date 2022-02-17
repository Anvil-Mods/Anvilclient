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
package anvilclient.anvilclient.gui.config;

import anvilclient.anvilclient.features.Feature;
import anvilclient.anvilclient.features.Features;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.gui.util.ClickOption;
import anvilclient.anvilclient.util.utils.SettingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TranslatableComponent;

public class MainGuiPlain extends ConfigScreen {

	public MainGuiPlain(Screen parentScreen) {
		super("anvilclient", parentScreen);
	}

	@Override
	protected void addOptions() {
		for (Feature feature : Features.FEATURE_LIST) {
			if (TogglableFeature.class.isAssignableFrom(feature.getClass())) {
				this.optionsRowList
						.addSmall(SettingUtils.getOptionListForTogglableFeature((TogglableFeature) feature, this));
			} else {
				this.optionsRowList.addBig(new ClickOption("anvilclient.feature." + feature.getName(),
						button -> Minecraft.getInstance().setScreen(new FeatureGui(feature, this))));
			}
		}
	}

	@Override
	protected void addButtons() {
		this.addRenderableWidget(new Button(this.width / 2 - (BUTTON_WIDTH + 5), this.height - DONE_BUTTON_TOP_OFFSET,
				BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(SortType.PLAIN.getTranslationKey()),
				button -> this.changeScreen()));
		this.addRenderableWidget(new Button(this.width / 2 - (BUTTON_WIDTH + 5) + BUTTON_WIDTH + 10,
				this.height - DONE_BUTTON_TOP_OFFSET, BUTTON_WIDTH, BUTTON_HEIGHT,
				new TranslatableComponent("gui.done"), button -> this.onClose()));
	}

	private void changeScreen() {
		ConfigScreen.sortType.setValue(SortType.CATEGORY);
		this.minecraft.setScreen(new MainGuiCategory(this.parentScreen));
	}

}
