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
package anvilclient.settings;

public interface ISetting<T> extends Comparable<ISetting<T>> {

	T getValue();

	T getDefaultValue();

	public T getMinValue();

	public T getMaxValue();

	void setValue(T newValue);

	String getKey();

	String getDescription();

	String valueToString();

	T stringToValue(String string);

	void loadValue(String string);
}
