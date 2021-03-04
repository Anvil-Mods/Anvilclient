package anvilclient.anvilclient.util;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class ClickButton extends AbstractOption implements Button.IPressable{
	
	protected String translationKey;
	protected Button.IPressable pressedAction;

	public ClickButton(String translationKeyIn, Button.IPressable pressedAction) {
		super(translationKeyIn);
		this.translationKey = translationKeyIn;
		this.pressedAction = pressedAction;
	}

	@Override
	public Widget createWidget(GameSettings options, int xIn, int yIn, int widthIn) {
		return new Button(xIn, yIn, widthIn, 20, new TranslationTextComponent(translationKey), pressedAction);
	}

	@Override
	public void onPress(Button p_onPress_1_) {
	}
}
