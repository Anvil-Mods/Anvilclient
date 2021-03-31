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
import net.minecraft.client.settings.BooleanOption;

public class CoordinatesConfigGui extends ConfigScreen {

	public CoordinatesConfigGui(Screen parentScreen) {
		super("anvilclient.configGui.coordinates.title", parentScreen);
	}
	
	public CoordinatesConfigGui() {
		super("anvilclient.configGui.coordinates.title");
	}
	
	@Override
	protected void addOptions() {
		this.optionsRowList.addOption(new BooleanOption(
                "anvilclient.configGui.coordinates.toggle",
                unused -> configManager.getCoordinates(),
                (unused, newValue) -> configManager.setCoordinates(newValue)
        ));
	}
}
