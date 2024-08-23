package io.github.yin.tweak.listener

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.common.Enumeration
import io.github.yin.tweak.inventory.holder.ViewHolder
import io.github.yin.tweak.service.SimpleShulkerBox
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.inventory.ItemStack

object PlayerDropItem : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player
        val holder = player.openInventory.topInventory.holder
        if (holder is ViewHolder) {
            val itemStack = event.itemDrop.itemStack
            save(player, itemStack, holder)
        }

    }

    private fun save(player: Player, itemStack: ItemStack, holder: ViewHolder) {
        if (itemStack.type in Enumeration.shulkerBoxes) {
            if (SimpleShulkerBox.saveInventory(itemStack, holder)) {
                player.playSound(player.location, SimpleShulkerBox.soundClose, 1.0f, 1.0f)
                player.closeInventory()

                Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
                    player.updateInventory()
                })
            }
        }
    }
}