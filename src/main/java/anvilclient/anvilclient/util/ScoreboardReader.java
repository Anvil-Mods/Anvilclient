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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import anvilclient.anvilclient.util.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ScoreboardReader {

	private ScoreboardReader() {
	}

	public static final Scoreboard DUMMY_SCOREBOARD = new Scoreboard();

	public static ScoreObjective getScoreObjective() {
		Minecraft mc = Minecraft.getInstance();
		Scoreboard scoreboard = mc.world.getScoreboard();
		ScoreObjective scoreobjective = null;
		ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(mc.player.getScoreboardName());
		if (scoreplayerteam != null) {
			int j2 = scoreplayerteam.getColor().getColorIndex();
			if (j2 >= 0) {
				scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + j2);
			}
		}

		return scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
	}

	public static Scoreboard getScoreboard() {
		ScoreObjective scoreobjective = getScoreObjective();
		if (scoreobjective != null) {
			return scoreobjective.getScoreboard();
		}
		return DUMMY_SCOREBOARD;
	}

	public static Collection<Score> getSortedScores() {
		return getScoreboard().getSortedScores(getScoreObjective());
	}

	public static ITextComponent getFirstScoreContaining(String string) {
		for (ITextComponent textComponent : getLines().values()) {
			if (TextUtils.removeFormattingCodes(textComponent.getString()).contains(string)) {
				return textComponent;
			}
		}
		return null;
	}

	public static boolean contains(String string) {
		return getFirstScoreContaining(string) != null;
	}

	public static HashMap<Score, ITextComponent> getLines() {
		Collection<Score> collection = getSortedScores();
		List<Score> list = collection.stream().filter((score) -> {
			return score.getPlayerName() != null && !score.getPlayerName().startsWith("#");
		}).collect(Collectors.toList());

		if (list.size() > 15) {
			collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
		} else {
			collection = list;
		}

		HashMap<Score, ITextComponent> map = new HashMap<>();

		for (Score score : collection) {
			ScorePlayerTeam scoreplayerteam = getScoreboard().getPlayersTeam(score.getPlayerName());
			ITextComponent itextcomponent = ScorePlayerTeam.func_237500_a_(scoreplayerteam,
					new StringTextComponent(score.getPlayerName()));
			map.put(score, itextcomponent);
		}

		return map;
	}
}
