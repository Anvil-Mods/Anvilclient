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
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class AutoTool {

	private static boolean isOriginalTool = true;
	private static Slot originalTool;

	private static boolean isDurabilityGood(ItemStack tool) {
		ConfigManager configManager = ConfigManager.getInstance();
		return configManager.getAutoToolMinDurability() < 1 || ItemHelper.isUnbreakable(tool)
				|| ItemHelper.getDurability(tool) >= configManager.getAutoToolMinDurability();
	}

	private static boolean isDurabilityGood(Slot slot) {
		return isDurabilityGood(slot.getStack());
	}
	
	private static boolean filterSilkTouchMode(ItemStack tool) {
		int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
		switch (ConfigManager.getInstance().getAutoToolSilkTouchMode()) {
			case DONT_USE:
				if (level >=1) {
					return false;
				} else {
					return true;
				}
	
			case USE_ONLY:
				if (level >=1) {
					return true;
				} else {
					return false;
				}
			default:
				return true;
		}
	}
	
	private static boolean filterSilkTouchMode(Slot slot) {
		return filterSilkTouchMode(slot.getStack());
	}
	
	private static int getSilkTouchEnchantmentLevel(ItemStack tool) {
		int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
		switch (ConfigManager.getInstance().getAutoToolSilkTouchMode()) {
		case DONT_USE: case PREFER_NOT_TO_USE:
			return level * -1;

		case DOESNT_MATTER:
			return 0;
			
		case PREFER: case USE_ONLY:
			return level;
			
		default:
			return 0;
		}
	}
	
	private static int getFortuneEnchantmentLevel(ItemStack tool) {
		return EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
	}

	private static Slot getBestTool(BlockPos blockPos) {
		ClientPlayerEntity localPlayer = LocalPlayerHelper.getLocalPlayer();
		if (!WorldHelper.canPlaceBlocksAt(localPlayer, blockPos)
				&& !WorldHelper.getWorld(localPlayer).isAirBlock(blockPos)) {
			return LocalPlayerHelper
					.getHotbarSlots(localPlayer).stream()
					.filter(AutoTool::isDurabilityGood)
					.filter(AutoTool::filterSilkTouchMode)
					.max(Comparator
						.comparing(Slot::getStack, Comparator
							.<ItemStack>comparingDouble(tool -> ItemHelper.getDiggingSpeedAt(localPlayer, tool, blockPos))
							.thenComparingInt(AutoTool::getSilkTouchEnchantmentLevel)
							.thenComparingInt(AutoTool::getFortuneEnchantmentLevel)
							.thenComparing(ItemHelper::isUnbreakable))
						.thenComparing(slot -> slot == LocalPlayerHelper.getSelectedSlot(localPlayer)))
					.orElse(LocalPlayerHelper.getSelectedSlot(localPlayer));
		}
		return LocalPlayerHelper.getSelectedSlot(localPlayer);
	}

	public static void selectBestTool(BlockPos blockPos) {
		if (ConfigManager.getInstance().getAutoTool() && Minecraft.getInstance().playerController.gameIsSurvivalOrAdventure()) {
			Slot bestTool = getBestTool(blockPos);
			ClientPlayerEntity localPlayer = LocalPlayerHelper.getLocalPlayer();
			if (isOriginalTool) {
				originalTool = LocalPlayerHelper.getSelectedSlot(localPlayer);
			}
			LocalPlayerHelper.setSelectedSlot(localPlayer, bestTool);
			isOriginalTool = false;
		}
	}

	public static void resetTool() {
		if (ConfigManager.getInstance().getAutoToolRevertTool()) {
			if (!isOriginalTool) {
				LocalPlayerHelper.setSelectedSlot(LocalPlayerHelper.getLocalPlayer(), originalTool);
				isOriginalTool = true;
			}
		}
	}
	
	public static enum SilkTouchMode {
		DONT_USE("anvilclient.configGui.autoToolSilkTouchMode.dont_use"),
		PREFER_NOT_TO_USE("anvilclient.configGui.autoToolSilkTouchMode.prefer_not_to_use"),
		DOESNT_MATTER("anvilclient.configGui.autoToolSilkTouchMode.doesnt_matter"),
		PREFER("anvilclient.configGui.autoToolSilkTouchMode.prefer"),
		USE_ONLY("anvilclient.configGui.autoToolSilkTouchMode.use_only");
		
		private final String translationKey;
		
		private SilkTouchMode(String translationKey) {
			this.translationKey = translationKey;
		}
		
		public String getTranslationKey() {
			return translationKey;
		}
	}
}
