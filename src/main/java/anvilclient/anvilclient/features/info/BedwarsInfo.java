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
package anvilclient.anvilclient.features.info;

import java.time.Instant;

import com.mojang.blaze3d.matrix.MatrixStack;

import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.KeyboundFeature;
import anvilclient.anvilclient.gui.util.Utils;
import anvilclient.anvilclient.util.ServerDetector;
import anvilclient.anvilclient.util.ServerDetector.Server;
import anvilclient.anvilclient.util.TextComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.DownloadTerrainScreen;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BedwarsInfo extends KeyboundFeature {

	@Override
	public String getName() {
		return "bedwarsInfo";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}

	private static final String START_TEXT = "\u00A7f\u00A7lBed Wars\u00A7r";

	private static final String REJOIN_TEXT = "\u00A7e\u00A7lTo leave Bed Wars, type /lobby\u00A7r";

	private boolean inBedWars = false;

	private long gameStartTime = Instant.now().getEpochSecond();

	@SubscribeEvent
	public void update(GuiOpenEvent event) {
		if (inBedWars && event.getGui() instanceof DownloadTerrainScreen) {
			onGameLeave();
		}
	}

	@SubscribeEvent
	public void update(ClientChatReceivedEvent event) {
		if (ServerDetector.getInstance().getCurrentServer() == Server.HYPIXEL && !inBedWars) {
			String message = TextComponents.getFormattedText(event.getMessage());
			if (message.contains(START_TEXT)) {
				onGameStart();
			} else if (message.contains(REJOIN_TEXT)) {
				onGameRejoin();
			}
		}
	}

	private static final int TEXT_COLOR = 0xFFFFFF;
	private static final int LINE_HEIGHT = 10;

	public void render(int width, int height, MatrixStack matrixStack, Minecraft mc, ClientPlayerEntity player) {
		if (isEnabled() && inBedWars) {
			long elapsedTime = Instant.now().getEpochSecond() - gameStartTime;
			int currentHeight = 0;
			int coordinatesX = (int) (width * 0.01);
			int coordinatesY = (int) (height * 0.25);
			if (gameStartTime != 0) {
				for (Stages stage : Stages.values()) {
					long secs = stage.getSecsTo(elapsedTime);
					if (secs >= 0) {
						AbstractGui.drawString(matrixStack, mc.fontRenderer,
								stage.getName() + ": " + Utils.formatTime(secs), coordinatesX,
								coordinatesY + currentHeight, TEXT_COLOR);
						currentHeight += LINE_HEIGHT + 1;
					}
				}
			}
		}
	}

	private void onGameStart() {
		inBedWars = true;
		gameStartTime = Instant.now().getEpochSecond();
	}

	private void onGameRejoin() {
		if (gameStartTime == 0) {
			inBedWars = true;
		} else {
			Minecraft.getInstance().player.sendMessage(
					new TranslationTextComponent("anvilclient.feature.bedwarsInfo.leftWhileInGame"), Util.DUMMY_UUID);
		}
	}

	private void onGameLeave() {
		inBedWars = false;
	}

	private enum Stages {
		DIAMOND2(6 * 60, "Diamond II"), EMERALD2(12 * 60, "Emerald II"), DIAMOND3(18 * 60, "Diamond III"),
		EMERALD3(24 * 60, "Emerald III"), BED_GONE(30 * 60, "Bed Gone"), SUDDEN_DEATH(40 * 60, "Sudden Death"),
		GAME_OVER(50 * 60, "Game Over");

		private final long SECS;
		private final String NAME;

		private Stages(long secs, String name) {
			this.SECS = secs;
			this.NAME = name;
		}

		public long getSecsTo(long elapsedTime) {
			return this.SECS - elapsedTime;
		}

		public String getName() {
			return NAME;
		}
	}

}
