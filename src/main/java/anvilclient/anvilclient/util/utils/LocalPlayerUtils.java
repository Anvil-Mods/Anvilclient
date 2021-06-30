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
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class LocalPlayerUtils {
	
	private LocalPlayerUtils() {
	}

	public static ClientPlayerEntity getLocalPlayer() {
		return Minecraft.getInstance().player;
	}

	public static PlayerInventory getPlayerInventory(ClientPlayerEntity localPlayer) {
		return localPlayer.inventory;
	}

	public static PlayerContainer getPlayerContainer(ClientPlayerEntity localPlayer) {
		return localPlayer.container;
	}
	
	public static int getHotbarSize() {
		return PlayerInventory.getHotbarSize();
	}

	public static List<Slot> getSlots(ClientPlayerEntity localPlayer) {
		return getPlayerContainer(localPlayer).inventorySlots;
	}

	public static List<Slot> getHotbarSlots(ClientPlayerEntity localPlayer) {
		return getSlots(localPlayer).subList(36, 45);
	}

	public static int getSelectedIndex(ClientPlayerEntity localPlayer) {
		return getPlayerInventory(localPlayer).currentItem;
	}

	public static ItemStack getSelectedItem(ClientPlayerEntity localPlayer) {
		return getPlayerInventory(localPlayer).getCurrentItem();
	}

	public static Slot getSelectedSlot(ClientPlayerEntity localPlayer) {
		return getHotbarSlots(localPlayer).get(getSelectedIndex(localPlayer));
	}
	
	public static void setSelectedIndex(ClientPlayerEntity localPlayer, int index) {
		if (index < 0 || index > getHotbarSize() - 1) {
		      throw new IllegalArgumentException("index must be between 0 and " + (getHotbarSize() - 1) + ", got " + index);
		}
		
		getPlayerInventory(localPlayer).currentItem = index;
	}
	
	public static void setSelectedSlot(ClientPlayerEntity localPlayer, Slot slot) {
		setSelectedIndex(localPlayer, slot.getSlotIndex());
	}
}
