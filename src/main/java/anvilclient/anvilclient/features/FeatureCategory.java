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
package anvilclient.anvilclient.features;

import anvilclient.anvilclient.settings.SettingSuitableEnum;
import net.minecraft.util.text.TranslationTextComponent;

public enum FeatureCategory implements SettingSuitableEnum{
	GRAPHIC,
	INFO,
	BUILDING,
	MISCELLANEOUS;
	
	private final String translationKey;
	
	private final TranslationTextComponent translationTextComponent;
	
	private FeatureCategory() {
		this.translationKey = "anvilclient.featureCategory." + this.toString().toLowerCase();
		this.translationTextComponent = new TranslationTextComponent(translationKey);
	}
	
	@Override
	public String getTranslationKey() {
		return translationKey;
	}

	@Override
	public TranslationTextComponent getTranslationTextComponent() {
		return translationTextComponent;
	}
	
	@Override
	public String getTranslatedName() {
		return translationTextComponent.getString();
	}

}
