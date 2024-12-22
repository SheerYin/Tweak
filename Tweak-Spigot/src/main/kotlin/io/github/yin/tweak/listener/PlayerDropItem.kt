package io.github.yin.tweak.listener

import io.github.yin.tweak.common.Enumeration
import io.github.yin.tweak.inventory.holder.ShulkerViewHolder
import io.github.yin.tweak.service.SimpleShulkerBox
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

object PlayerDropItem : Listener {

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player
        shulkerCancel(event, player)

    }

    fun shulkerCancel(event: PlayerDropItemEvent, player: Player) {
        val holder = player.openInventory.topInventory.holder
        if (holder is ShulkerViewHolder) {
            val itemStack = event.itemDrop.itemStack
            if (itemStack.type in Enumeration.shulkerBoxes) {
                if (SimpleShulkerBox.check(itemStack, holder)) {
                    event.isCancelled = true
                }
            }
        }
    }

}