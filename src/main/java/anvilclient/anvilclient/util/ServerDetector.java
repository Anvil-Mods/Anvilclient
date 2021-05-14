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

import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerDetector {

	private static final ServerDetector INSTANCE = new ServerDetector();

	public static ServerDetector getInstance() {
		return INSTANCE;
	}

	private ServerDetector() {
	}

	@SubscribeEvent
	public void update(ClientPlayerNetworkEvent event) {
		if (event instanceof ClientPlayerNetworkEvent.LoggedInEvent) {
			Minecraft mc = Minecraft.getInstance();
			ClientPlayNetHandler clientplaynethandler = mc.getConnection();
			if (clientplaynethandler != null && clientplaynethandler.getNetworkManager().isChannelOpen()) {
				if (mc.getIntegratedServer() != null && !mc.getIntegratedServer().getPublic()) {
					this.currentServer = Server.SINGLEPLAYER;
				} else if (mc.isConnectedToRealms()) {
					this.currentServer = Server.REALMS;
				} else if (mc.getIntegratedServer() == null
						&& (mc.getCurrentServerData() == null || !mc.getCurrentServerData().isOnLAN())) {
					ServerData currentServer = Minecraft.getInstance().getCurrentServerData();
					if (currentServer != null) {
						String serverAddress = currentServer.serverIP.toLowerCase();
						this.currentServer = Arrays.stream(Server.values()).filter(server -> server.DOMAIN != null)
								.filter(server -> serverAddress.contains(server.DOMAIN)).findFirst()
								.orElse(Server.UNKNOWN);
					} else {
						this.currentServer = Server.NONE;
					}
				} else {
					this.currentServer = Server.LAN;
				}
			}
		} else if (event instanceof ClientPlayerNetworkEvent.LoggedOutEvent) {
			currentServer = Server.NONE;
		}
	}

	
	private Server currentServer = Server.NONE;

	public Server getCurrentServer() {
		return currentServer;
	}

	
	public enum Server {
		SINGLEPLAYER,
		LAN,
		REALMS,
		NONE,

		UNKNOWN,
		HYPIXEL("hypixel.net");

		public final String DOMAIN;

		private Server(String domain) {
			this.DOMAIN = domain;
		}

		Server() {
			this.DOMAIN = null;
		}
	}

}
