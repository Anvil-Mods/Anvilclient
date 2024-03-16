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

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;

public class ItemUtils {

	private ItemUtils() {
	}

	public static int getDurability(ItemStack item) {
		return item.isDamageableItem() ? item.getMaxDamage() - item.getDamageValue() : 0;
	}

	public static boolean isUnbreakable(ItemStack item) {
		return item.isEmpty() || !item.isDamageableItem();
	}

	public static float getDiggingSpeed(Player player, ItemStack tool, BlockState blockState) {
		float destroySpeed = tool.getDestroySpeed(blockState);
		if (destroySpeed > 1.0F) {
			int efficiencyLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, tool);
			if (efficiencyLevel > 0 && !tool.isEmpty()) {
				destroySpeed += (float) (efficiencyLevel * efficiencyLevel + 1);
			}
		}

		if (MobEffectUtil.hasDigSpeed(player)) {
			destroySpeed *= 1.0F + (float) (MobEffectUtil.getDigSpeedAmplification(player) + 1) * 0.2F;
		}

		if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
			destroySpeed *= (switch (player.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) {
				case 0 -> 0.3f;
				case 1 -> 0.09f;
				case 2 -> 0.0027f;
				default -> 8.1E-4f;
			});
		}

		if (player.isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(player)) {
			destroySpeed /= 5.0F;
		}

		if (!player.onGround()) {
			destroySpeed /= 5.0F;
		}

		return destroySpeed;
	}

	public static float getDiggingSpeedAt(Player player, ItemStack tool, BlockPos blockPos) {
		return getDiggingSpeed(player, tool, player.level().getBlockState(blockPos));
	}

}
