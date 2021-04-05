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

import anvilclient.anvilclient.features.AutoTool;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.IteratableOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;

public class AutoToolConfigGui extends ConfigScreen {

	public AutoToolConfigGui(Screen parentScreen) {
		super("anvilclient.configGui.autoTool.title", parentScreen);
	}
	
	public AutoToolConfigGui() {
		this(null);
	}

	@Override
	protected void addOptions() {
		this.optionsRowList.addOption(new BooleanOption("anvilclient.configGui.autoTool.toggle",
				unused -> configManager.getAutoTool(),
				(unused, newValue) -> configManager.setAutoTool(newValue)));
		this.optionsRowList.addOption(new SliderPercentageOption(
				"anvilclient.configGui.autoToolMinDurability.title",
		        0, Byte.MAX_VALUE, 1,
		        unused -> (double) configManager.getAutoToolMinDurability(),
		        (unused, newValue) -> configManager.setAutoToolMinDurability(newValue.intValue()),
		        (gs, option) -> new StringTextComponent(
		                I18n.format("anvilclient.configGui.autoToolMinDurability.title")
		                + ": "
		                + configManager.getAutoToolMinDurability())));
		this.optionsRowList.addOption(new BooleanOption("anvilclient.configGui.autoToolRevertTool.toggle",
				unused -> configManager.getAutoToolRevertTool(),
				(unused, newValue) -> configManager.setAutoToolRevertTool(newValue)));
		this.optionsRowList.addOption(new IteratableOption("anvilclient.configGui.autoToolSilkTouchMode.title",
				(unused, newValue) -> 
					configManager.setAutoToolSilkTouchMode(AutoTool.SilkTouchMode.values()[
						(configManager.getAutoToolSilkTouchMode().ordinal() + newValue)
							% AutoTool.SilkTouchMode.values().length]),
				(unused, option) -> new StringTextComponent(
					I18n.format("anvilclient.configGui.autoToolSilkTouchMode.title")
					+ ": "
					+ I18n.format(configManager.getAutoToolSilkTouchMode().getTranslationKey()))));
	}

}
