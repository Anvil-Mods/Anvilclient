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

public class ByteSetting extends NumberSetting<Byte> {

	public ByteSetting(String name, String description, Byte defaultValue, Byte minValue, Byte maxValue,
			float stepSizeIn) {
		super(name, description, defaultValue, minValue, maxValue, stepSizeIn, 0);
	}

	@Override
	public void setValue(Number newValue) {
		this.value = newValue.byteValue();
	}

	@Override
	public String valueToString() {
		return Byte.toString(this.value);
	}

	@Override
	public Byte stringToValue(String string) {
		return Byte.parseByte(string);
	}

}
