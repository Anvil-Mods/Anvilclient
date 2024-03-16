/*
 * Copyright (C) 2021-2024 Ambossmann <https://github.com/Ambossmann>
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
 */
package anvilclient.gui.config;

import anvilclient.settings.EnumSetting;
import anvilclient.settings.IgnoreAsOption;
import anvilclient.settings.Setting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.OptionEnum;

public abstract class ConfigScreen extends Screen {
	private static final int TITLE_HEIGHT = 8;

	protected static final int OPTIONS_LIST_TOP_BOTTOM_OFFSET = 32;
	protected static final int OPTIONS_LIST_ITEM_HEIGHT = 25;

	protected static final int BUTTON_WIDTH = 200;
	protected static final int BUTTON_HEIGHT = 20;
	protected static final int DONE_BUTTON_TOP_OFFSET = 27;

	protected OptionsList optionsList;

	protected Screen parentScreen;

	public ConfigScreen(String nameTranslationKey, Screen parentScreen) {
		super(Component.translatable(nameTranslationKey));
		this.parentScreen = parentScreen;
	}

	public ConfigScreen(String nameTranslationKey) {
		this(nameTranslationKey, null);
	}

	@Override
	protected void init() {
		this.optionsList =
				this.addRenderableWidget(
						new OptionsList(
								this.minecraft,
								this.width,
								this.height - 2 * OPTIONS_LIST_TOP_BOTTOM_OFFSET,
								OPTIONS_LIST_TOP_BOTTOM_OFFSET,
								OPTIONS_LIST_ITEM_HEIGHT));

		this.addOptions();

		this.addButtons();
	}

	protected void addButtons() {
		this.addRenderableWidget(
				Button.builder(CommonComponents.GUI_DONE, button -> this.onClose())
						.pos((this.width - BUTTON_WIDTH) / 2, this.height - DONE_BUTTON_TOP_OFFSET)
						.size(BUTTON_WIDTH, BUTTON_HEIGHT)
						.build());
	}

	protected abstract void addOptions();

	public void showScreen(Screen screen) {
		this.minecraft.setScreen(screen);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);
		this.optionsList.render(graphics, mouseX, mouseY, partialTicks);
		graphics.drawCenteredString(this.font, this.title, this.width / 2, TITLE_HEIGHT, 0xFFFFFF);
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(parentScreen);
	}

	@Setting @IgnoreAsOption
	public static EnumSetting<SortType> sortType =
			new EnumSetting<>("anvilclient.sortType", "", SortType.CATEGORY);

	public enum SortType implements OptionEnum {
		PLAIN,
		CATEGORY;

		private final String translationKey;

		private SortType() {
			this.translationKey = "anvilclient.configGui.sortType." + this.toString().toLowerCase();
		}

		@Override
		public String getKey() {
			return translationKey;
		}

		@Override
		public int getId() {
			return this.ordinal();
		}
	}
}
