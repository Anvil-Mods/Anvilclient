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
package anvilclient.anvilclient.gui.config;

import com.mojang.blaze3d.matrix.MatrixStack;

import anvilclient.anvilclient.gui.util.ExtendedOptionsRowList;
import anvilclient.anvilclient.settings.EnumSetting;
import anvilclient.anvilclient.settings.IgnoreAsOption;
import anvilclient.anvilclient.settings.Setting;
import anvilclient.anvilclient.settings.SettingSuitableEnum;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ConfigScreen extends Screen {
	private static final int TITLE_HEIGHT = 8;

	protected static final int OPTIONS_LIST_TOP_HEIGHT = 24;
	protected static final int OPTIONS_LIST_BOTTOM_OFFSET = 32;
	protected static final int OPTIONS_LIST_ITEM_HEIGHT = 25;

	protected static final int BUTTON_WIDTH = 200;
	protected static final int BUTTON_HEIGHT = 20;
	protected static final int DONE_BUTTON_TOP_OFFSET = 26;

	protected ExtendedOptionsRowList optionsRowList;

	protected Screen parentScreen;

	public ConfigScreen(String nameTranslationKey, Screen parentScreen) {
		super(new TranslationTextComponent(nameTranslationKey));
		this.parentScreen = parentScreen;
	}

	public ConfigScreen(String nameTranslationKey) {
		this(nameTranslationKey, null);
	}

	@Override
	protected void init() {
		this.optionsRowList = new ExtendedOptionsRowList(this.minecraft, this.width, this.height,
				OPTIONS_LIST_TOP_HEIGHT, this.height - OPTIONS_LIST_BOTTOM_OFFSET, OPTIONS_LIST_ITEM_HEIGHT);

		this.addOptions();

		this.children.add(this.optionsRowList);

		this.addButtons();
	}
	
	protected void addButtons() {
		this.addButton(new Button((this.width - BUTTON_WIDTH) / 2, this.height - DONE_BUTTON_TOP_OFFSET, BUTTON_WIDTH,
				BUTTON_HEIGHT, new TranslationTextComponent("gui.done"), button -> this.closeScreen()));
	}

	protected abstract void addOptions();

	public void showScreen(Screen screen) {
		this.minecraft.displayGuiScreen(screen);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.optionsRowList.render(matrixStack, mouseX, mouseY, partialTicks);
		AbstractGui.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, TITLE_HEIGHT, 0xFFFFFF);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	public void closeScreen() {
		this.minecraft.displayGuiScreen(parentScreen);
	}

	@Override
	public void onClose() {
	}
	
	@Setting
	@IgnoreAsOption
	public static EnumSetting<SortType> sortType = new EnumSetting<ConfigScreen.SortType>("anvilclient.sortType", "", SortType.CATEGORY);
	
	public enum SortType implements SettingSuitableEnum {
		PLAIN,
		CATEGORY;
		
		private final String translationKey;
		private final ITextComponent translationTextComponent;

		private SortType() {
			this.translationKey = "anvilclient.configGui.sortType." + this.toString().toLowerCase();
			this.translationTextComponent = new TranslationTextComponent(translationKey);
		}

		@Override
		public String getTranslationKey() {
			return translationKey;
		}

		@Override
		public ITextComponent getTranslationTextComponent() {
			return translationTextComponent;
		}
		
	}
}
