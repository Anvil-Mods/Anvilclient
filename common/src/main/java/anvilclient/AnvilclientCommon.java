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
package anvilclient;

import anvilclient.features.Features;
import anvilclient.settings.ConfigManager;
import anvilclient.settings.SettingRegister;
import anvilclient.util.Keybinds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilclientCommon {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "anvilclient";
    public static final String KEY_CATEGORY = "anvilclient.key.categories.anvilclient";

    public static void init() {
        Keybinds.register();
        Features.init();
        Features.register();
        SettingRegister.registerFeatures();
        SettingRegister.registerClass(anvilclient.gui.config.ConfigScreen.class);
        ConfigManager.getInstance().loadProperties();
        ConfigManager.getInstance().cleanupConfig();
    }
}
