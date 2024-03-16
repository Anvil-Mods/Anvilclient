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
import anvilclient.features.components.ToggleComponent;
import anvilclient.settings.ISetting;
import anvilclient.settings.SettingRegister;
import anvilclient.util.utils.SettingUtils;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;

import java.util.TreeSet;

public class FeatureGui extends ConfigScreen {

	private final Feature feature;

	public FeatureGui(Feature feature, Screen parentScreen) {
		super("anvilclient.feature." + feature.getName(), parentScreen);
		this.feature = feature;
	}

	@Override
	protected void addOptions() {
		TreeSet<ISetting<?>> settingList = SettingRegister.SETTING_LIST_FOR_OPTIONS.get(feature);

		ToggleComponent featureToggle = feature.getFeatureToggle();
		if (featureToggle != null) {
			this.optionsList.addBig(OptionInstance.createBoolean("anvilclient.feature." + feature.getName() + ".toggle",
					featureToggle.isEnabled(),
					featureToggle::setEnabled));
		}

		for (ISetting<?> setting : settingList.stream().filter((setting) -> !setting.getKey().contains(".featureEnabled"))
				.toArray(ISetting<?>[]::new)) {
			OptionInstance<?> option = SettingUtils.getOptionForSetting(setting);
			if (option != null) {
				this.optionsList.addBig(option);
			}
		}
	}

}
