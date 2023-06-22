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
package anvilclient.settings;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AbstractSetting<T> implements ISetting<T> {

    protected final String name;
    protected final String description;

    protected final T defaultValue;
    protected final T minValue;
    protected final T maxValue;
    protected T value;

    protected AbstractSetting(String name, String description, T defaultValue, T minValue, T maxValue) {
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

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public T getMinValue() {
        return minValue;
    }

    @Override
    public T getMaxValue() {
        return maxValue;
    }

    @Override
    public void loadValue(String string) {
        try {
            this.setValue(this.stringToValue(string));
        } catch (Exception e) {
            this.setValue(this.defaultValue);
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(@NotNull ISetting<T> o) {
        return name.compareTo(o.getKey());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSetting<?> that = (AbstractSetting<?>) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
