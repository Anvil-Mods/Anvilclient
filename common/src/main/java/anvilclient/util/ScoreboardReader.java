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
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

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
        Objective scoreobjective = null;
        PlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(mc.player.getScoreboardName());
        if (scoreplayerteam != null) {
            int j2 = scoreplayerteam.getColor().getId();
            if (j2 >= 0) {
                scoreobjective = scoreboard.getDisplayObjective(3 + j2);
            }
        }

        return scoreobjective != null ? scoreobjective : scoreboard.getDisplayObjective(1);
    }

    public static Scoreboard getScoreboard() {
        Objective scoreobjective = getScoreObjective();
        if (scoreobjective != null) {
            return scoreobjective.getScoreboard();
        }
        return DUMMY_SCOREBOARD;
    }

    public static Collection<Score> getSortedScores() {
        return getScoreboard().getPlayerScores(getScoreObjective());
    }

    public static Component getFirstScoreContaining(String string) {
        for (Component textComponent : getLines().values()) {
            if (TextUtils.removeFormattingCodes(textComponent.getString()).contains(string)) {
                return textComponent;
            }
        }
        return null;
    }

    public static boolean contains(String string) {
        return getFirstScoreContaining(string) != null;
    }

    public static HashMap<Score, Component> getLines() {
        Collection<Score> collection = getSortedScores();
        List<Score> list = collection.stream()
                .filter((score) -> !score.getOwner().startsWith("#"))
                .collect(Collectors.toList());

        if (list.size() > 15) {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        } else {
            collection = list;
        }

        HashMap<Score, Component> map = new HashMap<>();

        for (Score score : collection) {
            PlayerTeam scoreplayerteam = getScoreboard().getPlayersTeam(score.getOwner());
            Component itextcomponent = PlayerTeam.formatNameForTeam(scoreplayerteam,
                    Component.literal(score.getOwner()));
            map.put(score, itextcomponent);
        }

        return map;
    }
}
