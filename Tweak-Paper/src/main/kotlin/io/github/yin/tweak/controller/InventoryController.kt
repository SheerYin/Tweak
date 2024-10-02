package io.github.yin.tweak.controller

import io.github.yin.tweak.cache.PlayerInventorySlotLockCache
import io.github.yin.tweak.service.QuickEnderChestService
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView

object InventoryController {

    fun handleClick(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val clickType = event.click
        when (clickType) {
            ClickType.LEFT -> {
                f1(event, inventoryView, topInventory)
            }

            ClickType.SHIFT_LEFT -> {
                f1(event, inventoryView, topInventory)
            }

            ClickType.RIGHT -> {
                f3(event, inventoryView, topInventory)
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
        if (PlayerInventorySlotLockCache.map[player.name] == slot) {
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
        val index = PlayerInventorySlotLockCache.map[player.name]

        if (hot == index || slot == index) {
            event.isCancelled = true
        }
    }

    private fun f3(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
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
                    val player = inventoryView.player
                    QuickEnderChestService.inventoryOpen(topInventory, player)
                }
            } else {
                event.isCancelled = true
                val slot = event.slot
                val player = inventoryView.player
                QuickShulkerBoxService.inventoryOpen(current, title, slot, player)
            }
        }
    }


//    fun handleLeft(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
//        val holder = topInventory.holder as? QuickShulkerBoxHolder ?: return
//
//        val rawSlot = event.rawSlot
//        if (rawSlot < topInventory.size) {
//            return
//        }
//
//        val slot = event.slot
//        if (slot == holder.index) {
//            event.isCancelled = true
//        }
//    }
//
//    fun handleRight(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
//        val rawSlot = event.rawSlot
//        if (rawSlot < topInventory.size) {
//            return
//        }
//
//        val current = event.currentItem ?: return
//        if (current.amount == 1) {
//            val material = current.type
//            val title = QuickShulkerBoxService.shulkerBoxColors[material]
//            if (title == null) {
//                if (material == QuickEnderChestService.enderChest) {
//                    event.isCancelled = true
//                    val player = inventoryView.player as Player
//                    QuickEnderChestService.open(topInventory, player)
//                }
//            } else {
//                event.isCancelled = true
//                val slot = event.slot
//                QuickShulkerBoxService.open(inventoryView, topInventory, current, title, slot)
//            }
//        }
//    }
//
//    fun handleDrop(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
//        val holder = topInventory.holder as? QuickShulkerBoxHolder ?: return
//
//        val rawSlot = event.rawSlot
//        if (rawSlot < topInventory.size) {
//            return
//        }
//
//        val slot = event.slot
//        if (slot == holder.index) {
//            event.isCancelled = true
//        }
//    }
//
//    fun handleControlDrop(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
//        val holder = topInventory.holder as? QuickShulkerBoxHolder ?: return
//
//        val rawSlot = event.rawSlot
//        if (rawSlot < topInventory.size) {
//            return
//        }
//
//        val slot = event.slot
//        if (slot == holder.index) {
//            event.isCancelled = true
//        }
//    }
//
//
//    fun handleNumber(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
//        val holder = topInventory.holder as? QuickShulkerBoxHolder ?: return
//
//        val rawSlot = event.rawSlot
//        if (rawSlot < topInventory.size) {
//            return
//        }
//
//        val hot = event.hotbarButton
//        val slot = event.slot
//        val index = holder.index
//        if (hot == index || slot == index) {
//            event.isCancelled = true
//        }
//    }
//
//    fun handleSwap(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
//        val holder = topInventory.holder as? QuickShulkerBoxHolder ?: return
//
//        val rawSlot = event.rawSlot
//        if (rawSlot < topInventory.size) {
//            return
//        }
//
//        val slot = event.slot
//        if (slot == holder.index) {
//            event.isCancelled = true
//        }
//    }

}