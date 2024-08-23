package io.github.yin.tweak.listener

import io.github.yin.tweak.common.Enumeration
import io.github.yin.tweak.service.SimpleShulkerBox
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

                open(itemStack, player)
            }
        }
    }

    private fun open(itemStack: ItemStack, player: Player) {
        val title = Enumeration.shulkerBoxColors[itemStack.type]
        if (title != null) {
            if (itemStack.amount == 1) {
                val itemMeta = itemStack.itemMeta
                val viewHolder = if (itemMeta.hasDisplayName()) {
                    SimpleShulkerBox.getInventory(itemStack, itemMeta.displayName()!!)
                } else {
                    SimpleShulkerBox.getInventory(itemStack, title)
                }
                player.openInventory(viewHolder.inventory)
                player.playSound(player.location, SimpleShulkerBox.soundOpen, 1.0f, 1.0f)
            }
        }
    }


}