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
package anvilclient.anvilclient.util.utils;

import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.gui.config.FeatureGui;
import anvilclient.anvilclient.gui.util.ClickOption;
import anvilclient.anvilclient.settings.BooleanSetting;
import anvilclient.anvilclient.settings.EnumSetting;
import anvilclient.anvilclient.settings.ISetting;
import anvilclient.anvilclient.settings.NumberSetting;
import anvilclient.anvilclient.settings.SettingSuitableEnum;
import net.minecraft.client.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.BooleanOption;
import net.minecraft.client.CycleOption;
import net.minecraft.client.ProgressOption;
import net.minecraft.network.chat.TextComponent;

public class SettingUtils {
	
	private SettingUtils() {
	}

	@SuppressWarnings("unchecked")
	public static Option getOptionForSetting(ISetting<?> setting) {
		Option option;
		String translationKey = "anvilclient.feature." + setting.getName();
		if (setting instanceof BooleanSetting) {
			BooleanSetting booleanSetting = (BooleanSetting) setting;
			option = new BooleanOption(translationKey, (unused) -> booleanSetting.getValue(),
					(unused, newValue) -> booleanSetting.setValue(newValue));

		} else if (setting instanceof EnumSetting<?>) {
			EnumSetting<? extends Enum<?>> enumSetting = (EnumSetting<? extends Enum<?>>) setting;
			option = new CycleOption(translationKey, (unused, newValue) -> enumSetting.setValue(
					enumSetting.getValue().getClass().getEnumConstants()[(enumSetting.getValue().ordinal() + newValue)
							% enumSetting.getValue().getClass().getEnumConstants().length]),
					(unused, unused2) -> new TextComponent(I18n.get("anvilclient.feature." + setting.getName())
							+ ": " + I18n.get(((SettingSuitableEnum) setting.getValue()).getTranslationKey())));

		} else if (setting instanceof NumberSetting<?>) {
			NumberSetting<?> numberSetting = (NumberSetting<?>) setting;
			option = new ProgressOption(translationKey, numberSetting.getMinValue().doubleValue(),
					numberSetting.getMaxValue().doubleValue(), numberSetting.getStepSize(),
					(unused) -> numberSetting.getDoubleValue(),
					(unused, newValue) -> numberSetting.setValue(newValue),
					(unused, unused2) -> new TextComponent(
							I18n.get(translationKey) + ": "
									+ MathUtils.trimDouble(numberSetting.getDoubleValue(), numberSetting.getDecimalCount())));
		} else {
			option = null;
		}
		return option;
	}
	
	public static Option[] getOptionListForTogglableFeature(TogglableFeature feature, Screen screen) {
		Option[] options = new Option[2];
		options[0] = new BooleanOption("anvilclient.feature." + feature.getName() + ".toggle",
				unused -> feature.isEnabled(),
				(unused, newValue) -> feature.setEnabled(newValue));
		options[1] = new ClickOption("anvilclient.feature." + feature.getName(),
				button -> Minecraft.getInstance().setScreen(new FeatureGui(feature, screen)));
		return options;
	}

}
