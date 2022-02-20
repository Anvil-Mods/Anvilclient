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

public abstract class NumberSetting<T extends Number> extends AbstractSetting<T> {

	protected float stepSize;
	protected int decimalCount;

	protected NumberSetting(String name, String description, T defaultValue, T minValue, T maxValue, float stepSizeIn,
			int decimalCountIn) {
		super(name, description, defaultValue, minValue, maxValue);
		this.stepSize = stepSizeIn;
		this.decimalCount = decimalCountIn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Number newValue) {
		super.setValue((T) newValue);
	}

	public byte getByteValue() {
		return value.byteValue();
	}

	public short getShortValue() {
		return value.shortValue();
	}

	public int getIntValue() {
		return value.intValue();
	}

	public long getLongValue() {
		return value.longValue();
	}

	public float getFloatValue() {
		return value.floatValue();
	}

	public double getDoubleValue() {
		return value.doubleValue();
	}

	public float getStepSize() {
		return stepSize;
	}

	public int getDecimalCount() {
		return decimalCount;
	}
}
