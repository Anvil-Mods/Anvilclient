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
package anvilclient.features.building;

import anvilclient.AnvilclientCommon;
import anvilclient.event.PlayerDamageBlockEvent;
import anvilclient.features.Feature;
import anvilclient.features.FeatureCategory;
import anvilclient.features.components.FeatureToggleComponent;
import anvilclient.features.components.KeybindingComponent;
import anvilclient.settings.BooleanSetting;
import anvilclient.settings.EnumSetting;
import anvilclient.settings.IntegerSetting;
import anvilclient.settings.Setting;
import anvilclient.util.utils.ItemUtils;
import anvilclient.util.utils.LocalPlayerUtils;
import anvilclient.util.utils.WorldUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.OptionEnum;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.lwjgl.glfw.GLFW;

import java.util.Comparator;

public class AutoTool extends Feature {

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

	private final FeatureToggleComponent toggleComponent = new FeatureToggleComponent(this, "", false);
	private final KeybindingComponent keybindingComponent = new KeybindingComponent(this, new KeyMapping("anvilclient.feature." + getName() + ".toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, AnvilclientCommon.KEY_CATEGORY), toggleComponent::toggleEnabled);

	@Setting
	public IntegerSetting minDurability = new IntegerSetting(getName() + ".minDurability", "", 5, 0,
			(int) Byte.MAX_VALUE, 1.0F);

	@Setting
	public BooleanSetting revertTool = new BooleanSetting(getName() + ".revertTool", "", true);

	@Setting
	public EnumSetting<AutoTool.SilkTouchMode> silkTouchMode = new EnumSetting<AutoTool.SilkTouchMode>(
			getName() + ".silkTouchMode", "", AutoTool.SilkTouchMode.DOESNT_MATTER);

	@Override
	public void register() {
		addComponents(toggleComponent, keybindingComponent);
		super.register();
		PlayerDamageBlockEvent.STARTED.register(this::onPlayerDamageBlock);
		PlayerDamageBlockEvent.RESET.register(this::onPlayerResetBreakingBlock);
	}

	private boolean isDurabilityGood(ItemStack tool) {
		return minDurability.getValue() < 1 || ItemUtils.isUnbreakable(tool)
				|| ItemUtils.getDurability(tool) >= minDurability.getValue();
	}

	private boolean isDurabilityGood(Slot slot) {
		return isDurabilityGood(slot.getItem());
	}

	private boolean filterSilkTouchMode(ItemStack tool) {
		int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
		return switch (silkTouchMode.getValue()) {
			case DONT_USE -> level < 1;
			case USE_ONLY -> level >= 1;
			default -> true;
		};
	}

	private boolean filterSilkTouchMode(Slot slot) {
		return filterSilkTouchMode(slot.getItem());
	}

	private int getSilkTouchEnchantmentLevel(ItemStack tool) {
		int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
		return switch (silkTouchMode.getValue()) {
			case DONT_USE, PREFER_NOT_TO_USE -> level * -1;
			case DOESNT_MATTER -> 0;
			case PREFER, USE_ONLY -> level;
		};
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

	private void selectBestTool(BlockPos blockPos) {
		if (toggleComponent.isEnabled() && Minecraft.getInstance().gameMode.hasExperience()) {
			Slot bestTool = getBestTool(blockPos);
			LocalPlayer localPlayer = LocalPlayerUtils.getLocalPlayer();
			if (isOriginalTool) {
				originalTool = LocalPlayerUtils.getSelectedSlot(localPlayer);
			}
			LocalPlayerUtils.setSelectedSlot(localPlayer, bestTool);
			isOriginalTool = false;
		}
	}

	private void resetTool() {
		if (revertTool.getValue()) {
			if (!isOriginalTool) {
				LocalPlayerUtils.setSelectedSlot(LocalPlayerUtils.getLocalPlayer(), originalTool);
				isOriginalTool = true;
			}
		}
	}

	private void onPlayerDamageBlock(BlockPos blockPos, Direction directionFacing) {
		this.selectBestTool(blockPos);
	}

	private void onPlayerResetBreakingBlock() {
		this.resetTool();
	}

	public enum SilkTouchMode implements OptionEnum {
		DONT_USE,
		PREFER_NOT_TO_USE,
		DOESNT_MATTER,
		PREFER,
		USE_ONLY;

		private final String translationKey;

		private SilkTouchMode() {
			this.translationKey = "anvilclient.feature.autoTool.silkTouchMode." + this.toString().toLowerCase();
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
}
