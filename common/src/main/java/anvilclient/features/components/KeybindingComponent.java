/*
 * Copyright (C) 2023-2024 Ambossmann <https://github.com/Ambossmann>
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
package anvilclient.features.components;

import anvilclient.features.Feature;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class KeybindingComponent extends BaseComponent {

	public KeybindingComponent(Feature parentFeature, KeyMapping keyMapping, Runnable keyAction) {
		super(parentFeature);
		this.keyMapping = keyMapping;
		this.keyAction = keyAction;
	}

	private final KeyMapping keyMapping;
	private final Runnable keyAction;

	@Override
	public void register() {
		KeyMappingRegistry.register(keyMapping);
		ClientTickEvent.CLIENT_POST.register(this::tick);
	}

	private void tick(Minecraft minecraft) {
		while (keyMapping.consumeClick()) {
			keyAction.run();
		}
	}
}
