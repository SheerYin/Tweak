package io.github.yin.tweak.listener

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.common.Enumeration
import io.github.yin.tweak.inventory.holder.ShulkerViewHolder
import io.github.yin.tweak.service.SimpleShulkerBox
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

object PlayerInteract : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_AIR) {
            val equipmentSlot = event.hand
            if (equipmentSlot == EquipmentSlot.HAND) {

                val itemStack = event.item ?: return
                val player = event.player

                handleInteract(player, itemStack)
            }
        }
    }

    private fun handleInteract(player: Player, itemStack: ItemStack) {
        val material = itemStack.type
        val cooldown = player.getCooldown(material)
        if (cooldown > 0) {
            return
        }
        val title = Enumeration.shulkerBoxColors[material]
        if (title != null) {
            if (itemStack.amount == 1) {
                shulkerOpen(player, itemStack, title)
            }
        }
    }

    private fun shulkerOpen(player: Player, itemStack: ItemStack, title: Component) {
        val inventoryView = player.openInventory
        val holder = inventoryView.topInventory.holder
        if (holder is ShulkerViewHolder) {
            if (SimpleShulkerBox.check(itemStack, holder)) {
                return
            } else {
                val cursor = inventoryView.cursor
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
        val itemMeta = itemStack.itemMeta
        val shulkerViewHolder = if (itemMeta.hasDisplayName()) {
            SimpleShulkerBox.getInventory(itemStack, itemMeta.displayName()!!)
        } else {
            SimpleShulkerBox.getInventory(itemStack, title)
        }
        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            player.openInventory(shulkerViewHolder.inventory)
            player.playSound(player.location, SimpleShulkerBox.soundOpen, 1.0f, 1.0f)
            player.setCooldown(itemStack.type, 5)
        })
    }


}