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

public class CharacterSetting extends AbstractSetting<Character> implements CharSequence {

	public CharacterSetting(String name, String description, Character defaultValue) {
		super(name, description, defaultValue, null, null);
	}

	@Override
	public int length() {
		return 1;
	}

	@Override
	public char charAt(int index) {
		if (index == 0) {
			return getValue();
		} else {
			throw new IndexOutOfBoundsException("Tried to get character at index #" + index + "; only #0 is allowed");
		}
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		if (start == 0 && end == 1) {
			return getValue().toString();
		} else {
			throw new IndexOutOfBoundsException(
					"Cannot copy sequence index " + start + " to " + end + "; must be a range between 0 and 1");
		}
	}

	@Override
	public String valueToString() {
		return Character.toString(this.value);
	}

	@Override
	public Character stringToValue(String string) {
		return string.charAt(0);
	}

}
