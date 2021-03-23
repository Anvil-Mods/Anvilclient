package anvilclient.anvilclient.gui.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;

public class CoordinatesConfigGui extends ConfigScreen {

	public CoordinatesConfigGui(Screen parentScreen) {
		super("anvilclient.configGui.coordinates.title", parentScreen);
	}
	
	public CoordinatesConfigGui() {
		super("anvilclient.configGui.coordinates.title");
	}
	
	@Override
	protected void addOptions() {
		this.optionsRowList.addOption(new BooleanOption(
                "anvilclient.configGui.coordinates.toggle",
                unused -> configManager.getCoordinates(),
                (unused, newValue) -> configManager.setCoordinates(newValue)
        ));
	}
}
