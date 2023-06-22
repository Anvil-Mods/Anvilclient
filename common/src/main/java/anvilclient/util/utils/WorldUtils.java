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
package anvilclient.util.utils;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;

public class WorldUtils {

	private WorldUtils() {
	}

	public static ClientLevel getWorld(LocalPlayer localPlayer) {
		return localPlayer == null ? null : localPlayer.clientLevel;
	}

	public static ClientLevel getWorld() {
		return getWorld(LocalPlayerUtils.getLocalPlayer());
	}

	public static boolean canPlaceBlocksAt(LocalPlayer localPlayer, BlockPos blockPos) {
		ClientLevel world = getWorld(localPlayer);
		return world.getBlockState(blockPos).getShape(world, blockPos).isEmpty();
	}

}
