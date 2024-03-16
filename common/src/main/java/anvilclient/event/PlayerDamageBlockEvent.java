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
package anvilclient.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public interface PlayerDamageBlockEvent {

	Event<Started> STARTED = EventFactory.createLoop();

	Event<Reset> RESET = EventFactory.createLoop();

	interface Started {
		void started(BlockPos blockPos, Direction directionFacing);
	}

	interface Reset {
		void reset();
	}
}
