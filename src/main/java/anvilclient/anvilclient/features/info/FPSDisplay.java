package anvilclient.anvilclient.features.info;

import com.mojang.blaze3d.matrix.MatrixStack;

import anvilclient.anvilclient.AnvilClient;
import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.mixin.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class FPSDisplay extends TogglableFeature {

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
		if (isEnabled()) {
			int coordinatesX = (int) (width * 0.75);
			int coordinatesY = (int) (height * 0.25);
			int fps;
			try {
				fps = ((IMixinMinecraft) mc).getFPS();
				AbstractGui.drawString(matrixStack, mc.font, "FPS: " + fps, coordinatesX, coordinatesY,
						TEXT_COLOR);
			} catch (IllegalArgumentException e) {
				AnvilClient.LOGGER.catching(e);
			} 
		}
	}

}
