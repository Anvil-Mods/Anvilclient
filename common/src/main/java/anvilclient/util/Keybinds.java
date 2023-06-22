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
package anvilclient.util;

import anvilclient.AnvilclientCommon;
import anvilclient.util.utils.ScreenUtils;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static final KeyMapping OPEN_SETTINGS = new KeyMapping("anvilclient.key.openSettings",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, AnvilclientCommon.KEY_CATEGORY);

    public static void register() {
        KeyMappingRegistry.register(OPEN_SETTINGS);
        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (OPEN_SETTINGS.consumeClick()) {
                Minecraft.getInstance().setScreen(ScreenUtils.getMainConfigGui(null));
            }
        });
    }


}