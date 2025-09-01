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

import de.ambossmann.anvilclient.settings.EnumSetting;
import de.ambossmann.anvilclient.settings.IgnoreAsOption;
import de.ambossmann.anvilclient.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.OptionEnum;

public abstract class ConfigScreen extends OptionsSubScreen {
	public ConfigScreen(String nameTranslationKey, Screen lastScreen) {
		super(lastScreen, Minecraft.getInstance().options, Component.translatable(nameTranslationKey));
	}

	@Setting @IgnoreAsOption
	public static EnumSetting<SortType> sortType =
			new EnumSetting<>("anvilclient.sortType", "", SortType.CATEGORY);

	public enum SortType implements OptionEnum {
		PLAIN,
		CATEGORY;

		private final String translationKey;

		SortType() {
			this.translationKey = "anvilclient.configGui.sortType." + this.toString().toLowerCase();
		}

		@Override
		public String getKey() {
			return translationKey;
		}

		@Override
		public int getId() {
			return this.ordinal();
		}
	}
}
