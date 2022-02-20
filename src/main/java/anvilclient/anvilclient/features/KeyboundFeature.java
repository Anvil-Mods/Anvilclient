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

import org.lwjgl.glfw.GLFW;

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.settings.Setting;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class KeyboundFeature extends Feature {

	@Setting
	protected KeyMapping keybind = new KeyMapping("anvilclient.feature." + getName() + ".toggle",
			KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, AnvilClient.KEY_CATEGORY);

	public abstract void onKey(KeyInputEvent event);

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		onKey(event);
	}

	@Override
	public void register() {
		super.register();
		ClientRegistry.registerKeyBinding(keybind);
	}

}
