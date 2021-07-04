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
package anvilclient.anvilclient.util.utils;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;

public class ItemUtils {
	
	private ItemUtils() {
	}
	
	public static int getDurability(ItemStack item) {
		return item.isDamageableItem() ? item.getMaxDamage() - item.getDamageValue() : 0;
	}
	
	public static boolean isUnbreakable(ItemStack item) {
		return item.isEmpty() || !item.isDamageableItem();
	}

	public static double getDiggingSpeed(PlayerEntity player, ItemStack tool, BlockState blockState) {
		float destroySpeed = tool.getDestroySpeed(blockState);
		if (destroySpeed > 1.0F) {
			int efficiencyLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, tool);
			if (efficiencyLevel > 0 && !tool.isEmpty()) {
				destroySpeed += (float) (efficiencyLevel * efficiencyLevel + 1);
			}
		}

		if (EffectUtils.hasDigSpeed(player)) {
			destroySpeed *= 1.0F + (float) (EffectUtils.getDigSpeedAmplification(player) + 1) * 0.2F;
		}

		if (player.hasEffect(Effects.DIG_SLOWDOWN)) {
			float f1;
			switch (player.getEffect(Effects.DIG_SLOWDOWN).getAmplifier()) {
			case 0:
				f1 = 0.3F;
				break;
			case 1:
				f1 = 0.09F;
				break;
			case 2:
				f1 = 0.0027F;
				break;
			case 3:
			default:
				f1 = 8.1E-4F;
			}

			destroySpeed *= f1;
		}

		if (player.isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(player)) {
			destroySpeed /= 5.0F;
		}

		if (!player.isOnGround()) {
			destroySpeed /= 5.0F;
		}

		return destroySpeed;
	}

	public static double getDiggingSpeedAt(PlayerEntity player, ItemStack tool, BlockPos blockPos) {
		return getDiggingSpeed(player, tool, player.level.getBlockState(blockPos));
	}

}
