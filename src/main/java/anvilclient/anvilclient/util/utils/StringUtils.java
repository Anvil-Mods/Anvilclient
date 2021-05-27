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
package anvilclient.anvilclient.util.utils;

import java.util.ArrayList;

public class StringUtils {
	
	private StringUtils() {
	}
	
	//Splits a given String into lines with n max Characters and returns the resulting lines as String[]
	public static String[] lineSplit(String string, int charLimit) {
		String line = "";
		String[] strings = string.split(" ");
		ArrayList<String> lines = new ArrayList<String>();
		for(String word : strings) {
			if(line.length() != 0) {
				if((line.length() + 1 + word.length()) >= charLimit) {
					lines.add(line);
					line = "";
				}
			line = line + " " + word;
			}
		}
		lines.add(line);
		return (String[]) lines.toArray();
	}
}
