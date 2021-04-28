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
package anvilclient.anvilclient.util;

import org.lwjgl.glfw.GLFW;

import anvilclient.anvilclient.AnvilClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybinds {
	public static final KeyBinding openSettings = new KeyBinding("anvilclient.key.openSettings",
			KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, AnvilClient.KEY_CATEGORY);
	public static final KeyBinding fullbright = new KeyBinding("anvilclient.configGui.fullbright.toggle",
			KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, AnvilClient.KEY_CATEGORY);
	public static final KeyBinding coordinates = new KeyBinding("anvilclient.configGui.coordinates.toggle",
			KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, AnvilClient.KEY_CATEGORY);
	public static final KeyBinding autoTool = new KeyBinding("anvilclient.configGui.autoTool.toggle",
			KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, AnvilClient.KEY_CATEGORY);

	public static void register() {
		ClientRegistry.registerKeyBinding(openSettings);
		ClientRegistry.registerKeyBinding(fullbright);
		ClientRegistry.registerKeyBinding(coordinates);
		ClientRegistry.registerKeyBinding(autoTool);
	}
}