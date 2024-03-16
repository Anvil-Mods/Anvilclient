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
package anvilclient.mixin;

import anvilclient.event.PlayerDamageBlockEvent;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MixinMultiPlayerGameMode {
	@Inject(at = @At("HEAD"), method = "continueDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z")
	private void continueDestroyBlock(BlockPos blockPos, Direction directionFacing,
			CallbackInfoReturnable<Boolean> callback) {
		PlayerDamageBlockEvent.STARTED.invoker().started(blockPos, directionFacing);
	}

	@Inject(at = @At("HEAD"), method = "stopDestroyBlock")
	private void stopDestroyBlock(CallbackInfo callback) {
		PlayerDamageBlockEvent.RESET.invoker().reset();
	}
}