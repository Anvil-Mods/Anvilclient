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

package anvilclient.anvilclient;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import anvilclient.anvilclient.features.Features;
import anvilclient.anvilclient.settings.ConfigManager;
import anvilclient.anvilclient.settings.SettingRegister;
import anvilclient.anvilclient.util.EventManager;
import anvilclient.anvilclient.util.Keybinds;
import anvilclient.anvilclient.util.ServerDetector;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod(AnvilClient.MOD_ID)
public class AnvilClient {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "anvilclient";
	public static final String KEY_CATEGORY = "anvilclient.key.categories.anvilclient";

	public AnvilClient() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> anvilclient.anvilclient.util.utils.ScreenUtils.registerForgeConfig());
		
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		Keybinds.register();
		EventManager.getInstance().registerOnEventBus();
		EventManager.FORGE_EVENT_BUS.register(ServerDetector.getInstance());
		Features.init();
		Features.register();
		SettingRegister.registerFeatures();
		SettingRegister.registerStaticClass(anvilclient.anvilclient.gui.config.ConfigScreen.class);
		ConfigManager.getInstance().loadProperties();
		ConfigManager.getInstance().cleanupConfig();
	}

	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
	}

}
