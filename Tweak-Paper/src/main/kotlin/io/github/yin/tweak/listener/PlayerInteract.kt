package io.github.yin.tweak.listener

import io.github.yin.tweak.service.QuickEnderChestService
import io.github.yin.tweak.service.QuickShulkerBoxService
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
        val action = event.action
        if (action == Action.RIGHT_CLICK_AIR) {
            val equipmentSlot = event.hand
            if (equipmentSlot == EquipmentSlot.HAND) {

                val itemStack = event.item ?: return
                val player = event.player
                interactRightAir(itemStack, player)
            }
        }
    }

    private fun interactRightAir(itemStack: ItemStack, player: Player) {
        val material = itemStack.type
        val cooldown = player.getCooldown(material)
        if (cooldown > 0) {
            return
        }
        val title = QuickShulkerBoxService.shulkerBoxColors[material]
        if (title == null) {
            if (material == QuickEnderChestService.enderChest) {
                val inventoryView = player.openInventory
                val topInventory = inventoryView.topInventory
                QuickEnderChestService.inventoryOpen(topInventory, player)
            }
        } else {
            if (itemStack.amount == 1) {
                val slot = player.inventory.heldItemSlot
                QuickShulkerBoxService.inventoryOpen(itemStack, title, slot, player)
            }
        }
    }


}