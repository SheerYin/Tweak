package io.github.yin.tweak.controller

import io.github.yin.tweak.service.QuickEnderChest
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object PlayerInteractController {

    fun handleInteract(itemStack: ItemStack, player: Player) {
        val material = itemStack.type
        val cooldown = player.getCooldown(material)
        if (cooldown > 0) {
            return
        }
        val title = QuickShulkerBoxService.shulkerBoxColors[material]
        if (title == null) {
            if (material == QuickEnderChest.enderChest) {
                val inventoryView = player.openInventory
                val topInventory = inventoryView.topInventory
                QuickEnderChest.open(inventoryView, topInventory)
            }
        } else {
            if (itemStack.amount == 1) {
                val inventoryView = player.openInventory
                val topInventory = inventoryView.topInventory
                QuickShulkerBoxService.open(inventoryView, topInventory, itemStack, title)
            }
        }
    }

}