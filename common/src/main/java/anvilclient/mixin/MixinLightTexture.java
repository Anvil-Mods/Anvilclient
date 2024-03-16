/*
 * Copyright (C) 2024 Ambossmann <https://github.com/Ambossmann>
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

import anvilclient.features.Features;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightTexture.class)
public class MixinLightTexture {
	@ModifyExpressionValue(
			at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1),
			method = "updateLightTexture(F)V")
	private float overrideGamma(float original) {
		return Features.FULLBRIGHT.getFeatureToggle().isEnabled()
				? Features.FULLBRIGHT.fullbrightLevel.getFloatValue()
				: original;
	}
}
