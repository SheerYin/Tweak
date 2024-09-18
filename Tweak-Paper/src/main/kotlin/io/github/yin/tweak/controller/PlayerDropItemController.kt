package io.github.yin.tweak.controller

import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.inventory.ItemStack

object PlayerDropItemController {

    fun handleDrop(event: PlayerDropItemEvent, holder: QuickShulkerBoxHolder, itemStack: ItemStack) {
        if (QuickShulkerBoxService.check(itemStack, holder)) {
            event.isCancelled = true
            return
        }
    }

}