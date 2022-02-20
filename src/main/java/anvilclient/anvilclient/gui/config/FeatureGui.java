/*******************************************************************************
 * Copyright (C) 2021, 2022 Anvil-Mods
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
 *******************************************************************************/
package anvilclient.anvilclient.gui.config;

import java.util.List;

import anvilclient.anvilclient.features.Feature;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.settings.ISetting;
import anvilclient.anvilclient.settings.SettingRegister;
import anvilclient.anvilclient.util.utils.SettingUtils;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Option;
import net.minecraft.client.gui.screens.Screen;

public class FeatureGui extends ConfigScreen {

	private final Feature feature;

	public FeatureGui(Feature feature, Screen parentScreen) {
		super("anvilclient.feature." + feature.getName(), parentScreen);
		this.feature = feature;
	}

	@Override
	protected void addOptions() {
		List<ISetting<?>> settingList = SettingRegister.SETTING_LIST_FOR_OPTIONS.get(feature);

		if (feature instanceof TogglableFeature togglableFeature) {
			this.optionsList.addBig(CycleOption.createOnOff("anvilclient.feature." + feature.getName() + ".toggle",
					unused -> togglableFeature.isEnabled(),
					(unused, unused2, newValue) -> togglableFeature.setEnabled(newValue)));
		}

		for (ISetting<?> setting : settingList.stream().filter((setting) -> !setting.getName().contains(".enabled"))
				.toArray(ISetting<?>[]::new)) {
			Option option = SettingUtils.getOptionForSetting(setting);
			if (option != null) {
				this.optionsList.addBig(option);
			}
		}
	}

}
