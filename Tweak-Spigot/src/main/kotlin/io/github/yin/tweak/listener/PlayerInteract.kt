package io.github.yin.tweak.listener

import io.github.yin.tweak.common.Enumeration
import io.github.yin.tweak.service.SimpleShulkerBox
import org.bukkit.Material
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

                val player = event.player
                val itemStack = event.item ?: return

                handleInteract(itemStack, player)
            }
        }
    }

    private fun handleInteract(itemStack: ItemStack, player: Player) {
        val title = Enumeration.shulkerBoxColors[itemStack.type]
        if (title == null) {
            if (itemStack.type == Material.ENDER_CHEST) {
                enderChest(player)
            }
        } else {
            shulker(itemStack, player, title)
        }
    }

    private fun shulker(itemStack: ItemStack, player: Player, title: String) {
        if (itemStack.amount == 1) {
            val itemMeta = itemStack.itemMeta!!
            val viewHolder = if (itemMeta.hasDisplayName()) {
                SimpleShulkerBox.getInventory(itemStack, itemMeta.displayName)
            } else {
                SimpleShulkerBox.getInventory(itemStack, title)
            }
            player.openInventory(viewHolder.inventory)
            player.playSound(player.location, SimpleShulkerBox.soundOpen, 1.0f, 1.0f)
        }
    }

    private fun enderChest(player: Player) {
        player.openInventory(player.enderChest)
    }


}