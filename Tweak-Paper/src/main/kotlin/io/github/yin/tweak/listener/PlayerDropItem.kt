package io.github.yin.tweak.listener

import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

object PlayerDropItem : Listener {

//    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
//    fun onPlayerDropItem(event: PlayerDropItemEvent) {
//        val player = event.player
//
//        val inventoryView = player.openInventory
//        val topInventory = inventoryView.topInventory
//        val holder = topInventory.holder
//
//        if (holder is QuickShulkerBoxHolder) {
//            val itemStack = event.itemDrop.itemStack
//            if (QuickShulkerBoxService.check(itemStack, holder)) {
//                event.isCancelled = true
//                return
//            }
//        }
//    }

}