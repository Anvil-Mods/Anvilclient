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
package anvilclient.gui.config;

import anvilclient.features.FeatureCategory;
import anvilclient.util.utils.SettingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class MainGuiCategory extends ConfigScreen {

    public MainGuiCategory(Screen parentScreen) {
        super("anvilclient", parentScreen);
    }

    @Override
    protected void addOptions() {
        for (FeatureCategory category : FeatureCategory.values()) {
            this.optionsList.addBig(SettingUtils.getClickOption(category.getKey(), () -> Minecraft.getInstance()
                    .setScreen(new CategoryGui(category, this))));
        }
    }

    @Override
    protected void addButtons() {
        this.addRenderableWidget(Button.builder(Component.translatable(SortType.CATEGORY.getKey()),
                        button -> this.changeScreen())
                .pos(this.width / 2 - (BUTTON_WIDTH + 5), this.height - DONE_BUTTON_TOP_OFFSET)
                .size(BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose())
                .pos(this.width / 2 - (BUTTON_WIDTH + 5) + BUTTON_WIDTH + 10, this.height - DONE_BUTTON_TOP_OFFSET)
                .size(BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
    }

    private void changeScreen() {
        ConfigScreen.sortType.setValue(SortType.PLAIN);
        this.minecraft.setScreen(new MainGuiPlain(this.parentScreen));
    }

}
