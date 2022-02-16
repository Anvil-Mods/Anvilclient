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

import java.util.Arrays;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class TextUtils {

	private TextUtils() {
	}

	public static String getFormattedText(ITextComponent textComponent) {
		StringBuilder stringbuilder = new StringBuilder();
		String s = textComponent.getString();
		if (!s.isEmpty()) {
			stringbuilder.append(getFormattingCodeOfStyle(textComponent.getStyle()));
			stringbuilder.append(s);
			stringbuilder.append(TextFormatting.RESET.toString());
		}
		
		textComponent.getSiblings().stream().forEachOrdered(textComp -> stringbuilder.append(getFormattedText(textComp)));

		return stringbuilder.toString();
	}

	public static String getFormattingCodeOfStyle(Style style) {

		StringBuilder stringbuilder = new StringBuilder();
		Color color = style.getColor();
		if (color != null) {
			stringbuilder.append(Arrays.stream(TextFormatting.values()).filter(TextFormatting::isColor)
					.filter(formatting -> formatting.getColor() == color.getValue()).map(TextFormatting::toString)
					.findFirst().orElse(""));
		}

		if (style.isBold()) {
			stringbuilder.append(TextFormatting.BOLD.toString());
		}

		if (style.isItalic()) {
			stringbuilder.append(TextFormatting.ITALIC.toString());
		}

		if (style.isUnderlined()) {
			stringbuilder.append(TextFormatting.UNDERLINE.toString());
		}

		if (style.isObfuscated()) {
			stringbuilder.append(TextFormatting.OBFUSCATED.toString());
		}

		if (style.isStrikethrough()) {
			stringbuilder.append(TextFormatting.STRIKETHROUGH.toString());
		}

		return stringbuilder.toString();
	}
	
	public static String removeFormattingCodes(String string) {
		return string.replaceAll("\u00A7.", "");
	}

}
