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
package anvilclient.features;

import anvilclient.features.building.AutoTool;
import anvilclient.features.graphic.Fullbright;
import anvilclient.features.info.CPSDisplay;
import anvilclient.features.info.Coordinates;
import anvilclient.features.info.FPSDisplay;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Features {

	public static final AutoTool AUTO_TOOL = new AutoTool();
	public static final Coordinates COORDINATES = new Coordinates();
	public static final Fullbright FULLBRIGHT = new Fullbright();
//	public static final BedwarsInfo BEDWARS_INFO = new BedwarsInfo();
	public static final FPSDisplay FPS_DISPLAY = new FPSDisplay();
	public static final CPSDisplay CPS_DISPLAY = new CPSDisplay();

	public static final List<Feature> FEATURE_LIST = new ArrayList<>();

	public static final HashMap<FeatureCategory, List<Feature>> FEATURE_LIST_BY_CATEGORY = new HashMap<>();

	public static void init() {
		for (Field featureField : Features.class.getDeclaredFields()) {
			if (Modifier.isStatic(featureField.getModifiers())
					&& Feature.class.isAssignableFrom(featureField.getType())) {
				try {
					FEATURE_LIST.add((Feature) featureField.get(null));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		for (FeatureCategory featureCategory : FeatureCategory.values()) {
			List<Feature> featureList = new ArrayList<>();
			for (Feature feature : FEATURE_LIST) {
				if (feature.getCategory() == featureCategory) {
					featureList.add(feature);
				}
			}
			FEATURE_LIST_BY_CATEGORY.put(featureCategory, featureList);
		}
	}

	public static void register() {
		for (Feature feature : FEATURE_LIST) {
			feature.register();
		}
	}

}
