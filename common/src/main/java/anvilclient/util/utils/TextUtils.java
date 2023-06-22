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

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.util.Arrays;

public class TextUtils {

	private TextUtils() {
	}

	public static String getFormattedText(Component textComponent) {
		StringBuilder stringbuilder = new StringBuilder();
		String s = textComponent.getString();
		if (!s.isEmpty()) {
			stringbuilder.append(getFormattingCodeOfStyle(textComponent.getStyle()));
			stringbuilder.append(s);
			stringbuilder.append(ChatFormatting.RESET.toString());
		}

		textComponent.getSiblings().stream()
				.forEachOrdered(textComp -> stringbuilder.append(getFormattedText(textComp)));

		return stringbuilder.toString();
	}

	public static String getFormattingCodeOfStyle(Style style) {

		StringBuilder stringbuilder = new StringBuilder();
		TextColor color = style.getColor();
		if (color != null) {
			stringbuilder.append(Arrays.stream(ChatFormatting.values()).filter(ChatFormatting::isColor)
					.filter(formatting -> formatting.getColor() == color.getValue()).map(ChatFormatting::toString)
					.findFirst().orElse(""));
		}

		if (style.isBold()) {
			stringbuilder.append(ChatFormatting.BOLD.toString());
		}

		if (style.isItalic()) {
			stringbuilder.append(ChatFormatting.ITALIC.toString());
		}

		if (style.isUnderlined()) {
			stringbuilder.append(ChatFormatting.UNDERLINE.toString());
		}

		if (style.isObfuscated()) {
			stringbuilder.append(ChatFormatting.OBFUSCATED.toString());
		}

		if (style.isStrikethrough()) {
			stringbuilder.append(ChatFormatting.STRIKETHROUGH.toString());
		}

		return stringbuilder.toString();
	}

	public static String removeFormattingCodes(String string) {
		return string.replaceAll("\u00A7.", "");
	}

}
