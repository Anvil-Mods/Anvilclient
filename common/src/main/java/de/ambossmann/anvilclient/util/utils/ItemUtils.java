/*
 * Copyright (C) 2021-2025 Ambossmann <https://github.com/Ambossmann>
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
package de.ambossmann.anvilclient.util.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ItemUtils {

	private ItemUtils() {}

	public static int getDurability(ItemStack item) {
		return item.isDamageableItem() ? item.getMaxDamage() - item.getDamageValue() : 0;
	}

	public static boolean isUnbreakable(ItemStack item) {
		return item.isEmpty() || !item.isDamageableItem();
	}

	public static float getDiggingSpeed(Player player, ItemStack tool, BlockState blockState) {
		float destroySpeed = tool.getDestroySpeed(blockState);
		if (destroySpeed > 1.0F) {
			destroySpeed += (float) player.getAttributeValue(Attributes.MINING_EFFICIENCY);
		}

		if (MobEffectUtil.hasDigSpeed(player)) {
			destroySpeed *= 1.0F + (MobEffectUtil.getDigSpeedAmplification(player) + 1) * 0.2F;
		}

		if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
			destroySpeed *=
					switch (player.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) {
						case 0 -> 0.3f;
						case 1 -> 0.09f;
						case 2 -> 0.0027f;
						default -> 8.1E-4f;
					};
		}

		destroySpeed *= (float) player.getAttributeValue(Attributes.BLOCK_BREAK_SPEED);
		if (player.isEyeInFluid(FluidTags.WATER)) {
			destroySpeed *= (float) player.getAttribute(Attributes.SUBMERGED_MINING_SPEED).getValue();
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
