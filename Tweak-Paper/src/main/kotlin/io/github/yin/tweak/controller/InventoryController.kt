package io.github.yin.tweak.controller

import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import io.github.yin.tweak.service.QuickEnderChestService
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView

object InventoryController {

    fun handleLeft(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val holder = topInventory.holder as? QuickShulkerBoxHolder ?: return

        val rawSlot = event.rawSlot
        if (rawSlot < topInventory.size) {
            return
        }

        val current = event.currentItem ?: return
        if (current.type in QuickShulkerBoxService.shulkerBoxes) {
            val slot = event.slot
            if (slot == holder.index) {
                event.isCancelled = true
            }
        }
    }

    fun handleRight(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
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
                    QuickEnderChestService.open(topInventory, player)
                }
            } else {
                event.isCancelled = true
                val index = event.slot
                QuickShulkerBoxService.open(inventoryView, topInventory, current, title, index)
            }
        }
    }

    fun handleDrop(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val rawSlot = event.rawSlot
        if (rawSlot < topInventory.size) {
            return
        }

        val current = event.currentItem ?: return
        if (current.type in QuickShulkerBoxService.shulkerBoxes) {
            val holder = topInventory.holder
            if (holder is QuickShulkerBoxHolder) {
                val index = event.slot
                if (index == holder.index) {
                    event.isCancelled = true
                }
            }
        }
    }

    fun handleControlDrop(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val holder = topInventory.holder as? QuickShulkerBoxHolder ?: return

        val rawSlot = event.rawSlot
        if (rawSlot < topInventory.size) {
            return
        }

        val current = event.currentItem ?: return
        if (current.type in QuickShulkerBoxService.shulkerBoxes) {
            val slot = event.slot
            if (slot == holder.index) {
                event.isCancelled = true
            }
        }
    }


    fun handleNumber(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val holder = topInventory.holder as? QuickShulkerBoxHolder ?: return

        val rawSlot = event.rawSlot
        if (rawSlot < topInventory.size) {
            return
        }

        val hot = event.hotbarButton
        val hotber = inventoryView.bottomInventory.getItem(hot)
        val current = event.currentItem

        if ((hotber?.type in QuickShulkerBoxService.shulkerBoxes && hot == holder.index) || (current?.type in QuickShulkerBoxService.shulkerBoxes && event.slot == holder.index)) {
            event.isCancelled = true
        }
    }

    fun handleSwap(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val holder = topInventory.holder as? QuickShulkerBoxHolder ?: return

        val rawSlot = event.rawSlot
        if (rawSlot < topInventory.size) {
            return
        }

        val current = event.currentItem ?: return
        if (current.type in QuickShulkerBoxService.shulkerBoxes) {
            val slot = event.slot
            if (slot == holder.index) {
                event.isCancelled = true
            }
        }
    }

}