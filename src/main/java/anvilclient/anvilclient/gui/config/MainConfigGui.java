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

import anvilclient.anvilclient.gui.util.ClickOption;
import anvilclient.anvilclient.util.ConfigManager;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;

public final class MainConfigGui extends ConfigScreen {

	private AbstractOption[] fullbrightOptions = {
			new BooleanOption("anvilclient.configGui.fullbright.toggle",
					unused -> configManager.getFullbright(),
					(unused, newValue) -> configManager.setFullbright(newValue)),
			new ClickOption("anvilclient.configGui.fullbright.title",
					(unused) -> this.minecraft.displayGuiScreen(new FullbrightConfigGui(this))) };

	public MainConfigGui(Screen parentScreen) {
		super("anvilclient.mainConfigGui.title", parentScreen);
	}

	public MainConfigGui() {
		super("anvilclient.mainConfigGui.title");
	}

	@Override
	protected void addOptions() {
		this.optionsRowList.addOptions(fullbrightOptions);
	}
}
