/*
 * Copyright (C) 2023 Ambossmann <https://github.com/Ambossmann>
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
package de.ambossmann.anvilclient.features.components;

import de.ambossmann.anvilclient.features.Feature;
import de.ambossmann.anvilclient.settings.BooleanSetting;
import de.ambossmann.anvilclient.settings.Setting;

import java.util.function.Consumer;

public class ToggleComponent extends BaseComponent {

    @Setting
    public final BooleanSetting enabled;

    private final Consumer<Boolean> updateHook;

    public ToggleComponent(Feature parentFeature, String toggleName, String description, boolean defaultValue, Consumer<Boolean> updateHook) {
        super(parentFeature);
        enabled = new BooleanSetting(parentFeature.getName() + ".toggle." + toggleName, description, defaultValue);
        this.updateHook = updateHook;
    }

    public ToggleComponent(Feature parentFeature, String toggleName, String description, boolean defaultValue) {
        this(parentFeature, toggleName, description, defaultValue, (b) -> {});
    }

    public void setEnabled(Boolean newEnabled) {
        this.enabled.setValue(newEnabled);
    }

    public boolean isEnabled() {
        return this.enabled.getValue();
    }

    public void toggleEnabled() {
        this.enabled.toggle();
        updateHook.accept(this.enabled.getValue());
    }

    public void enable() {
        this.enabled.enable();
        updateHook.accept(this.enabled.getValue());
    }

    public void disable() {
        this.enabled.disable();
        updateHook.accept(this.enabled.getValue());
    }

}
