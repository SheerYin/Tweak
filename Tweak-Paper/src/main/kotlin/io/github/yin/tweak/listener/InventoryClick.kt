package io.github.yin.tweak.listener

import io.github.yin.tweak.controller.InventoryController
import io.github.yin.tweak.controller.QuickShulkerBoxController
import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

object InventoryClick : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onInventoryClick(event: InventoryClickEvent) {

        val inventoryView = event.view

        val topInventory = inventoryView.topInventory

        val holder = topInventory.holder

        if (holder is QuickShulkerBoxHolder) {
            QuickShulkerBoxController.handleClick(event, inventoryView, topInventory, holder)
        } else {
            InventoryController.handleClick(event, inventoryView, topInventory)
        }


//        val clickType = event.click
//
//        val inventoryView = event.view
//        val topInventory = inventoryView.topInventory
//
//        when (clickType) {
//            ClickType.LEFT -> {
//                InventoryController.handleLeft(event, inventoryView, topInventory)
//            }
//
//            ClickType.SHIFT_LEFT -> return
//            ClickType.RIGHT -> {
//                InventoryController.handleRight(event, inventoryView, topInventory)
//            }
//
//            ClickType.SHIFT_RIGHT -> return
//            ClickType.WINDOW_BORDER_LEFT -> return
//            ClickType.WINDOW_BORDER_RIGHT -> return
//            ClickType.MIDDLE -> return
//            ClickType.NUMBER_KEY -> {
//                InventoryController.handleNumber(event, inventoryView, topInventory)
//            }
//            ClickType.DOUBLE_CLICK -> return
//            ClickType.DROP -> {
//                InventoryController.handleDrop(event, inventoryView, topInventory)
//            }
//
//            ClickType.CONTROL_DROP -> {
//                InventoryController.handleControlDrop(event, inventoryView, topInventory)
//            }
//
//            ClickType.CREATIVE -> return
//            ClickType.SWAP_OFFHAND -> {
//                InventoryController.handleSwap(event, inventoryView, topInventory)
//            }
//            ClickType.UNKNOWN -> return
//        }
    }


}