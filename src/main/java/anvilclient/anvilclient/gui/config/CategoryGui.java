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

import java.util.List;

import anvilclient.anvilclient.features.Feature;
import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.Features;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.gui.util.ClickOption;
import anvilclient.anvilclient.util.utils.SettingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class CategoryGui extends ConfigScreen {
	
	FeatureCategory category;

	public CategoryGui(FeatureCategory category, Screen parentScreen) {
		super(category.getTranslationKey(), parentScreen);
		this.category = category;
	}

	@Override
	protected void addOptions() {
		List<Feature> featureList = Features.FEATURE_LIST_BY_CATEGORY.get(category);
		
		for (Feature feature : featureList) {
			if (TogglableFeature.class.isAssignableFrom(feature.getClass())) {
				this.optionsRowList
						.addSmall(SettingUtils.getOptionListForTogglableFeature((TogglableFeature) feature, this));
			} else {
				this.optionsRowList.addBig(new ClickOption("anvilclient.feature." + feature.getName(),
						button -> Minecraft.getInstance().setScreen(new FeatureGui(feature, this))));
			}
		}
		
	}

}
