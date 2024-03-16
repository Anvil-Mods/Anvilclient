/*
 * Copyright (C) 2023-2024 Ambossmann <https://github.com/Ambossmann>
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
package anvilclient.features.components;

import anvilclient.features.Feature;
import anvilclient.util.utils.HudUtils;
import dev.architectury.event.events.client.ClientGuiEvent;
import net.minecraft.client.gui.GuiGraphics;

import java.util.function.BooleanSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

public class HudComponent extends BaseComponent {

    private final Supplier<String> textSupplier;
    private final BooleanSupplier enabled;
    private final IntUnaryOperator xFunction;

    private final IntUnaryOperator yFunction;
    private final int textColor;

    public HudComponent(Feature parentFeature, Supplier<String> textSupplier, BooleanSupplier enabled, IntUnaryOperator xFunction, IntUnaryOperator yFunction, int textColor) {
        super(parentFeature);
        this.textSupplier = textSupplier;
        this.enabled = enabled;
        this.xFunction = xFunction;
        this.yFunction = yFunction;
        this.textColor = textColor;
    }

    @Override
    public void register() {
        super.register();
        ClientGuiEvent.RENDER_HUD.register(this::renderHud);
    }

    private void renderHud(GuiGraphics graphics, float tickDelta) {
        if (enabled.getAsBoolean() && HudUtils.shouldRender()) {
            graphics.drawString(HudUtils.getFont(), textSupplier.get(), xFunction.applyAsInt(HudUtils.getScreenWidth()), yFunction.applyAsInt(HudUtils.getScreenHeight()), textColor);
        }
    }

    public HudComponent chained(Supplier<String> textSupplier, int spacing) {
        return new HudComponent(parentFeature, textSupplier, enabled, xFunction, (i) -> yFunction.applyAsInt(i) + spacing, textColor);
    }
}
