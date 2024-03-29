/*
 * Copyright (C) 2021-2024 Ambossmann <https://github.com/Ambossmann>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <https://www.gnu.org/licenses/>.
 */
package anvilclient.util;

import dev.architectury.event.events.client.ClientPlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;

import java.util.Arrays;
import java.util.Objects;

public class ServerDetector {

    private static final ServerDetector INSTANCE = new ServerDetector();

    public static ServerDetector getInstance() {
        return INSTANCE;
    }

    private ServerDetector() {
    }

    public void register() {
        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(this::join);
        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(this::quit);
    }

    //TODO test if this works
    private void join(LocalPlayer localPlayer) {
        Minecraft mc = Minecraft.getInstance();
        if (Objects.equals(mc.player, localPlayer)) {
            ClientPacketListener clientPacketListener = mc.getConnection();
            if (clientPacketListener != null && clientPacketListener.getConnection().isConnected()) {
                ServerData serverData = Minecraft.getInstance().getCurrentServer();
                if (mc.getSingleplayerServer() != null && !mc.getSingleplayerServer().isPublished()) {
                    this.currentServer = Server.SINGLEPLAYER;
                } else if (serverData != null && serverData.isRealm()) {
                    this.currentServer = Server.REALMS;
                } else if (mc.getSingleplayerServer() != null || serverData != null && serverData.isLan()) {
                    this.currentServer = Server.LAN;
                } else {
                    if (serverData != null) {
                        String serverAddress = serverData.ip.toLowerCase();
                        this.currentServer = Arrays.stream(Server.values())
                                .filter(server -> server.DOMAIN != null)
                                .filter(server -> serverAddress.contains(server.DOMAIN)).findFirst()
                                .orElse(Server.UNKNOWN);
                    } else {
                        this.currentServer = Server.NONE;
                    }
                }
            }
        }
    }

    private void quit(LocalPlayer localPlayer) {
        Minecraft mc = Minecraft.getInstance();
        if (Objects.equals(mc.player, localPlayer)) {
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

        private Server() {
            this.DOMAIN = null;
        }
    }

}
