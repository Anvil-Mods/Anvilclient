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

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public class WorldHelper {

	public static ClientWorld getWorld(ClientPlayerEntity localPlayer) {
		return localPlayer == null ? null : localPlayer.worldClient;
	}

	public static ClientWorld getWorld() {
		return getWorld(LocalPlayerHelper.getLocalPlayer());
	}

	public static boolean canPlaceBlocksAt(ClientPlayerEntity localPlayer, BlockPos blockPos) {
		ClientWorld world = getWorld(localPlayer);
		return world.getBlockState(blockPos).getShape(world, blockPos).isEmpty();
	}

}
