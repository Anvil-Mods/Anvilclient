/*
 * Copyright (C) 2021-2023 Ambossmann <https://github.com/Ambossmann>
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
 */
package anvilclient.settings;

public class BooleanSetting extends AbstractSetting<Boolean> {

	public BooleanSetting(String name, String description, Boolean defaultValue) {
		super(name, description, defaultValue, null, null);
	}

	public void toggle() {
		setValue(!getValue());
	}

	public void enable() {
		setValue(true);
	}

	public void disable() {
		setValue(false);
	}

	@Override
	public String valueToString() {
		return Boolean.toString(this.value);
	}

	@Override
	public Boolean stringToValue(String string) {
		return Boolean.parseBoolean(string);
	}
}
