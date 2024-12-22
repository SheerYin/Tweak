package io.github.yin.tweak.controller

import io.github.yin.tweak.cache.InventorySlotLockCache
import io.github.yin.tweak.service.QuickEnderChestService
import io.github.yin.tweak.service.QuickShulkerBoxService
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

object InventoryController {

    fun handleClick(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val clickType = event.click
        when (clickType) {
            ClickType.LEFT -> {
                move(event, inventoryView, topInventory)
            }

            ClickType.SHIFT_LEFT -> {
                move(event, inventoryView, topInventory)
            }

            ClickType.RIGHT -> {
                right(event, inventoryView, topInventory)
            }

            ClickType.SHIFT_RIGHT -> {
                move(event, inventoryView, topInventory)
            }

            ClickType.WINDOW_BORDER_LEFT -> {}
            ClickType.WINDOW_BORDER_RIGHT -> {}
            ClickType.MIDDLE -> {}
            ClickType.NUMBER_KEY -> {
                number(event, inventoryView, topInventory)
            }

            ClickType.DOUBLE_CLICK -> {
                move(event, inventoryView, topInventory)
            }

            ClickType.DROP -> {
                move(event, inventoryView, topInventory)
            }

            ClickType.CONTROL_DROP -> {
                move(event, inventoryView, topInventory)
            }

            ClickType.CREATIVE -> {}
            ClickType.SWAP_OFFHAND -> {
                move(event, inventoryView, topInventory)
            }

            ClickType.UNKNOWN -> {}
        }
    }

    // 当格位被锁定时，不应该被移动或变化。否则欺诈
    // 常规手段尝试移动被锁定物品
    private fun move(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
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

    // 当格位被锁定时，不应该被移动或变化。否则欺诈
    // 数字键尝试移动被锁定物品
    private fun number(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
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

    // 在库存右键
    private fun right(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory) {
        val rawSlot = event.rawSlot
        if (rawSlot < topInventory.size) {
            return
        }

        val current = event.currentItem ?: return
        if (current.amount == 1) {
            val material = current.type
            val title = QuickShulkerBoxService.shulkerBoxColors[material]
            if (title == null) {
                workItemStack(event, inventoryView, topInventory, material)
            } else {
                containerItemStack(event, inventoryView, current, title)
            }
        }
    }

    fun workItemStack(event: InventoryClickEvent, inventoryView: InventoryView, topInventory: Inventory, material: Material) {
        val player = inventoryView.player as Player

        if (material == QuickEnderChestService.enderChest) {
            if (!player.hasPermission("tweak.quick.enderchest")) {
                return
            }
            event.isCancelled = true
            QuickEnderChestService.inventoryOpen(topInventory, player)
        }
    }

    fun containerItemStack(event: InventoryClickEvent, inventoryView: InventoryView, current: ItemStack, title: Component) {
        val player = inventoryView.player as Player

        if (!player.hasPermission("tweak.quick.shulkerbox")) {
            return
        }

        event.isCancelled = true
        val slot = event.slot
        QuickShulkerBoxService.inventoryOpen(current, title, slot, player)
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