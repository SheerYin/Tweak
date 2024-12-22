package io.github.yin.tweak.listener

import io.github.yin.tweak.common.Enumeration
import io.github.yin.tweak.inventory.holder.ShulkerViewHolder
import io.github.yin.tweak.service.SimpleShulkerBox
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

object InventoryClose : Listener {

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val holder = event.inventory.holder
        if (holder is ShulkerViewHolder) {
            if (holder.save) {
                return
            }
            shulkerSave(event, holder)
        }
    }

    private fun shulkerSave(event: InventoryCloseEvent, holder: ShulkerViewHolder) {
        val inventoryView = event.view
        val cursor = inventoryView.cursor ?: return
        val player = inventoryView.player as Player
        if (cursor.type in Enumeration.shulkerBoxes) {
            if (SimpleShulkerBox.saveInventory(cursor, holder)) {
                player.playSound(player.location, SimpleShulkerBox.soundClose, 1.0f, 1.0f)
                return // 保存成功提前返回
            }
        }
        for (stack in player.inventory.contents) {
            if (stack != null && stack.type in Enumeration.shulkerBoxes) {
                if (SimpleShulkerBox.saveInventory(stack, holder)) {
                    player.playSound(player.location, SimpleShulkerBox.soundClose, 1.0f, 1.0f)
                    return // 保存成功提前返回
                }
            }
        }
    }

}