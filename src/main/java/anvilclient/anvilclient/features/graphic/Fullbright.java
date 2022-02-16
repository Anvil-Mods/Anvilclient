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
package anvilclient.anvilclient.features.graphic;

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.settings.DoubleSetting;
import anvilclient.anvilclient.settings.IgnoreAsOption;
import anvilclient.anvilclient.settings.Setting;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.Minecraft;

public class Fullbright extends TogglableFeature {

	@Override
	public String getName() {
		return "fullbright";
	}
	
	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.GRAPHIC;
	}
	
	private Options gameSettings = Minecraft.getInstance().options;
	
	private boolean vanillaGammaInitialized = true;
	
	@Setting
	public DoubleSetting fullbrightLevel = new DoubleSetting(getName() + ".level", "", 12.0, 0.0, 12.0, 0.1F, 1);
	
	@Setting
	@IgnoreAsOption
	public DoubleSetting vanillaGamma = new DoubleSetting(getName() + ".vanillaGamma", "", 1.0, 0.0, 1.0, 0.01F, 2);
	
	public void update() {
		if (isEnabled()) {
			if (!vanillaGammaInitialized && Option.GAMMA.get(gameSettings) <= 1.0) {
				vanillaGamma.setValue(Option.GAMMA.get(gameSettings));
			}
			Option.GAMMA.set(gameSettings, fullbrightLevel.getValue());
		} else if (!isEnabled()){
			Option.GAMMA.set(gameSettings, vanillaGamma.getValue());
			AnvilClient.LOGGER.debug("Gamma set to VanillaGammma " + Option.GAMMA.get(gameSettings));
			vanillaGammaInitialized = false;
		}
	}
	
	@Override
	public void toggleEnabled() {
		super.toggleEnabled();
		this.update();
	}
	
	@Override
	public void setEnabled(Boolean newEnabled) {
		super.setEnabled(newEnabled);
		this.update();
	}
	
	@Override
	public void enable() {
		super.enable();
		this.update();
	}
	
	@Override
	public void disable() {
		super.disable();
		this.update();
	}
}
