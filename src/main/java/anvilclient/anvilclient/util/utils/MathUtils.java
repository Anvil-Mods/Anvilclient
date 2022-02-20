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
package anvilclient.anvilclient.util.utils;

import java.util.Random;

import net.minecraft.util.Mth;

public class MathUtils {

	private MathUtils() {
	}

	private static Random randomGenerator = new Random();

	public static int randInt(int min, int max) {
		int number = randomGenerator.nextInt((max - min) + 1);
		return normalizeInt(number, 0, max - min, min, max, false);
	}

	public static int normalizeInt(int value, int minold, int maxold, int min, int max, boolean clamp) {
		if (clamp) {
			value = Mth.clamp(value, minold, maxold);
		}
		return (max - min) / (maxold - minold) * (value - minold) + min;
	}

	public static double trimDouble(double value, int decimalCount) {
		double factor = Math.pow(10, decimalCount);
		return ((double) ((int) (value * factor))) / factor;
	}

	public static float trimFloat(float value, int decimalCount) {
		return (float) trimDouble(value, decimalCount);
	}
}
