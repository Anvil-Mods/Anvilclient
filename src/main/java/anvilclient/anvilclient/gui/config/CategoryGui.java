package anvilclient.anvilclient.gui.config;

import java.util.List;

import anvilclient.anvilclient.features.Feature;
import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.Features;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.gui.util.ClickOption;
import anvilclient.anvilclient.gui.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

public class CategoryGui extends ConfigScreen {
	
	FeatureCategory category;

	public CategoryGui(FeatureCategory category, Screen parentScreen) {
		super(category.getTranslationKey(), parentScreen);
		this.category = category;
	}

	@Override
	protected void addOptions() {
		List<Feature> featureList = Features.FEATURE_LIST_BY_CATEGORY.get(category);
		
		for (Feature feature : featureList) {
			if (TogglableFeature.class.isAssignableFrom(feature.getClass())) {
				this.optionsRowList
						.addOptions(Utils.getOptionListForTogglableFeature((TogglableFeature) feature, this));
			} else {
				this.optionsRowList.addOption(new ClickOption("anvilclient.feature." + feature.getName(),
						button -> Minecraft.getInstance().displayGuiScreen(new FeatureGui(feature, this))));
			}
		}
		
	}

}
