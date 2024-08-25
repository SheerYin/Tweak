package io.github.yin.tweak.listener

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.common.Enumeration
import io.github.yin.tweak.inventory.holder.ShulkerViewHolder
import io.github.yin.tweak.service.SimpleShulkerBox
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

object InventoryClick : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onInventoryClick(event: InventoryClickEvent) {
        handleClick(event)
    }


    private fun handleClick(event: InventoryClickEvent) {
        val clickType = event.click
        when (clickType) {
            LEFT -> {
                shulkerCancel(event)
            }
            RIGHT -> {
                val rawSlot = event.rawSlot
                val inventoryView = event.view
                val top = inventoryView.topInventory
                val topSize = top.size
                if (rawSlot < topSize) {
                    return
                }

                val current = event.currentItem ?: return
                val title = Enumeration.shulkerBoxColors[current.type]
                if (title == null) {
                    if (current.type == Material.ENDER_CHEST) {
                        event.isCancelled = true
                        val player = inventoryView.player as Player
                        val cooldown = player.getCooldown(materialEnder)
                        if (cooldown > 0) {
                            return
                        }
                        enderChest(player)
                    }
                } else {
                    event.isCancelled = true
                    val player = inventoryView.player as Player
                    val cooldown = player.getCooldown(current.type)
                    if (cooldown > 0) {
                        return
                    }
                    if (current.amount == 1) {
                        val holder = top.holder ?: return
                        shulkerOpen(inventoryView, holder, current, title, player)
                    }
                }
            }
            DROP -> {
                shulkerCancel(event)
            }
            CONTROL_DROP -> {
                shulkerCancel(event)
            }
            else -> return
        }
    }

    fun shulkerCancel(event: InventoryClickEvent) {
        val rawSlot = event.rawSlot
        val inventoryView = event.view
        val top = inventoryView.topInventory
        if (rawSlot < top.size) {
            return
        }
        val current = event.currentItem ?: return
        if (current.type in Enumeration.shulkerBoxes) {
            val holder = top.holder ?: return
            if (holder is ShulkerViewHolder) {
                if (SimpleShulkerBox.check(current, holder)) {
                    event.isCancelled = true
                    return
                }
            }
        }
    }

    fun shulkerOpen(inventoryView: InventoryView, holder: InventoryHolder, current: ItemStack, title: String, player: Player) {
        if (holder is ShulkerViewHolder) {
            if (SimpleShulkerBox.check(current, holder)) {
                return
            } else {
                val cursor = inventoryView.cursor ?: return
                var saveSuccess = false
                if (cursor.type in Enumeration.shulkerBoxes) {
                    // 如果保存成功
                    saveSuccess = SimpleShulkerBox.saveInventory(cursor, holder)
                }
                // 保存成功后不再尝试遍历玩家 inventory
                if (!saveSuccess) {
                    for (stack in player.inventory.contents) {
                        if (stack != null && stack.type in Enumeration.shulkerBoxes) {
                            if (SimpleShulkerBox.saveInventory(stack, holder)) {
                                break // 保存成功提前结束循环
                            }
                        }
                    }
                }
            }
        }
        val itemMeta = current.itemMeta ?: return
        val shulkerViewHolder = if (itemMeta.hasDisplayName()) {
            SimpleShulkerBox.getInventory(current, itemMeta.displayName)
        } else {
            SimpleShulkerBox.getInventory(current, title)
        }
        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            player.openInventory(shulkerViewHolder.inventory)
            player.playSound(player.location, SimpleShulkerBox.soundOpen, 1.0f, 1.0f)
            player.setCooldown(current.type, 5)
        })
    }

    private val materialEnder = Material.ENDER_CHEST
    private fun enderChest(player: Player) {
        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            player.openInventory(player.enderChest)
            player.setCooldown(materialEnder, 5)
        })
    }


}