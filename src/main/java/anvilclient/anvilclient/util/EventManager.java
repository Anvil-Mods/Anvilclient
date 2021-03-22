package anvilclient.anvilclient.util;

import anvilclient.anvilclient.gui.hud.Hud;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventManager {

	private static final EventManager INSTANCE = new EventManager();

	public static EventManager getInstance() {
		return INSTANCE;
	}
	
	
	private final Hud hud;
	
	private EventManager() {
		this.hud = Hud.getInstance();
	}
	
	public void registerOnEventBus() {
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        hud.render(event);
    }
	
	@SubscribeEvent
	public void onGuiClose(GuiOpenEvent event) {
		if (event.getGui() == null) {
			hud.updateScaledWidthAndHeight();
		}
	}
}
