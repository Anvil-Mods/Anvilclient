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
import java.util.HashMap;
import java.util.List;

import anvilclient.anvilclient.features.Feature;
import anvilclient.anvilclient.features.Features;

public class SettingRegister {

	public static final ArrayList<ISetting<?>> SETTING_LIST = new ArrayList<>();

	public static final HashMap<Feature, List<ISetting<?>>> SETTING_LIST_FOR_OPTIONS = new HashMap<>();

	public static void registerFeatures() {
		registerClasses(Features.FEATURE_LIST);
		for (Feature feature : Features.FEATURE_LIST) {
			List<ISetting<?>> settingList = new ArrayList<>();
			for (Field setting : feature.getClass().getFields()) {
				if (ISetting.class.isAssignableFrom(setting.getType()) && setting.isAnnotationPresent(Setting.class)
						&& !setting.isAnnotationPresent(IgnoreAsOption.class)) {
					try {
						settingList.add((ISetting<?>) setting.get(feature));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			SETTING_LIST_FOR_OPTIONS.put(feature, settingList);
		}
	}

	public static boolean register(Field setting, Object object) {
		if (ISetting.class.isAssignableFrom(setting.getType()) && setting.isAnnotationPresent(Setting.class)) {
			try {
				return SETTING_LIST.add((ISetting<?>) setting.get(object));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public static void registerClass(Object object) {
		Arrays.stream(object.getClass().getFields()).filter(field -> !Modifier.isStatic(field.getModifiers()))
				.forEach(field -> register(field, object));
	}

	public static void registerStaticClass(Class<?> clazz) {
		Arrays.stream(clazz.getFields()).filter(field -> Modifier.isStatic(field.getModifiers()))
				.forEach(field -> register(field, null));
	}

	public static void registerClasses(List<?> objects) {
		objects.forEach(SettingRegister::registerClass);
	}

}
