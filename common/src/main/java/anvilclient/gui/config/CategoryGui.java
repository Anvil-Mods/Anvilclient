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
package anvilclient.gui.config;

import anvilclient.features.Feature;
import anvilclient.features.FeatureCategory;
import anvilclient.features.Features;
import anvilclient.util.utils.SettingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;

public class CategoryGui extends ConfigScreen {

	FeatureCategory category;

	public CategoryGui(FeatureCategory category, Screen parentScreen) {
		super(category.getKey(), parentScreen);
		this.category = category;
	}

	@Override
	protected void addOptions() {
		List<Feature> featureList = Features.FEATURE_LIST_BY_CATEGORY.get(category);

		for (Feature feature : featureList) {
			if (feature.getFeatureToggle() != null) {
				this.optionsList
						.addSmall(SettingUtils.getOptionListForFeature(feature, this));
			} else {
				this.optionsList.addBig(SettingUtils.getClickOption("anvilclient.feature." + feature.getName(),
						() -> Minecraft.getInstance().setScreen(new FeatureGui(feature, this))));
			}
		}

	}

}
