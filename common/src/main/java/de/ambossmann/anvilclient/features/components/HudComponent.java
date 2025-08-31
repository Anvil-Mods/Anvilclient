/*
 * Copyright (C) 2023-2025 Ambossmann <https://github.com/Ambossmann>
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
package de.ambossmann.anvilclient.features.components;

import de.ambossmann.anvilclient.features.Feature;
import de.ambossmann.anvilclient.settings.DoubleSetting;
import de.ambossmann.anvilclient.settings.IgnoreAsOption;
import de.ambossmann.anvilclient.settings.Setting;
import de.ambossmann.anvilclient.util.utils.HudUtils;
import dev.architectury.event.events.client.ClientGuiEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class HudComponent extends BaseComponent {

	private final List<RenderFunctionContainer> renderFunctionContainers = new ArrayList<>();
	private final BooleanSupplier enabledSupplier;
	private final String name;

	@Setting @IgnoreAsOption public final DoubleSetting x;

	@Setting @IgnoreAsOption public final DoubleSetting y;

	public HudComponent(
			Feature parentFeature,
			RenderFunction renderFunction,
			BooleanSupplier enabledSupplier,
			double defaultX,
			double defaultY) {
		this(parentFeature, renderFunction, enabledSupplier, "hud", defaultX, defaultY);
	}

	public HudComponent(
			Feature parentFeature,
			RenderFunction renderFunction,
			BooleanSupplier enabledSupplier,
			String name,
			double defaultX,
			double defaultY) {
		super(parentFeature);
		this.enabledSupplier = enabledSupplier;
		this.name = name;

		this.x =
				new DoubleSetting(
						parentFeature.getName() + "." + name + ".x", "", defaultX, 0.0, 1.0, 0, 3);
		this.y =
				new DoubleSetting(
						parentFeature.getName() + "." + name + ".y", "", defaultY, 0.0, 1.0, 0, 3);

		renderFunctionContainers.add(new RenderFunctionContainer(renderFunction, 0, 0));
	}

	@Override
	public void register() {
		super.register();
		ClientGuiEvent.RENDER_HUD.register(this::renderHud);
	}

	private void renderHud(GuiGraphics graphics, float tickDelta) {
		if (enabledSupplier.getAsBoolean() && HudUtils.shouldRender()) {
			int startX = (int) (HudUtils.getScreenWidth() * x.getDoubleValue());
			int startY = (int) (HudUtils.getScreenHeight() * y.getDoubleValue());
			for (RenderFunctionContainer container : renderFunctionContainers) {
				container.renderFunction.render(
						graphics, tickDelta, startX + container.xOffset, startY + container.yOffset);
			}
		}
	}

	public boolean addRenderFunction(RenderFunction renderFunction, int xOffset, int yOffset) {
		return addRenderFunctionContainer(
				new RenderFunctionContainer(renderFunction, xOffset, yOffset));
	}

	private boolean addRenderFunctionContainer(RenderFunctionContainer renderFunctionContainer) {
		if (renderFunctionContainers.contains(renderFunctionContainer)) {
			return false;
		}
		renderFunctionContainers.add(renderFunctionContainer);
		return true;
	}

	private record RenderFunctionContainer(RenderFunction renderFunction, int xOffset, int yOffset) {}

	@FunctionalInterface
	public interface RenderFunction {

		void render(GuiGraphics graphics, float tickDelta, int x, int y);
	}

	public record TextRenderFunction(Supplier<String> textSupplier, IntSupplier textColorSupplier)
			implements RenderFunction {

		public TextRenderFunction(Supplier<String> textSupplier, int textColor) {
			this(textSupplier, () -> textColor);
		}

		public TextRenderFunction(Supplier<String> textSupplier) {
			this(textSupplier, () -> 0xFFFFFF);
		}

		@Override
		public void render(GuiGraphics graphics, float tickDelta, int x, int y) {
			graphics.drawString(
					HudUtils.getFont(), textSupplier.get(), x, y, textColorSupplier.getAsInt());
		}
	}

	public record ItemRenderFunction(Supplier<ItemStack> itemSupplier) implements RenderFunction {

		@Override
		public void render(GuiGraphics graphics, float tickDelta, int x, int y) {
			ItemStack itemStack = itemSupplier.get();
			graphics.renderItem(itemStack, x, y);
			graphics.renderItemDecorations(Minecraft.getInstance().font, itemStack, x, y);
		}
	}
}
