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

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.features.Fullbright;
import anvilclient.anvilclient.gui.config.MainConfigGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AnvilClient.MOD_ID, bus = Bus.FORGE)
public class KeyInputHandler{
	
	private static final ConfigManager configManager = ConfigManager.getInstance();

	@SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        if (Keybinds.openSettings.isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new MainConfigGui());
        }
        if (Keybinds.fullbright.isPressed()) {
        	configManager.toggleFullbright();
        	configManager.save();
        	Fullbright.update();
        }
        if (Keybinds.coordinates.isPressed()) {
        	configManager.toggleCoordinates();
        	configManager.save();
        }
        if (Keybinds.autoTool.isPressed()) {
        	configManager.toggleAutoTool();
        	configManager.save();
        }
    }
}