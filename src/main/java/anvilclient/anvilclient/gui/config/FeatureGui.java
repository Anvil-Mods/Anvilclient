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
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.gui.util.Utils;
import anvilclient.anvilclient.settings.ISetting;
import anvilclient.anvilclient.settings.SettingRegister;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;

public class FeatureGui extends ConfigScreen {

	private final Feature feature;

	public FeatureGui(Feature feature, Screen parentScreen) {
		super("anvilclient.feature." + feature.getName() + "title", parentScreen);
		this.feature = feature;
	}

	@Override
	protected void addOptions() {
		List<ISetting<?>> settingList = SettingRegister.SETTING_LIST_FOR_OPTIONS.get(feature);
		
		if (TogglableFeature.class.isAssignableFrom(feature.getClass())) {
			this.optionsRowList.addOption(new BooleanOption("anvilclient.feature." + feature.getName() + ".toggle",
					unused -> ((TogglableFeature) feature).isEnabled(),
					(unused, newValue) -> ((TogglableFeature) feature).setEnabled(newValue)));
		}
		
		for (ISetting<?> setting : settingList.stream().filter((setting) -> !setting.getName().contains(".enabled"))
				.toArray(ISetting<?>[]::new)) {
			AbstractOption option = Utils.getOptionForSetting(setting);
			if (option != null) {
				this.optionsRowList.addOption(option);
			}
		}
	}

}
