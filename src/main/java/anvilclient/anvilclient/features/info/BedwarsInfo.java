/*******************************************************************************
 * Copyright (C) 2021, 2022 Anvil-Mods
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package anvilclient.anvilclient.features.info;

import java.time.Instant;
import java.util.Arrays;

import com.mojang.blaze3d.vertex.PoseStack;

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.util.ScoreboardReader;
import anvilclient.anvilclient.util.ServerDetector;
import anvilclient.anvilclient.util.ServerDetector.Server;
import anvilclient.anvilclient.util.utils.HudUtils;
import anvilclient.anvilclient.util.utils.TextUtils;
import anvilclient.anvilclient.util.utils.TimeUtils;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.ScreenEvent.Open;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BedwarsInfo extends TogglableFeature implements IGuiOverlay {

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

	private long gameStartTime = Instant.now().toEpochMilli();

	@SubscribeEvent
	public void update(Open event) {
		if (inBedWars && event.getScreen() instanceof ReceivingLevelScreen) {
			onGameLeave();
		}
	}

	@SubscribeEvent
	public void update(ClientChatReceivedEvent event) {
		if (ServerDetector.getInstance().getCurrentServer() == Server.HYPIXEL && !inBedWars) {
			String message = TextUtils.getFormattedText(event.getMessage());
			if (message.contains(START_TEXT)) {
				onGameStart();
			} else if (message.contains(REJOIN_TEXT)) {
				onGameRejoin();
			}
		}
	}

	private static final int TEXT_COLOR = 0xFFFFFF;
	private static final int LINE_HEIGHT = 10;

	@Override
	public void register() {
		super.register();
		GuiOverlayManager.registerOverlayAbove(ForgeGui.HUD_TEXT_ELEMENT, "Bedwars Info", this);
	}

	@Override
	public void render(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
		if (isEnabled() && inBedWars && HudUtils.shouldRender()) {
			long elapsedTime = Instant.now().toEpochMilli() - gameStartTime;
			int currentHeight = 0;
			int coordinatesX = (int) (width * 0.01);
			int coordinatesY = (int) (height * 0.25);
			syncTime();
			if (gameStartTime != 0) {
				for (Stages stage : Stages.values()) {
					long millis = stage.getMillisTo(elapsedTime) + 1000L;
					if (millis >= 0) {
						GuiComponent.drawString(poseStack, HudUtils.getFont(),
								stage.getName() + ": " + TimeUtils.formatTimeMillis(millis), coordinatesX,
								coordinatesY + currentHeight, TEXT_COLOR);
						currentHeight += LINE_HEIGHT + 1;
					}
				}
			}
		}
	}

	private boolean syncTime() {
		for (Stages stage : Stages.values()) {
			Component textComponent = ScoreboardReader.getFirstScoreContaining(stage.getName() + " in ");
			try {
				if (textComponent != null) {
					String stageTime = TextUtils.removeFormattingCodes(textComponent.getString());
					int[] times = Arrays.stream(stageTime.trim().replace(stage.getName() + " in ", "").split(":"))
							.mapToInt(Integer::parseInt).toArray();
					int secs = times[0] * 60 + times[1];
					this.gameStartTime = Instant.now().toEpochMilli() - (stage.getMillis() - secs * 1000);
					return true;
				}
			} catch (NumberFormatException e) {
				AnvilClient.LOGGER.catching(e);
			}
		}
		return false;
	}

	private void onGameStart() {
		inBedWars = true;
		gameStartTime = Instant.now().toEpochMilli();
	}

	private void onGameRejoin() {
		onGameStart();
	}

	private void onGameLeave() {
		inBedWars = false;
	}

	private enum Stages {
		DIAMOND2(6 * 60, "Diamond II"), EMERALD2(12 * 60, "Emerald II"), DIAMOND3(18 * 60, "Diamond III"),
		EMERALD3(24 * 60, "Emerald III"), BED_GONE(30 * 60, "Bed Gone"), SUDDEN_DEATH(40 * 60, "Sudden Death"),
		GAME_OVER(50 * 60, "Game Over");

		private final long MILLIS;
		private final String NAME;

		private Stages(long secs, String name) {
			this.MILLIS = secs * 1000L;
			this.NAME = name;
		}

		public long getMillis() {
			return MILLIS;
		}

		public long getMillisTo(long elapsedTime) {
			return this.MILLIS - elapsedTime;
		}

		public String getName() {
			return NAME;
		}
	}

}
