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
package anvilclient.anvilclient.features.building;

import java.util.Comparator;

import anvilclient.anvilclient.event.PlayerDamageBlockEvent;
import anvilclient.anvilclient.event.PlayerResetBreakingBlockEvent;
import anvilclient.anvilclient.features.FeatureCategory;
import anvilclient.anvilclient.features.TogglableFeature;
import anvilclient.anvilclient.settings.BooleanSetting;
import anvilclient.anvilclient.settings.EnumSetting;
import anvilclient.anvilclient.settings.IntegerSetting;
import anvilclient.anvilclient.settings.Setting;
import anvilclient.anvilclient.settings.SettingSuitableEnum;
import anvilclient.anvilclient.util.utils.ItemUtils;
import anvilclient.anvilclient.util.utils.LocalPlayerUtils;
import anvilclient.anvilclient.util.utils.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoTool extends TogglableFeature {

	@Override
	public String getName() {
		return "autoTool";
	}

	@Override
	public FeatureCategory getCategory() {
		return FeatureCategory.BUILDING;
	}

	private boolean isOriginalTool = true;
	private Slot originalTool;

	@Setting
	public IntegerSetting minDurability = new IntegerSetting(getName() + ".minDurability", "", 5, 0,
			(int) Byte.MAX_VALUE, 1.0F);

	@Setting
	public BooleanSetting revertTool = new BooleanSetting(getName() + ".revertTool", "", true);

	@Setting
	public EnumSetting<AutoTool.SilkTouchMode> silkTouchMode = new EnumSetting<AutoTool.SilkTouchMode>(
			getName() + ".silkTouchMode", "", AutoTool.SilkTouchMode.DOESNT_MATTER);

	private boolean isDurabilityGood(ItemStack tool) {
		return minDurability.getValue() < 1 || ItemUtils.isUnbreakable(tool)
				|| ItemUtils.getDurability(tool) >= minDurability.getValue();
	}

	private boolean isDurabilityGood(Slot slot) {
		return isDurabilityGood(slot.getItem());
	}

	private boolean filterSilkTouchMode(ItemStack tool) {
		int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
		switch (silkTouchMode.getValue()) {
			case DONT_USE:
				if (level >= 1) {
					return false;
				} else {
					return true;
				}

			case USE_ONLY:
				if (level >= 1) {
					return true;
				} else {
					return false;
				}
			default:
				return true;
		}
	}

	private boolean filterSilkTouchMode(Slot slot) {
		return filterSilkTouchMode(slot.getItem());
	}

	private int getSilkTouchEnchantmentLevel(ItemStack tool) {
		int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
		switch (silkTouchMode.getValue()) {
			case DONT_USE:
			case PREFER_NOT_TO_USE:
				return level * -1;

			case DOESNT_MATTER:
				return 0;

			case PREFER:
			case USE_ONLY:
				return level;

			default:
				return 0;
		}
	}

	private int getFortuneEnchantmentLevel(ItemStack tool) {
		return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
	}

	private Slot getBestTool(BlockPos blockPos) {
		LocalPlayer localPlayer = LocalPlayerUtils.getLocalPlayer();
		if (!WorldUtils.canPlaceBlocksAt(localPlayer, blockPos)
				&& !WorldUtils.getWorld(localPlayer).isEmptyBlock(blockPos)) {
			return LocalPlayerUtils
					.getHotbarSlots(localPlayer).stream()
					.filter(this::isDurabilityGood)
					.filter(this::filterSilkTouchMode)
					.max(Comparator
							.comparing(Slot::getItem, Comparator
									.<ItemStack>comparingDouble(
											tool -> ItemUtils.getDiggingSpeedAt(localPlayer, tool, blockPos))
									.thenComparingInt(this::getSilkTouchEnchantmentLevel)
									.thenComparingInt(this::getFortuneEnchantmentLevel)
									.thenComparing(ItemUtils::isUnbreakable))
							.thenComparing(slot -> slot == LocalPlayerUtils.getSelectedSlot(localPlayer)))
					.orElse(LocalPlayerUtils.getSelectedSlot(localPlayer));
		}
		return LocalPlayerUtils.getSelectedSlot(localPlayer);
	}

	public void selectBestTool(BlockPos blockPos) {
		if (isEnabled() && Minecraft.getInstance().gameMode.hasExperience()) {
			Slot bestTool = getBestTool(blockPos);
			LocalPlayer localPlayer = LocalPlayerUtils.getLocalPlayer();
			if (isOriginalTool) {
				originalTool = LocalPlayerUtils.getSelectedSlot(localPlayer);
			}
			LocalPlayerUtils.setSelectedSlot(localPlayer, bestTool);
			isOriginalTool = false;
		}
	}

	public void resetTool() {
		if (revertTool.getValue()) {
			if (!isOriginalTool) {
				LocalPlayerUtils.setSelectedSlot(LocalPlayerUtils.getLocalPlayer(), originalTool);
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

		private final TranslatableComponent translatableComponent;

		private SilkTouchMode() {
			this.translationKey = "anvilclient.feature.autoTool.silkTouchMode." + this.toString().toLowerCase();
			this.translatableComponent = new TranslatableComponent(translationKey);
		}

		@Override
		public String getTranslationKey() {
			return translationKey;
		}

		@Override
		public TranslatableComponent getTranslatableComponent() {
			return translatableComponent;
		}
	}
}
