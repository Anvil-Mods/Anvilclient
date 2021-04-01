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
package anvilclient.anvilclient.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.features.Fullbright;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ConfigManager {
	private static final ConfigManager INSTANCE;

	public static ConfigManager getInstance() {
		return INSTANCE;
	}

	private static final ForgeConfigSpec SPEC;

	private static final Path CONFIG_PATH = Paths.get("config", AnvilClient.MOD_ID + ".toml");

	static {
		Pair<ConfigManager, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigManager::new);
		INSTANCE = specPair.getLeft();
		SPEC = specPair.getRight();
		CommentedFileConfig config = CommentedFileConfig.builder(CONFIG_PATH).sync().autoreload()
				.writingMode(WritingMode.REPLACE).build();
		config.load();
		config.save();
		SPEC.setConfig(config);
	}

	private ConfigManager(ForgeConfigSpec.Builder configSpecBuilder) {

		fullbright = configSpecBuilder.define("fullbright", false);
		fullbrightLevel = configSpecBuilder.defineInRange("fullbrightLevel", 12.0, 0.0, 12.0);
		vanillaGamma = configSpecBuilder.defineInRange("vanillaGamma", 1.0, 0.0, 1.0);

		coordinates = configSpecBuilder.define("coordinates", false);
		
		autoTool = configSpecBuilder.define("autoTool", false);
		autoToolMinDurability = configSpecBuilder.defineInRange("autoToolMinDurability", 5, 0, Byte.MAX_VALUE);
		autoToolRevertTool = configSpecBuilder.define("autoToolRevertTool", true);
	}

	public void save() {
		SPEC.save();
		Fullbright.update();
	}

	private final BooleanValue fullbright;

	public boolean getFullbright() {
		return fullbright.get();
	}

	public void setFullbright(boolean newValue) {
		fullbright.set(newValue);
	}

	public void toggleFullbright() {
		fullbright.set(!fullbright.get());
	}

	private final DoubleValue fullbrightLevel;

	public Double getFullbrightLevel() {
		return fullbrightLevel.get();
	}

	public void setFullbrightLevel(Double newValue) {
		fullbrightLevel.set(newValue);
	}

	private final DoubleValue vanillaGamma;

	public double getVanillaGamma() {
		return vanillaGamma.get();
	}

	public void setVanillaGamma(double newValue) {
		vanillaGamma.set(newValue);
	}

	private final BooleanValue coordinates;

	public boolean getCoordinates() {
		return coordinates.get();
	}

	public void setCoordinates(boolean newValue) {
		coordinates.set(newValue);
	}
	
	public void toggleCoordinates() {
		coordinates.set(!coordinates.get());
	}
	
	private final BooleanValue autoTool;
	
	public boolean getAutoTool() {
		return autoTool.get();
	}
	
	public void setAutoTool(boolean newValue) {
		autoTool.set(newValue);
	}
	
	public void toggleAutoTool() {
		autoTool.set(!autoTool.get());
	}
	
	private final IntValue autoToolMinDurability;
	
	public int getAutoToolMinDurability() {
		return autoToolMinDurability.get();
	}

	public void setAutoToolMinDurability(int newValue) {
		autoToolMinDurability.set(newValue);
	}
	
	private final BooleanValue autoToolRevertTool;
	
	public boolean getAutoToolRevertTool() {
		return autoToolRevertTool.get();
	}

	public void setAutoToolRevertTool(boolean newValue) {
		autoToolRevertTool.set(newValue);
	}

	public void toggleAutoToolRevertTool() {
		autoToolRevertTool.set(!autoToolRevertTool.get());
	}
}
