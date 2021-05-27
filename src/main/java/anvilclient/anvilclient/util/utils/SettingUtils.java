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
import net.minecraft.client.AbstractOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.IteratableOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;

public class SettingUtils {
	
	private SettingUtils() {
	}

	@SuppressWarnings("unchecked")
	public static AbstractOption getOptionForSetting(ISetting<?> setting) {
		AbstractOption option;
		String translationKey = "anvilclient.feature." + setting.getName();
		if (BooleanSetting.class.isAssignableFrom(setting.getClass())) {
			BooleanSetting booleanSetting = (BooleanSetting) setting;
			option = new BooleanOption(translationKey, (unused) -> booleanSetting.getValue(),
					(unused, newValue) -> booleanSetting.setValue(newValue));

		} else if (EnumSetting.class.isAssignableFrom(setting.getClass())) {
			EnumSetting<?> enumSetting = (EnumSetting<?>) setting;
			option = new IteratableOption(translationKey, (unused, newValue) -> enumSetting.setValue(
					enumSetting.getValue().getClass().getEnumConstants()[(enumSetting.getValue().ordinal() + newValue)
							% enumSetting.getValue().getClass().getEnumConstants().length]),
					(unused, unused2) -> new StringTextComponent(I18n.format("anvilclient.feature." + setting.getName())
							+ ": " + I18n.format(((SettingSuitableEnum) setting.getValue()).getTranslationKey())));

		} else if (NumberSetting.class.isAssignableFrom(setting.getClass())) {
			NumberSetting<?> numberSetting = (NumberSetting<?>) setting;
			option = new SliderPercentageOption(translationKey, numberSetting.getMinValue().doubleValue(),
					numberSetting.getMaxValue().doubleValue(), numberSetting.getStepSize(),
					(unused) -> numberSetting.getDoubleValue(),
					(unused, newValue) -> numberSetting.setValue(newValue),
					(unused, unused2) -> new StringTextComponent(
							I18n.format(translationKey) + ": "
									+ MathUtils.trimDouble(numberSetting.getDoubleValue(), numberSetting.getDecimalCount())));
		} else {
			option = null;
		}
		return option;
	}
	
	public static AbstractOption[] getOptionListForTogglableFeature(TogglableFeature feature, Screen screen) {
		AbstractOption[] options = new AbstractOption[2];
		options[0] = new BooleanOption("anvilclient.feature." + feature.getName() + ".toggle",
				unused -> feature.isEnabled(),
				(unused, newValue) -> feature.setEnabled(newValue));
		options[1] = new ClickOption("anvilclient.feature." + feature.getName(),
				button -> Minecraft.getInstance().displayGuiScreen(new FeatureGui(feature, screen)));
		return options;
	}

}
