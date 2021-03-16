package anvilclient.anvilclient.gui.util;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;

public class TextOption extends AbstractOption {

	protected String translationKey;
	
	public TextOption(String translationKeyIn) {
		super(translationKeyIn);
		this.translationKey = translationKeyIn;
	}

	@Override
	public Widget createWidget(GameSettings options, int xIn, int yIn, int widthIn) {
		// TODO Auto-generated method stub
		return null;
	}

}
