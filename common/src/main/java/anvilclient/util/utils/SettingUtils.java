/*
 * Copyright (C) 2021-2024 Ambossmann <https://github.com/Ambossmann>
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
package anvilclient.util.utils;

import anvilclient.features.Feature;
import anvilclient.features.components.ToggleComponent;
import anvilclient.gui.config.FeatureGui;
import anvilclient.settings.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;
import org.jetbrains.annotations.NotNull;

public class SettingUtils {

	private SettingUtils() {}

	@SuppressWarnings("unchecked")
	public static <T> OptionInstance<T> getOptionForSetting(ISetting<T> setting) {
		OptionInstance<T> option = null;
		String translationKey = "anvilclient.feature." + setting.getKey();
		if (setting instanceof BooleanSetting booleanSetting) {
			option =
					(OptionInstance<T>)
							OptionInstance.createBoolean(
									translationKey, booleanSetting.getValue(), booleanSetting::setValue);

		} else if (setting instanceof EnumSetting<?> enumSetting) {
			option = (OptionInstance<T>) optionInstanceForEnum(enumSetting, translationKey);

		} else if (setting instanceof IntegerSetting integerSetting) {
			option =
					(OptionInstance<T>)
							new OptionInstance<Integer>(
									translationKey,
									OptionInstance.noTooltip(),
									(component, integer) ->
											Options.genericValueLabel(component, integerSetting.getIntValue()),
									new OptionInstance.IntRange(
											integerSetting.getMinValue(), integerSetting.getMaxValue()),
									integerSetting.getValue(),
									integerSetting::setValue);

		} else if (setting instanceof DoubleSetting doubleSetting) {
			option =
					(OptionInstance<T>)
							new OptionInstance<Double>(
									translationKey,
									OptionInstance.noTooltip(),
									(component, doubleValue) ->
											Options.genericValueLabel(
													component,
													Component.literal(
															MathUtils.formatDouble(
																	doubleValue, doubleSetting.getDecimalCount()))),
									new DoubleRange(
											doubleSetting.getMinValue(),
											doubleSetting.getMaxValue(),
											doubleSetting.getDecimalCount()),
									doubleSetting.getValue(),
									doubleSetting::setValue);
		}
		return option;
	}

	private static <T extends Enum<T> & OptionEnum> OptionInstance<T> optionInstanceForEnum(
			EnumSetting<T> enumSetting, String translationKey) {
		return new OptionInstance<T>(
				translationKey,
				OptionInstance.noTooltip(),
				OptionInstance.forOptionEnum(),
				new OptionInstance.Enum<T>(
						Arrays.asList(enumSetting.getValues()),
						Codec.INT.xmap(
								integer -> enumSetting.getValues()[integer],
								t -> enumSetting.getValue().ordinal())),
				enumSetting.getValue(),
				enumSetting::setValue);
	}

	public static OptionInstance[] getOptionListForFeature(Feature feature, Screen screen) {
		String translationKey = "anvilclient.feature." + feature.getName();
		OptionInstance[] options = new OptionInstance[2];
		ToggleComponent toggleComponent = feature.getFeatureToggle();
		options[0] =
				OptionInstance.createBoolean(
						translationKey, toggleComponent.isEnabled(), toggleComponent::setEnabled);
		options[1] =
				getClickOption(
						translationKey,
						() -> Minecraft.getInstance().setScreen(new FeatureGui(feature, screen)));
		return options;
	}

	public static OptionInstance<Object> getClickOption(
			String translationKey, Runnable pressedAction) {
		return new OptionInstance<>(
				translationKey,
				OptionInstance.noTooltip(),
				((component, object) -> component),
				new ClickValueSet(pressedAction),
				null,
				(object) -> {});
	}

	private static class ClickValueSet implements OptionInstance.ValueSet<Object> {

		private Runnable action;

		public ClickValueSet(Runnable action) {
			this.action = action;
		}

		@Override
		public @NotNull Function<OptionInstance<Object>, AbstractWidget> createButton(
				OptionInstance.TooltipSupplier<Object> tooltipSupplier,
				Options options,
				int i,
				int j,
				int k,
				Consumer<Object> consumer) {
			return optionInstance ->
					Button.builder(optionInstance.caption, button -> action.run()).pos(i, j).width(k).build();
		}

		@Override
		public @NotNull Optional<Object> validateValue(Object object) {
			return Optional.empty();
		}

		@Override
		public @NotNull Codec<Object> codec() {
			return new Codec<Object>() {
				@Override
				public <T> DataResult<Pair<Object, T>> decode(DynamicOps<T> ops, T input) {
					return null;
				}

				@Override
				public <T> DataResult<T> encode(Object input, DynamicOps<T> ops, T prefix) {
					return null;
				}
			};
		}
	}

	public record DoubleRange(double minInclusive, double maxInclusive, int decimalCount)
			implements DoubleRangeBase {
		@Override
		public Optional<Double> validateValue(Double d) {
			return d >= this.minInclusive() && d <= this.maxInclusive()
					? Optional.of(d)
					: Optional.empty();
		}

		@Override
		public Codec<Double> codec() {
			return Codec.doubleRange(this.minInclusive, this.maxInclusive);
		}
	}

	interface DoubleRangeBase extends OptionInstance.SliderableValueSet<Double> {
		double minInclusive();

		double maxInclusive();

		int decimalCount();

		@Override
		default double toSliderValue(Double d) {
			return Mth.map(
					MathUtils.trimDouble(d, decimalCount()),
					this.minInclusive(),
					this.maxInclusive(),
					0.0,
					1.0);
		}

		@Override
		default Double fromSliderValue(double d) {
			return MathUtils.trimDouble(
					Mth.map(d, 0.0, 1.0, this.minInclusive(), this.maxInclusive()), decimalCount());
		}
	}
}
