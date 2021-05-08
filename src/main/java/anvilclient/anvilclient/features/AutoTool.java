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

import anvilclient.anvilclient.event.PlayerDamageBlockEvent;
import anvilclient.anvilclient.event.PlayerResetBreakingBlockEvent;
import anvilclient.anvilclient.settings.BooleanSetting;
import anvilclient.anvilclient.settings.EnumSetting;
import anvilclient.anvilclient.settings.IntegerSetting;
import anvilclient.anvilclient.settings.Setting;
import anvilclient.anvilclient.settings.SettingSuitableEnum;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoTool extends KeyboundFeature {

	@Override
	public String getName() {
		return "autotool";
	}
	
	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.BUILDING;
	}
	
	private boolean isOriginalTool = true;
	private Slot originalTool;
	
	@Setting
	public IntegerSetting minDurability = new IntegerSetting(getName() + ".minDurability", "", 5, 0, (int) Byte.MAX_VALUE, 1.0F);
	
	@Setting
	public BooleanSetting revertTool = new BooleanSetting(getName() + ".revertTool", "", true);
	
	@Setting
	public EnumSetting<AutoTool.SilkTouchMode> silkTouchMode = new EnumSetting<AutoTool.SilkTouchMode>(getName() + ".silkTouchMode", "", AutoTool.SilkTouchMode.DOESNT_MATTER);

	private boolean isDurabilityGood(ItemStack tool) {
		return minDurability.getValue() < 1 || ItemHelper.isUnbreakable(tool)
				|| ItemHelper.getDurability(tool) >= minDurability.getValue();
	}

	private boolean isDurabilityGood(Slot slot) {
		return isDurabilityGood(slot.getStack());
	}
	
	private boolean filterSilkTouchMode(ItemStack tool) {
		int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
		switch (silkTouchMode.getValue()) {
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
	
	private boolean filterSilkTouchMode(Slot slot) {
		return filterSilkTouchMode(slot.getStack());
	}
	
	private int getSilkTouchEnchantmentLevel(ItemStack tool) {
		int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
		switch (silkTouchMode.getValue()) {
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
	
	private int getFortuneEnchantmentLevel(ItemStack tool) {
		return EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
	}

	private Slot getBestTool(BlockPos blockPos) {
		ClientPlayerEntity localPlayer = LocalPlayerHelper.getLocalPlayer();
		if (!WorldHelper.canPlaceBlocksAt(localPlayer, blockPos)
				&& !WorldHelper.getWorld(localPlayer).isAirBlock(blockPos)) {
			return LocalPlayerHelper
					.getHotbarSlots(localPlayer).stream()
					.filter(this::isDurabilityGood)
					.filter(this::filterSilkTouchMode)
					.max(Comparator
						.comparing(Slot::getStack, Comparator
							.<ItemStack>comparingDouble(tool -> ItemHelper.getDiggingSpeedAt(localPlayer, tool, blockPos))
							.thenComparingInt(this::getSilkTouchEnchantmentLevel)
							.thenComparingInt(this::getFortuneEnchantmentLevel)
							.thenComparing(ItemHelper::isUnbreakable))
						.thenComparing(slot -> slot == LocalPlayerHelper.getSelectedSlot(localPlayer)))
					.orElse(LocalPlayerHelper.getSelectedSlot(localPlayer));
		}
		return LocalPlayerHelper.getSelectedSlot(localPlayer);
	}

	public void selectBestTool(BlockPos blockPos) {
		if (isEnabled() && Minecraft.getInstance().playerController.gameIsSurvivalOrAdventure()) {
			Slot bestTool = getBestTool(blockPos);
			ClientPlayerEntity localPlayer = LocalPlayerHelper.getLocalPlayer();
			if (isOriginalTool) {
				originalTool = LocalPlayerHelper.getSelectedSlot(localPlayer);
			}
			LocalPlayerHelper.setSelectedSlot(localPlayer, bestTool);
			isOriginalTool = false;
		}
	}

	public void resetTool() {
		if (revertTool.getValue()) {
			if (!isOriginalTool) {
				LocalPlayerHelper.setSelectedSlot(LocalPlayerHelper.getLocalPlayer(), originalTool);
				isOriginalTool = true;
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerDamageBlock(PlayerDamageBlockEvent event) {
		this.selectBestTool(event.getBlockPos());
	}
	
	@SubscribeEvent
	public void onPlayerResetBreakingBlock(PlayerResetBreakingBlockEvent event) {
		this.resetTool();
	}

	public enum SilkTouchMode implements SettingSuitableEnum {
		DONT_USE,
		PREFER_NOT_TO_USE,
		DOESNT_MATTER,
		PREFER,
		USE_ONLY;
		
		private final String translationKey;
		
		private final ITextComponent translationTextComponent;
		
		private SilkTouchMode() {
			this.translationKey = "anvilclient.configGui.autoTool.silkTouchMode." + this.toString().toLowerCase();
			this.translationTextComponent = new TranslationTextComponent(translationKey);
		}
		
		@Override
		public String getTranslationKey() {
			return translationKey;
		}

		@Override
		public ITextComponent getTranslationTextComponent() {
			return translationTextComponent;
		}
	}
}
