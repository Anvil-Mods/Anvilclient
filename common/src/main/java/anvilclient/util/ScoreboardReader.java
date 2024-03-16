/*******************************************************************************
 * Copyright (C) 2021, 2022 Anvil-Mods
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
 *******************************************************************************/
package anvilclient.util;

import anvilclient.util.utils.TextUtils;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.world.scores.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardReader {

    private ScoreboardReader() {
    }

    public static final Scoreboard DUMMY_SCOREBOARD = new Scoreboard();

    public static Objective getScoreObjective() {
        Minecraft mc = Minecraft.getInstance();
        Scoreboard scoreboard = mc.level.getScoreboard();
        PlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(mc.player.getScoreboardName());
        if (scoreplayerteam != null) {
            ChatFormatting teamColor = scoreplayerteam.getColor();
            if (teamColor != ChatFormatting.RESET) {
                return scoreboard.getDisplayObjective(DisplaySlot.teamColorToSlot(teamColor));
            }
        }

        return scoreboard.getDisplayObjective(DisplaySlot.SIDEBAR);
    }

    public static Scoreboard getScoreboard() {
        Objective scoreobjective = getScoreObjective();
        if (scoreobjective != null) {
            return scoreobjective.getScoreboard();
        }
        return DUMMY_SCOREBOARD;
    }

    public static Collection<PlayerScoreEntry> getScores() {
        return getScoreboard().listPlayerScores(getScoreObjective());
    }

    public static Component getFirstScoreContaining(String string) {
        for (Component textComponent : getLines()) {
            if (TextUtils.removeFormattingCodes(textComponent.getString()).contains(string)) {
                return textComponent;
            }
        }
        return null;
    }

    public static boolean contains(String string) {
        return getFirstScoreContaining(string) != null;
    }

    public static List<Component> getLines() {
        Scoreboard scoreboard = getScoreboard();
        return scoreboard.listPlayerScores(getScoreObjective()).stream()
                .filter(playerScoreEntry -> !playerScoreEntry.isHidden())
                .sorted(Gui.SCORE_DISPLAY_ORDER).limit(15L).map(playerScoreEntry -> {
                    PlayerTeam playerTeam = scoreboard.getPlayersTeam(playerScoreEntry.owner());
                    Component ownerName = playerScoreEntry.ownerName();
                    return (Component) PlayerTeam.formatNameForTeam(playerTeam, ownerName);
                }).toList();
    }
}
