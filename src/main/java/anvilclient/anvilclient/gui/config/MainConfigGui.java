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

import anvilclient.anvilclient.util.ClickButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;

public final class MainConfigGui extends ConfigScreen {

	public MainConfigGui(Screen parentScreen) {
		super("anvilclient.mainConfigGui.title", parentScreen);
	}
	
	public MainConfigGui() {
		super("anvilclient.mainConfigGui.title");
	}
	
	@Override
	protected void init() {
		super.init();
	}

	@Override
	protected void addOptions() {
		this.optionsRowList.addOption(new ClickButton("anvilclient.configGui.fullbright.title", (unused) -> this.minecraft.displayGuiScreen(new FullbrightConfigGui(this))));
	}
}
