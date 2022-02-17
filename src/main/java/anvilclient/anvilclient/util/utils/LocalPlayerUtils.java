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

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LocalPlayerUtils {
	
	private LocalPlayerUtils() {
	}

	@SuppressWarnings("resource")
	public static LocalPlayer getLocalPlayer() {
		return Minecraft.getInstance().player;
	}

	public static Inventory getPlayerInventory(LocalPlayer localPlayer) {
		return localPlayer.getInventory();
	}

	public static InventoryMenu getPlayerContainer(LocalPlayer localPlayer) {
		return localPlayer.inventoryMenu;
	}
	
	public static int getHotbarSize() {
		return Inventory.getSelectionSize();
	}

	public static List<Slot> getSlots(LocalPlayer localPlayer) {
		return getPlayerContainer(localPlayer).slots;
	}

	public static List<Slot> getHotbarSlots(LocalPlayer localPlayer) {
		return getSlots(localPlayer).subList(36, 45);
	}

	public static int getSelectedIndex(LocalPlayer localPlayer) {
		return getPlayerInventory(localPlayer).selected;
	}

	public static ItemStack getSelectedItem(LocalPlayer localPlayer) {
		return getPlayerInventory(localPlayer).getSelected();
	}

	public static Slot getSelectedSlot(LocalPlayer localPlayer) {
		return getHotbarSlots(localPlayer).get(getSelectedIndex(localPlayer));
	}
	
	public static void setSelectedIndex(LocalPlayer localPlayer, int index) {
		if (index < 0 || index > getHotbarSize() - 1) {
		      throw new IllegalArgumentException("index must be between 0 and " + (getHotbarSize() - 1) + ", got " + index);
		}
		
		getPlayerInventory(localPlayer).selected = index;
	}
	
	public static void setSelectedSlot(LocalPlayer localPlayer, Slot slot) {
		setSelectedIndex(localPlayer, slot.getSlotIndex());
	}
}
