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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractSetting<T> implements ISetting<T> {
	
	protected final String name;
	protected final String description;
	
	
	
	protected final T defaultValue;
	protected final T minValue;
	protected final T maxValue;
	protected T value;
	
	protected AbstractSetting(@Nonnull String name, @Nonnull String description, @Nonnull T defaultValue, @Nullable T minValue, @Nullable T maxValue) {
		this.name = name;
		this.description = description;
		this.defaultValue = defaultValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = this.defaultValue;
	}
	
	@Override
	public void setValue(T newValue) {
		this.value = newValue;
		ConfigManager.getInstance().setProperty(name, valueToString());
	}
	
	@Override
	public T getValue() {
		return this.value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public T getDefaultValue() {
		return defaultValue;
	}
	
	public T getMinValue() {
		return minValue;
	}
	
	public T getMaxValue() {
		return maxValue;
	}
	
	public abstract String valueToString();
	
	public abstract T stringToValue(String string);
	
	public void loadValue(String string) {
		try {
			this.value = this.stringToValue(string);
		} catch (Exception e) {
			this.value = this.defaultValue;
			e.printStackTrace();
		}
	}
	
}
