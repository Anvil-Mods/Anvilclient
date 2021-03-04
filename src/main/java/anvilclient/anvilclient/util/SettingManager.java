package anvilclient.anvilclient.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import anvilclient.anvilclient.AnvilClient;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class SettingManager {
	public static final SettingManager INSTANCE;

	private static final ForgeConfigSpec SPEC;

	private static final Path CONFIG_PATH = Paths.get("config", AnvilClient.MOD_ID + ".toml");

	static {
		Pair<SettingManager, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SettingManager::new);
		INSTANCE = specPair.getLeft();
		SPEC = specPair.getRight();
		CommentedFileConfig config = CommentedFileConfig.builder(CONFIG_PATH).sync().autoreload()
				.writingMode(WritingMode.REPLACE).build();
		config.load();
		config.save();
		SPEC.setConfig(config);
	}

	private SettingManager(ForgeConfigSpec.Builder configSpecBuilder) {

		fullbright = configSpecBuilder.translation("anvilclient.configGui.fullbright.title")
				.comment(new TranslationTextComponent("anvilclient.configGui.fullbright.description").getString())
				.define("fullbright", false);
		fullbrightLevel = configSpecBuilder.translation("anvilclient.configGui.fullbrightLevel.title")
				.defineInRange("fullbrightLevel", 12.0, 0.0, 12.0);
	}

	public static SettingManager getInstance() {
		return INSTANCE;
	}
	
	public void save() {
		SPEC.save();
	}

	private final BooleanValue fullbright;

	private final DoubleValue fullbrightLevel;

	public boolean getFullbright() {
		return fullbright.get();
	}

	public void setFullbright(boolean newValue) {
		fullbright.set(newValue);
	}

	public Double getFullbrightLevel() {
		return fullbrightLevel.get();
	}

	public void setFullbrightLevel(Double newValue) {
		fullbrightLevel.set(newValue);
	}
}
