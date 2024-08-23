package io.github.yin.tweak.listener

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.common.Enumeration
import io.github.yin.tweak.inventory.holder.ViewHolder
import io.github.yin.tweak.service.SimpleShulkerBox
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

object InventoryClick : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onInventoryClick(event: InventoryClickEvent) {
        val rawSlot = event.rawSlot
        val inventoryView = event.view
        val topSize = inventoryView.topInventory.size
        if (rawSlot < topSize) {
            // 如果点击的是上方 inventory
            return
        }

        val clickType = event.click
        if (clickType == ClickType.RIGHT) {
            val current = event.currentItem ?: return
            handleClick(event, inventoryView, current)
        }
    }

    private fun handleClick(event: InventoryClickEvent, inventoryView: InventoryView, current: ItemStack) {
        val title = Enumeration.shulkerBoxColors[current.type]
        if (title == null) {
            val player = inventoryView.player as Player
            if (current.type == Material.ENDER_CHEST) {
                enderChest(player)
            }
        } else {
            shulker(event, inventoryView, current, title)
        }
    }

    private fun shulker(event: InventoryClickEvent, inventoryView: InventoryView, current: ItemStack, title: String) {
        if (current.amount == 1) {
            event.isCancelled = true

            val holder = event.inventory.holder
            val player = inventoryView.player as Player
            if (holder is ViewHolder) {
                val cursor = inventoryView.cursor
                var saveSuccess = false
                if (cursor != null && cursor.type in Enumeration.shulkerBoxes) {
                    // 如果保存成功
                    saveSuccess = SimpleShulkerBox.saveInventory(cursor, holder)
                }
                // 保存成功后不再尝试遍历玩家 inventory
                if (!saveSuccess) {
                    for (itemStack in player.inventory.contents) {
                        if (itemStack != null && itemStack.type in Enumeration.shulkerBoxes) {
                            if (SimpleShulkerBox.saveInventory(itemStack, holder)) {
                                break // 保存成功提前结束循环
                            }
                        }
                    }
                }
            }

            val itemMeta = current.itemMeta!!
            val viewHolder = if (itemMeta.hasDisplayName()) {
                SimpleShulkerBox.getInventory(current, itemMeta.displayName)
            } else {
                SimpleShulkerBox.getInventory(current, title)
            }
            player.openInventory(viewHolder.inventory)
            player.playSound(player.location, SimpleShulkerBox.soundOpen, 1.0f, 1.0f)

            Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
                player.updateInventory()
            })
        }
    }

    private fun enderChest(player: Player) {
        player.openInventory(player.enderChest)
    }
}