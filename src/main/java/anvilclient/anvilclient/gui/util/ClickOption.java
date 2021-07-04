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

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class ClickOption extends AbstractOption implements Button.IPressable{
	
	protected String translationKey;
	protected Button.IPressable pressedAction;

	public ClickOption(String translationKeyIn, Button.IPressable pressedAction) {
		super(translationKeyIn);
		this.translationKey = translationKeyIn;
		this.pressedAction = pressedAction;
	}

	@Override
	public Widget createButton(GameSettings options, int xIn, int yIn, int widthIn) {
		return new Button(xIn, yIn, widthIn, 20, new TranslationTextComponent(translationKey), pressedAction);
	}

	@Override
	public void onPress(Button p_onPress_1_) {
	}
}
