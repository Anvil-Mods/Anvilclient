package anvilclient.anvilclient.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import anvilclient.anvilclient.AnvilClient;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

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
	}

	public void save() {
		SPEC.save();
		SettingManager.getInstance().update();
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

	public Double getVanillaGamma() {
		return vanillaGamma.get();
	}

	public void setVanillaGamma(Double newValue) {
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
}
