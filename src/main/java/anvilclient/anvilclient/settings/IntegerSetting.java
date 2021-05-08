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
package anvilclient.anvilclient.settings;

public class IntegerSetting extends NumberSetting<Integer> {

	public IntegerSetting(String name, String description, Integer defaultValue, Integer minValue,
			Integer maxValue, float stepSizeIn) {
		super(name, description, defaultValue, minValue, maxValue, stepSizeIn, 0);
	}
	
	@Override
	public void setValue(Number newValue) {
		this.value = newValue.intValue();
	}

	@Override
	public String valueToString() {
		return Integer.toString(this.value);
	}

	@Override
	public Integer stringToValue(String string) {
		return Integer.parseInt(string);
	}

}
