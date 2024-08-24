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
import org.bukkit.event.inventory.InventoryCloseEvent

object InventoryClose : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onInventoryClose(event: InventoryCloseEvent) {
        val holder = event.inventory.holder
        if (holder is ViewHolder) {
            if (!holder.save) {
                return
            }
            save(event, holder)
        }
    }

    private fun save(event: InventoryCloseEvent, holder: ViewHolder) {
        val inventoryView = event.view
        val cursor = inventoryView.cursor
        val player = inventoryView.player as Player
        if (cursor.type in Enumeration.shulkerBoxes) {
            if (SimpleShulkerBox.saveInventory(cursor, holder)) {
                player.playSound(player.location, SimpleShulkerBox.soundClose, 1.0f, 1.0f)
                Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
                    player.updateInventory()
                })
                return // 保存成功提前返回
            }
        }
        for (itemStack in player.inventory.contents) {
            if (itemStack != null && itemStack.type in Enumeration.shulkerBoxes) {
                if (SimpleShulkerBox.saveInventory(itemStack, holder)) {
                    player.playSound(player.location, SimpleShulkerBox.soundClose, 1.0f, 1.0f)
                    Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
                        player.updateInventory()
                    })
                    return // 保存成功提前返回
                }
            }
        }
    }

}