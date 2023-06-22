/*******************************************************************************
 * Copyright (C) 2022 Anvil-Mods
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
package anvilclient.util.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;

public class HudUtils {

    public static final int DEFAULT_LINE_HEIGHT = 11;

    public static boolean shouldRender() {
        Options options = getMinecraft().options;
        return !options.hideGui && !options.renderDebug;
    }

    public static Font getFont() {
        return getMinecraft().font;
    }

    public static int getScreenWidth() {
        return getMinecraft().getWindow().getGuiScaledWidth();
    }

    public static int getScreenHeight() {
        return getMinecraft().getWindow().getGuiScaledHeight();
    }

    private static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

}
