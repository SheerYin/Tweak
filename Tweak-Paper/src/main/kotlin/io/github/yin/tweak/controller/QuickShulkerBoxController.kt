package io.github.yin.tweak.controller

import io.github.yin.tweak.cache.InventorySlotLockCache
import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import io.github.yin.tweak.service.QuickEnderChestService
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView

object QuickShulkerBoxController {

    fun handleClick(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory, holder: QuickShulkerBoxHolder) {
        val clickType = event.click
        when (clickType) {
            ClickType.LEFT -> {
                f1(event, inventoryView, topInventory)
            }

            ClickType.SHIFT_LEFT -> {
                f1(event, inventoryView, topInventory)
            }

            ClickType.RIGHT -> {
                f3(event, inventoryView, topInventory, holder)
            }

            ClickType.SHIFT_RIGHT -> {
                f1(event, inventoryView, topInventory)
            }

            ClickType.WINDOW_BORDER_LEFT -> {}
            ClickType.WINDOW_BORDER_RIGHT -> {}
            ClickType.MIDDLE -> {}
            ClickType.NUMBER_KEY -> {
                f2(event, inventoryView, topInventory)
            }

            ClickType.DOUBLE_CLICK -> {
                f1(event, inventoryView, topInventory)
            }

            ClickType.DROP -> {
                f1(event, inventoryView, topInventory)
            }

            ClickType.CONTROL_DROP -> {
                f1(event, inventoryView, topInventory)
            }

            ClickType.CREATIVE -> {}
            ClickType.SWAP_OFFHAND -> {
                f1(event, inventoryView, topInventory)
            }

            ClickType.UNKNOWN -> {}
        }
    }

    private fun f1(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val rawSlot = event.rawSlot
        if (rawSlot < topInventory.size) {
            return
        }

        val player = inventoryView.player
        val slot = event.slot
        if (InventorySlotLockCache.map[player.name] == slot) {
            event.isCancelled = true
        }
    }

    private fun f2(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val rawSlot = event.rawSlot
        if (rawSlot < topInventory.size) {
            return
        }

        val hot = event.hotbarButton
        val slot = event.slot

        val player = inventoryView.player
        val index = InventorySlotLockCache.map[player.name]

        if (hot == index || slot == index) {
            event.isCancelled = true
        }
    }

    private fun f3(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory, holder: QuickShulkerBoxHolder) {
        val rawSlot = event.rawSlot
        if (rawSlot < topInventory.size) {
            return
        }

        val current = event.currentItem ?: return
        if (current.amount == 1) {
            val material = current.type
            val title = QuickShulkerBoxService.shulkerBoxColors[material]
            if (title == null) {
                if (material == QuickEnderChestService.enderChest) {
                    event.isCancelled = true
                    val player = inventoryView.player as Player
                    QuickEnderChestService.inventoryOpen(topInventory, player)
                }
            } else {
                event.isCancelled = true
                val slot = event.slot
                val player = inventoryView.player as Player
                QuickShulkerBoxService.holderOpen(inventoryView, holder, current, title, slot, player)
            }
        }
    }

}