/*
 * Copyright (C) 2021-2023 Ambossmann <https://github.com/Ambossmann>
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
package anvilclient.settings;

import anvilclient.AnvilclientCommon;
import dev.architectury.platform.Platform;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class ConfigManager {

    private static final ConfigManager INSTANCE = new ConfigManager();

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    private ConfigManager() {
    }

    private Properties properties = new Properties();

    private File configFile = new File(Platform.getConfigFolder() + File.separator + "anvilclient.cfg");

    public void loadProperties() {
        load();
        for (ISetting<?> setting : SettingRegister.SETTING_LIST) {
            if (properties.containsKey(setting.getKey())) {
                setting.loadValue(properties.getProperty(setting.getKey()));
            } else {
                properties.setProperty(setting.getKey(), setting.valueToString());
            }
        }
        save();
    }

    private void load() {
        try {
            FileReader reader = new FileReader(configFile);
            properties.load(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            AnvilclientCommon.LOGGER.warn("Config file not found.");
        } catch (IOException e) {
            AnvilclientCommon.LOGGER.error("Error loading config file.");
            AnvilclientCommon.LOGGER.catching(e);
        }
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(configFile);
            properties.store(writer, "host settings");
            writer.close();
        } catch (IOException e) {
            AnvilclientCommon.LOGGER.error("Error saving config.");
            AnvilclientCommon.LOGGER.catching(e);
        }
    }

    public void setPropertyWithoutSaving(String key, String value) {
        properties.setProperty(key, value);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        save();
    }

    public void cleanupConfig() {
        Object[] settings = SettingRegister.SETTING_LIST.stream().map(ISetting::getKey).toArray();
        for (Object key : Collections.list(this.properties.keys())) {
            if (Arrays.stream(settings).noneMatch(key::equals)) {
                properties.remove(key);
            }
        }
        save();
    }
}
