/*******************************************************************************
 * Copyright (C) 2021, 2022 Anvil-Mods
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

public class EnumSetting<T extends Enum<T> & SettingSuitableEnum> extends AbstractSetting<T> {

	public EnumSetting(String name, String description, T defaultValue) {
		super(name, description, defaultValue, null, null);
	}

	@SuppressWarnings("unchecked")
	public void setValue(Enum<T> newValue) {
		super.setValue((T) newValue);
	}

	@SuppressWarnings("unchecked")
	public void setValueRaw(Object newValue) {
		if (newValue != null && this.value.getClass().equals(newValue.getClass())) {
			super.setValue((T) newValue);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String valueToString() {
		return this.value.toString();
	}

	@Override
	public T stringToValue(String string) {
		return Enum.valueOf(this.value.getDeclaringClass(), string);
	}

	public T[] getValues() {
		return this.value.getDeclaringClass().getEnumConstants();
	}

}
