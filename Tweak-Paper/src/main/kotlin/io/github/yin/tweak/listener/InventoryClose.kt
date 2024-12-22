package io.github.yin.tweak.listener

import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

object InventoryClose : Listener {

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val inventoryView = event.view
        val topInventory = inventoryView.topInventory
        val holder = topInventory.holder

        if (holder is QuickShulkerBoxHolder) {
            QuickShulkerBoxService.close(inventoryView, holder)
        }
    }

}