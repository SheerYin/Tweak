package io.github.yin.tweak.listener

import io.github.yin.tweak.controller.PlayerInteractController
import io.github.yin.tweak.service.QuickEnderChest
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object PlayerInteract : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val action = event.action
        if (action == Action.RIGHT_CLICK_AIR) {
            val equipmentSlot = event.hand
            if (equipmentSlot == EquipmentSlot.HAND) {

                val itemStack = event.item ?: return
                val player = event.player
                PlayerInteractController.handleInteract(itemStack, player)
            }
        } else if (action == Action.RIGHT_CLICK_BLOCK) {
            val equipmentSlot = event.hand
            if (equipmentSlot == EquipmentSlot.HAND) {

                val block = event.clickedBlock ?: return
                val player = event.player
                QuickEnderChest.interact(block, player)
            }
        }
    }

}