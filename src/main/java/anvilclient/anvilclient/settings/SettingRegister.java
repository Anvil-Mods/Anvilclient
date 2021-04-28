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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

import anvilclient.anvilclient.features.Feature;

public class SettingRegister {
	
	private static final SettingRegister INSTANCE = new SettingRegister();

	public static SettingRegister getInstance() {
		return INSTANCE;
	}
	
	private ArrayList<AbstractSetting<?>> settingList;
	
	private SettingRegister() {
	}
	
	public boolean register(Field setting, Feature feature) {
		if (setting.getType() == AbstractSetting.class && setting.getAnnotation(Setting.class) != null && feature.getClass().isAssignableFrom(AbstractSetting.class)) {
			try {
				return settingList.add((AbstractSetting<?>) setting.get(feature));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void registerClass(Feature feature) {
		Arrays.stream(feature.getClass().getFields())
			.filter(field -> !Modifier.isStatic(field.getModifiers()))
			.forEach(field -> register(field, feature));
	}
	
	public void registerStaticClass(Class<AbstractSetting<?>> clazz) {
		Arrays.stream(clazz.getFields())
			.filter(field -> Modifier.isStatic(field.getModifiers()))
			.forEach(field -> register(field, null));
	}

}
