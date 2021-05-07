package anvilclient.anvilclient.features;

import net.minecraft.util.text.TranslationTextComponent;

public enum FeatureCategory {
	GRAPHIC,
	INFO,
	BUILDING;
	
	private final String name;
	
	private final TranslationTextComponent translationTextComponent;
	
	private FeatureCategory() {
		this.name = "featureCategory." + this.toString().toLowerCase();
		this.translationTextComponent = new TranslationTextComponent(name);
	}
	
	public String getName() {
		return name;
	}

	public TranslationTextComponent getTranslationTextComponent() {
		return translationTextComponent;
	}
	
	public String getTranslatedName() {
		return translationTextComponent.getString();
	}

}
