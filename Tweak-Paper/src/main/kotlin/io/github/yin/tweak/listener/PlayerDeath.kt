package io.github.yin.tweak.listener

import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeath : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.player
        val inventoryView = player.openInventory
        val topInventory = inventoryView.topInventory
        val holder = topInventory.holder

        if (holder is QuickShulkerBoxHolder) {
            QuickShulkerBoxService.close(inventoryView, holder)
        }
    }


}