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

public class StringSetting extends AbstractSetting<String> implements CharSequence {

	public StringSetting(String name, String description, String defaultValue, String minValue, String maxValue) {
		super(name, description, defaultValue, minValue, maxValue);
	}

	@Override
	public int length() {
		return getValue().length();
	}

	@Override
	public char charAt(int index) {
		return getValue().charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return getValue().subSequence(start, end);
	}

	@Override
	public String valueToString() {
		return this.value;
	}

	@Override
	public String stringToValue(String string) {
		return string;
	}

}
