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
package anvilclient.anvilclient.gui.util;

import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TranslatableComponent;

public class ClickOption extends Option implements Button.OnPress{
	
	protected String translationKey;
	protected Button.OnPress pressedAction;

	public ClickOption(String translationKeyIn, Button.OnPress pressedAction) {
		super(translationKeyIn);
		this.translationKey = translationKeyIn;
		this.pressedAction = pressedAction;
	}

	@Override
	public AbstractWidget createButton(Options options, int xIn, int yIn, int widthIn) {
		return new Button(xIn, yIn, widthIn, 20, new TranslatableComponent(translationKey), pressedAction);
	}

	@Override
	public void onPress(Button p_onPress_1_) {
	}
}
