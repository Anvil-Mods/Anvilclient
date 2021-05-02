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

public abstract class NumberSetting<T extends Number> extends AbstractSetting<T> {
	
	protected float stepSize;

	protected NumberSetting(String name, String description, T defaultValue, T minValue, T maxValue, float stepSizeIn) {
		super(name, description, defaultValue, minValue, maxValue);
		this.stepSize = stepSizeIn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Number newValue) {
		super.setValue((T) newValue);
	}

	public byte getByteValue() {
		return (Byte) this.getValue();
	}

	public short getShortValue() {
		return (Short) this.getValue();
	}

	public int getIntValue() {
		return (Integer) this.getValue();
	}

	public long getLongValue() {
		return (Long) this.getValue();
	}

	public float getFloatValue() {
		return (Float) this.getValue();
	}

	public double getDoubleValue() {
		return (Double) this.getValue();
	}
	
	public float getStepSize() {
		return stepSize;
	}
}
