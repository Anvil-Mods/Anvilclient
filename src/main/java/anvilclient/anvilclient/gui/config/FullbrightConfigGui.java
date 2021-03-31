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

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;

public class FullbrightConfigGui extends ConfigScreen {

	public FullbrightConfigGui(Screen parentScreen) {
		super("anvilclient.configGui.fullbright.title", parentScreen);
	}
	
	public FullbrightConfigGui() {
		super("anvilclient.configGui.fullbright.title");
	}
	
	@Override
	protected void addOptions() {
		this.optionsRowList.addOption(new BooleanOption(
                "anvilclient.configGui.fullbright.toggle",
                unused -> configManager.getFullbright(),
                (unused, newValue) -> configManager.setFullbright(newValue)
        ));
		this.optionsRowList.addOption(new SliderPercentageOption(
				"anvilclient.configGui.fullbrightLevel.title",
		        0.0, 12.0, (float) 0.1,
		        unused -> configManager.getFullbrightLevel(),
		        (unused, newValue) -> configManager.setFullbrightLevel(newValue.doubleValue()),
		        (gs, option) -> new StringTextComponent(
		                I18n.format("anvilclient.configGui.fullbrightLevel.title")
		                + ": "
		                + ((double) Math.round(((double) option.get(gs))*10))/10
		        )
		));
	}
	
}
