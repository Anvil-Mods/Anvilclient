package anvilclient.anvilclient.features.info;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.mojang.blaze3d.matrix.MatrixStack;

import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.KeyboundFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class FPSDisplay extends KeyboundFeature {

	@Override
	public String getName() {
		return "fpsDisplay";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}
	
	private static final int TEXT_COLOR = 0xFFFFFF;
	
	public void render(int width, int height, MatrixStack matrixStack, Minecraft mc) {
		int coordinatesX = (int) (width * 0.75);
		int coordinatesY = (int) (height * 0.25);
		int fps;
		try {
			fps = (int) FieldUtils.readStaticField(mc.getClass(), "debugFPS", true);
			AbstractGui.drawString(matrixStack, mc.fontRenderer, "FPS: " + fps, coordinatesX, coordinatesY, TEXT_COLOR);
		} catch (IllegalAccessException e) {
		}
	}

}
