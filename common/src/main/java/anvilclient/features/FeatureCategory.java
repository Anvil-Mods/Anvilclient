/*
 * Copyright (C) 2021-2023 Ambossmann <https://github.com/Ambossmann>
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
package anvilclient.features;

import net.minecraft.util.OptionEnum;

public enum FeatureCategory implements OptionEnum {
	GRAPHIC,
	INFO,
	BUILDING;

	private final String translationKey;

	private FeatureCategory() {
		this.translationKey = "anvilclient.featureCategory." + this.toString().toLowerCase();
	}

	@Override
	public String getKey() {
		return translationKey;
	}

	@Override
	public int getId() {
		return this.ordinal();
	}
}
