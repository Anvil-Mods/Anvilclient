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
package anvilclient.anvilclient.gui.config;

import com.mojang.blaze3d.vertex.PoseStack;

import anvilclient.anvilclient.gui.util.ExtendedOptionsList;
import anvilclient.anvilclient.settings.EnumSetting;
import anvilclient.anvilclient.settings.IgnoreAsOption;
import anvilclient.anvilclient.settings.Setting;
import anvilclient.anvilclient.settings.SettingSuitableEnum;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class ConfigScreen extends Screen {
	private static final int TITLE_HEIGHT = 8;

	protected static final int OPTIONS_LIST_TOP_HEIGHT = 24;
	protected static final int OPTIONS_LIST_BOTTOM_OFFSET = 32;
	protected static final int OPTIONS_LIST_ITEM_HEIGHT = 25;

	protected static final int BUTTON_WIDTH = 200;
	protected static final int BUTTON_HEIGHT = 20;
	protected static final int DONE_BUTTON_TOP_OFFSET = 26;

	protected ExtendedOptionsList optionsList;

	protected Screen parentScreen;

	public ConfigScreen(String nameTranslationKey, Screen parentScreen) {
		super(new TranslatableComponent(nameTranslationKey));
		this.parentScreen = parentScreen;
	}

	public ConfigScreen(String nameTranslationKey) {
		this(nameTranslationKey, null);
	}

	@Override
	protected void init() {
		this.optionsList = new ExtendedOptionsList(this.minecraft, this.width, this.height,
				OPTIONS_LIST_TOP_HEIGHT, this.height - OPTIONS_LIST_BOTTOM_OFFSET, OPTIONS_LIST_ITEM_HEIGHT);

		this.addOptions();

		this.addWidget(this.optionsList);

		this.addButtons();
	}

	protected void addButtons() {
		this.addRenderableWidget(
				new Button((this.width - BUTTON_WIDTH) / 2, this.height - DONE_BUTTON_TOP_OFFSET, BUTTON_WIDTH,
						BUTTON_HEIGHT, new TranslatableComponent("gui.done"), button -> this.onClose()));
	}

	protected abstract void addOptions();

	public void showScreen(Screen screen) {
		this.minecraft.setScreen(screen);
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);
		this.optionsList.render(poseStack, mouseX, mouseY, partialTicks);
		GuiComponent.drawCenteredString(poseStack, this.font, this.title, this.width / 2, TITLE_HEIGHT, 0xFFFFFF);
		super.render(poseStack, mouseX, mouseY, partialTicks);
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(parentScreen);
	}

	@Override
	public void removed() {
	}

	@Setting
	@IgnoreAsOption
	public static EnumSetting<SortType> sortType = new EnumSetting<ConfigScreen.SortType>("anvilclient.sortType", "",
			SortType.CATEGORY);

	public enum SortType implements SettingSuitableEnum {
		PLAIN,
		CATEGORY;

		private final String translationKey;
		private final TranslatableComponent translatableComponent;

		private SortType() {
			this.translationKey = "anvilclient.configGui.sortType." + this.toString().toLowerCase();
			this.translatableComponent = new TranslatableComponent(translationKey);
		}

		@Override
		public String getTranslationKey() {
			return translationKey;
		}

		@Override
		public TranslatableComponent getTranslatableComponent() {
			return translatableComponent;
		}

	}
}
