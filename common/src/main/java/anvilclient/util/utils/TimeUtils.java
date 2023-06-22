/*******************************************************************************
 * Copyright (C) 2021 Anvil-Mods
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
package anvilclient.util.utils;

public class TimeUtils {

	private TimeUtils() {
	}

	public static String formatTimeSecs(long secs) {
		String formattedTime;
		if (secs / 3600 >= 1) {
			formattedTime = String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);
		} else {
			formattedTime = String.format("%02d:%02d", (secs % 3600) / 60, secs % 60);
		}
		return formattedTime;
	}

	public static String formatTimeMillis(long millis) {
		return formatTimeSecs(millis / 1000L);
	}

}
