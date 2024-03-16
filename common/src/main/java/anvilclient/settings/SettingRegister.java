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

import anvilclient.features.Feature;
import anvilclient.features.Features;
import anvilclient.features.components.BaseComponent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class SettingRegister {

    public static final TreeSet<ISetting<?>> SETTING_LIST = new TreeSet<>();

    public static final HashMap<Feature, TreeSet<ISetting<?>>> SETTING_LIST_FOR_OPTIONS = new HashMap<>();

    public static void registerFeatures() {
        for (Feature feature : Features.FEATURE_LIST) {
            registerFeature(feature);
        }
    }

    public static boolean register(Field setting, Object object) {
        if (isSettingField(setting)) {
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

    public static void register(ISetting<?> setting) {
        if (!SETTING_LIST.add(setting)) {
            throw new UnsupportedOperationException("Setting already registered");
        }
    }

    private static boolean isSettingField(Field setting) {
        return ISetting.class.isAssignableFrom(setting.getType()) && setting.isAnnotationPresent(Setting.class);
    }

    public static void registerObject(Object object) {
        Arrays.stream(object.getClass().getFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(SettingRegister::isSettingField)
                .map((field) -> {
                    try {
                        return (ISetting<?>) field.get(object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(SettingRegister::register);
    }

    public static void registerFeature(Feature feature) {
        TreeSet<ISetting<?>> settingSet = SETTING_LIST_FOR_OPTIONS.get(feature);
        if (settingSet == null) {
            settingSet = new TreeSet<>();
        }
        TreeSet<ISetting<?>> finalSettingSet = settingSet;
        Arrays.stream(feature.getClass().getFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(SettingRegister::isSettingField)
                .forEach(((field -> registerFeatureSetting(feature, finalSettingSet, field, feature))));
        for (BaseComponent component : feature.getComponents()) {
            Arrays.stream(component.getClass().getFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .filter(SettingRegister::isSettingField)
                    .forEach(((field -> registerFeatureSetting(feature, finalSettingSet, field, component))));
        }

        SETTING_LIST_FOR_OPTIONS.put(feature, finalSettingSet);
    }

    private static void registerFeatureSetting(Feature feature, TreeSet<ISetting<?>> settingSet, Field field,
                                               Object object) {
        try {
            ISetting<?> setting = (ISetting<?>) field.get(object);
            register(setting);
            if (!field.isAnnotationPresent(IgnoreAsOption.class) && !settingSet.add(setting)) {
                throw new UnsupportedOperationException("Setting already registered");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public static void registerClass(Class<?> clazz) {
        Arrays.stream(clazz.getFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .forEach(field -> register(field, null));
    }

}
