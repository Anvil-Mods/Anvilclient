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
package anvilclient.features.components;

import anvilclient.features.Feature;

import java.util.function.Consumer;

public class FeatureToggleComponent extends ToggleComponent {
    public FeatureToggleComponent(Feature parentFeature, String description, boolean defaultValue, Consumer<Boolean> updateHook) {
        super(parentFeature, "featureEnabled", description, defaultValue, updateHook);
    }

    public FeatureToggleComponent(Feature parentFeature, String description, boolean defaultValue) {
        super(parentFeature, "featureEnabled", description, defaultValue);
    }
}
