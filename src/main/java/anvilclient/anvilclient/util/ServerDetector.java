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

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ClientPacketListener;
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
			ClientPacketListener clientplaynethandler = mc.getConnection();
			if (clientplaynethandler != null && clientplaynethandler.getConnection().isConnected()) {
				if (mc.getSingleplayerServer() != null && !mc.getSingleplayerServer().isPublished()) {
					this.currentServer = Server.SINGLEPLAYER;
				} else if (mc.isConnectedToRealms()) {
					this.currentServer = Server.REALMS;
				} else if (mc.getSingleplayerServer() == null
						&& (mc.getCurrentServer() == null || !mc.getCurrentServer().isLan())) {
					ServerData currentServer = Minecraft.getInstance().getCurrentServer();
					if (currentServer != null) {
						String serverAddress = currentServer.ip.toLowerCase();
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

		@Nullable
		public final String DOMAIN;

		private Server(String domain) {
			this.DOMAIN = domain;
		}

		private Server() {
			this.DOMAIN = null;
		}
	}

}
