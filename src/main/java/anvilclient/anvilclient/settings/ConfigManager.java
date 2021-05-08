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
package anvilclient.anvilclient.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import anvilclient.anvilclient.AnvilClient;

public class ConfigManager {

	private static final ConfigManager INSTANCE = new ConfigManager();

	public static ConfigManager getInstance() {
		return INSTANCE;
	}

	private ConfigManager() {
	}

	private Properties properties = new Properties();

	private File configFile = new File(
			Paths.get("").toAbsolutePath().toString() + File.separator + "config" + File.separator + "anvilclient.cfg");

	public void loadProperties() {
		load();
		for (ISetting<?> setting : SettingRegister.SETTING_LIST) {
			if (properties.containsKey(setting.getName())) {
				setting.loadValue(properties.getProperty(setting.getName()));
			} else {
				properties.setProperty(setting.getName(), setting.valueToString());
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
			AnvilClient.LOGGER.warn("Config file not found.");
		} catch (IOException e) {
			AnvilClient.LOGGER.error("Error loading config file.");
			AnvilClient.LOGGER.catching(e);
		}
	}

	public void save() {
		try {
			FileWriter writer = new FileWriter(configFile);
			properties.store(writer, "host settings");
			writer.close();
		} catch (IOException e) {
			AnvilClient.LOGGER.error("Error saving config.");
			AnvilClient.LOGGER.catching(e);
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
		Object[] settings = SettingRegister.SETTING_LIST.stream().map(ISetting::getName).toArray();
		for (Object key : Collections.list(this.properties.keys())) {
			if (!Arrays.stream(settings).anyMatch(key::equals)) {
				properties.remove(key);
			}
		}
		save();
	}
}
