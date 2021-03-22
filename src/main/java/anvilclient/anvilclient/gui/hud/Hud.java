package anvilclient.anvilclient.gui.hud;

import com.mojang.blaze3d.matrix.MatrixStack;

import anvilclient.anvilclient.util.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Hud extends AbstractGui {
	
	private static final Hud INSTANCE = new Hud();
	
	public static Hud getInstance() {
		return INSTANCE;
	}


	private final int TEXT_COLOR = 0xFFFFFF;

	private final int LINE_HEIGHT = 10;

	private final MatrixStack matrixStack;

	private final Minecraft mc;

	private final ConfigManager configManager;
	
	int width;
	int height;

	private Hud() {
		this.mc = Minecraft.getInstance();
		this.configManager = ConfigManager.getInstance();
		this.matrixStack = new MatrixStack();
		updateScaledWidthAndHeight();
	}

	public void render(RenderGameOverlayEvent.Post event) {
		if (shouldRender() && event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
			renderCoordinates();
		}
	}

	public void updateScaledWidthAndHeight() {
		this.width = mc.getMainWindow().getScaledWidth();
        this.height = mc.getMainWindow().getScaledHeight();
	}

	private void renderCoordinates() {
		if (configManager.getCoordinates()) {
			int currentHeight = 0;
			ClientPlayerEntity player = mc.player;
			int coordinatesX = (int) (width * 0.75);
			int coordinatesY = (int) (height * 0.75);
			drawString(matrixStack, mc.fontRenderer, "X: " + player.getPosX(), coordinatesX, coordinatesY + currentHeight, TEXT_COLOR);
			currentHeight += LINE_HEIGHT + 1;
			drawString(matrixStack, mc.fontRenderer, "Y: " + player.getPosY(), coordinatesX, coordinatesY + currentHeight, TEXT_COLOR);
			currentHeight += LINE_HEIGHT + 1;
			drawString(matrixStack, mc.fontRenderer, "Z: " + player.getPosZ(), coordinatesX, coordinatesY + currentHeight, TEXT_COLOR);
		}
	}

	private boolean shouldRender() {
		return !(mc.currentScreen instanceof ChatScreen) && !mc.gameSettings.showDebugInfo;
	}
	
}