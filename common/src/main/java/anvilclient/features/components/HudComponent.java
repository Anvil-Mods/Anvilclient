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
