package anvilclient.anvilclient.features.info;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;

import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.settings.EnumSetting;
import anvilclient.anvilclient.settings.IntegerSetting;
import anvilclient.anvilclient.settings.Setting;
import anvilclient.anvilclient.settings.SettingSuitableEnum;
import anvilclient.anvilclient.util.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.InputEvent.MouseInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CPSDisplay extends TogglableFeature {
	
	@Override
	public String getName() {
		return "cpsDisplay";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.INFO;
	}
	
	private List<Long> clicks = new ArrayList<>();
	
	private static final int TEXT_COLOR = 0xFFFFFF;
	
	@Setting
	public final EnumSetting<CPSMouseButton> mouseButton = new EnumSetting<CPSDisplay.CPSMouseButton>(getName() + ".mouseButton", "", CPSDisplay.CPSMouseButton.LEFT);
	
	@Setting
	public final IntegerSetting measuringSpan = new IntegerSetting(getName() + ".measuringSpan", "", 2, 1, 10, 1);
	
	@SubscribeEvent
	public void onMouseClick(MouseInputEvent event) {
		if (isEnabled()) {
			if (event.getAction() == GLFW.GLFW_PRESS && event.getButton() == mouseButton.getValue().getButton()) {
				clicks.add(Instant.now().toEpochMilli());
			} 
		}
	}
	
	public void render(int width, int height, MatrixStack matrixStack, Minecraft mc) {
		if (isEnabled()) {
			int coordinatesX = (int) (width * 0.75);
			int coordinatesY = (int) (height * 0.25) + 11;
			double cps = ((double)clicks.size())/((double)measuringSpan.getValue());
			AbstractGui.drawString(matrixStack, mc.font, "CPS: " + MathUtils.trimDouble(cps, 2), coordinatesX, coordinatesY, TEXT_COLOR);
			long lgt = Instant.now().toEpochMilli() - 1000*measuringSpan.getValue();
			List<Long> clicks2 = new ArrayList<>(clicks);
			for (Long click : clicks2) {
				if (click < lgt) {
					clicks.remove(click);
				}
			} 
		}
	}

	
	public enum CPSMouseButton implements SettingSuitableEnum {
		LEFT(GLFW.GLFW_MOUSE_BUTTON_LEFT),
		RIGHT(GLFW.GLFW_MOUSE_BUTTON_RIGHT),
		MIDDLE(GLFW.GLFW_MOUSE_BUTTON_MIDDLE);
		
		private final String translationKey;
		private final ITextComponent translationTextComponent;
		
		private final int button;

		private CPSMouseButton(int button) {
			this.translationKey = "key.mouse." + this.toString().toLowerCase();
			this.translationTextComponent = new TranslationTextComponent(translationKey);
			this.button = button;
		}

		@Override
		public String getTranslationKey() {
			return translationKey;
		}

		@Override
		public ITextComponent getTranslationTextComponent() {
			return translationTextComponent;
		}
		
		public int getButton() {
			return button;
		}
	}

}
