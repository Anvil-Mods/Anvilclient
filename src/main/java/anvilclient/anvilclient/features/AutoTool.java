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
package anvilclient.anvilclient.features;

import java.util.Comparator;

import anvilclient.anvilclient.util.ConfigManager;
import anvilclient.anvilclient.util.ItemHelper;
import anvilclient.anvilclient.util.LocalPlayerHelper;
import anvilclient.anvilclient.util.WorldHelper;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class AutoTool {
	
	private static boolean isDurabilityGood(ItemStack tool) {
		ConfigManager configManager = ConfigManager.getInstance();
		return configManager.getAutoToolMinDurability() < 1
			|| ItemHelper.isUnbreakable(tool)
			|| ItemHelper.getDurability(tool) >= configManager.getAutoToolMinDurability();
	}
	
	private static boolean isDurabilityGood(Slot slot) {
		return isDurabilityGood(slot.getStack());
	}
	
	private static Slot getBestTool(BlockPos blockPos) {
		ClientPlayerEntity localPlayer = LocalPlayerHelper.getLocalPlayer();
		if (!WorldHelper.canPlaceBlocksAt(localPlayer, blockPos) && !WorldHelper.getWorld(localPlayer).isAirBlock(blockPos)) {
		      return LocalPlayerHelper.getHotbarSlots(localPlayer).stream()
		          .filter(AutoTool::isDurabilityGood)
		          .max(Comparator.comparing(Slot::getStack,
		              Comparator.<ItemStack>comparingDouble(tool -> ItemHelper.getDiggingSpeedAt(localPlayer, tool, blockPos))
		              .thenComparing(ItemHelper::isUnbreakable)))
		          .orElse(LocalPlayerHelper.getSelectedSlot(localPlayer));
		    }
		return LocalPlayerHelper.getSelectedSlot(localPlayer);
	}

	public static void selectBestTool(BlockPos blockPos) {
		if (ConfigManager.getInstance().getAutoTool()) {
			LocalPlayerHelper.setSelectedSlot(LocalPlayerHelper.getLocalPlayer(), getBestTool(blockPos));
		}
	}
}
