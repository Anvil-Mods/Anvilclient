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
package anvilclient.anvilclient.event;

import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraftforge.eventbus.api.Event;

public class PlayerDamageBlockEvent extends Event {

	private final BlockPos blockPos;
	private final Direction directionFacing;

	public PlayerDamageBlockEvent(BlockPos blockPos, Direction directionFacing) {
		this.blockPos = blockPos;
		this.directionFacing = directionFacing;
	}

	public BlockPos getBlockPos() {
		return blockPos;
	}

	public Direction getDirectionFacing() {
		return directionFacing;
	}
}
