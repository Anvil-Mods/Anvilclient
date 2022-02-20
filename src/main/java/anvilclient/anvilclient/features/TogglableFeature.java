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
package anvilclient.anvilclient.features;

import anvilclient.anvilclient.settings.BooleanSetting;
import anvilclient.anvilclient.settings.Setting;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;

public abstract class TogglableFeature extends KeyboundFeature {

	@Setting
	public BooleanSetting enabled = new BooleanSetting(getName() + ".enabled", "", false);

	public void setEnabled(Boolean newEnabled) {
		this.enabled.setValue(newEnabled);
	}

	public boolean isEnabled() {
		return this.enabled.getValue().booleanValue();
	}

	public void toggleEnabled() {
		this.enabled.toggle();
	}

	public void enable() {
		this.enabled.enable();
	}

	public void disable() {
		this.enabled.disable();
	}

	@Override
	public void onKey(KeyInputEvent event) {
		if (keybind.consumeClick()) {
			toggleEnabled();
		}
	}

}
