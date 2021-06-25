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
package anvilclient.anvilclient.features;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anvilclient.anvilclient.features.building.AutoTool;
import anvilclient.anvilclient.features.graphic.Fullbright;
import anvilclient.anvilclient.features.info.BedwarsInfo;
import anvilclient.anvilclient.features.info.Coordinates;
import anvilclient.anvilclient.features.info.FPSDisplay;

public class Features {
	
	public static final AutoTool AUTO_TOOL = new AutoTool();
	public static final Coordinates COORDINATES = new Coordinates();
	public static final Fullbright FULLBRIGHT = new Fullbright();
	public static final BedwarsInfo BEDWARS_INFO = new BedwarsInfo();
	public static final FPSDisplay FPS_DISPLAY = new FPSDisplay();
	
	public static final List<Feature> FEATURE_LIST = new ArrayList<>();
	
	public static final HashMap<FeatureCategory, List<Feature>> FEATURE_LIST_BY_CATEGORY = new HashMap<>();
	
	public static void init() {
		for (Field featureField : Features.class.getDeclaredFields()) {
			if (Modifier.isStatic(featureField.getModifiers()) && Feature.class.isAssignableFrom(featureField.getType())) {
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
