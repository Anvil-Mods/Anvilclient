/*******************************************************************************
 * Copyright (C) 2021  Anvilclient and Contributors
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package anvilclient.anvilclient.util;

import anvilclient.anvilclient.event.PlayerDamageBlockEvent;
import anvilclient.anvilclient.event.PlayerResetBreakingBlockEvent;
import anvilclient.anvilclient.features.AutoTool;
import anvilclient.anvilclient.gui.hud.Hud;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventManager {

	private static final EventManager INSTANCE = new EventManager();

	public static EventManager getInstance() {
		return INSTANCE;
	}
	
	public static final IEventBus FORGE_EVENT_BUS = MinecraftForge.EVENT_BUS;
	
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
	
	@SubscribeEvent
	public void onPlayerDamageBlock(PlayerDamageBlockEvent event) {
		AutoTool.selectBestTool(event.getBlockPos());
	}
	
	@SubscribeEvent
	public void onPlayerResetBreakingBlock(PlayerResetBreakingBlockEvent event) {
		AutoTool.resetTool();
	}
}
