package anvilclient.anvilclient.gui.config;

import java.util.List;

import anvilclient.anvilclient.features.Feature;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.gui.util.Utils;
import anvilclient.anvilclient.settings.ISetting;
import anvilclient.anvilclient.settings.SettingRegister;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;

public class FeatureGui extends ConfigScreen {

	private final Feature feature;

	public FeatureGui(Feature feature, Screen parentScreen) {
		super("anvilclient.feature." + feature.getName() + "title", parentScreen);
		this.feature = feature;
	}

	@Override
	protected void addOptions() {
		List<ISetting<?>> settingList = SettingRegister.SETTING_LIST_FOR_OPTIONS.get(feature);
		
		if (TogglableFeature.class.isAssignableFrom(feature.getClass())) {
			this.optionsRowList.addOption(new BooleanOption("anvilclient.feature." + feature.getName() + ".toggle",
					unused -> ((TogglableFeature) feature).isEnabled(),
					(unused, newValue) -> ((TogglableFeature) feature).setEnabled(newValue)));
		}
		
		for (ISetting<?> setting : settingList.stream().filter((setting) -> !setting.getName().contains(".enabled"))
				.toArray(ISetting<?>[]::new)) {
			AbstractOption option = Utils.getOptionForSetting(setting);
			if (option != null) {
				this.optionsRowList.addOption(option);
			}
		}
	}

}
