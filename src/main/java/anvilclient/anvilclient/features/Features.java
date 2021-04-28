package anvilclient.anvilclient.features;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Features {
	
	public static final AutoTool AUTO_TOOL = new AutoTool();
	public static final Coordinates COORDINATES = new Coordinates();
	public static final Fullbright FULLBRIGHT = new Fullbright();
	
	public static final List<Feature> FEATURE_LIST = new ArrayList<>();
	
	static {
		for (Field featureField : Features.class.getDeclaredFields()) {
			if (Modifier.isStatic(featureField.getModifiers()) && Feature.class.isAssignableFrom(featureField.getType())) {
				try {
					FEATURE_LIST.add((Feature) featureField.get(null));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void register() {
		for (Feature feature : FEATURE_LIST) {
			feature.register();
		}
	}

}
