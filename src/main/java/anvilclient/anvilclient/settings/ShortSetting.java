/*******************************************************************************
 * Copyright (C) 2021 Anvil-Mods
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
 *******************************************************************************/
package anvilclient.anvilclient.settings;

public class ShortSetting extends NumberSetting<Short> {

	public ShortSetting(String name, String description, Short defaultValue, Short minValue, Short maxValue,
			float stepSizeIn) {
		super(name, description, defaultValue, minValue, maxValue, stepSizeIn, 0);
	}

	@Override
	public void setValue(Number newValue) {
		this.value = newValue.shortValue();
	}

	@Override
	public String valueToString() {
		return Short.toString(this.value);
	}

	@Override
	public Short stringToValue(String string) {
		return Short.parseShort(string);
	}

}
