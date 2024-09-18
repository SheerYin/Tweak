package io.github.yin.tweak.controller

import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.inventory.InventoryView

object PlayerDeathController {

    fun handleDeath(inventoryView: InventoryView, holder: QuickShulkerBoxHolder) {
        if (holder.save) {
            return
        } else {
            QuickShulkerBoxService.close(inventoryView, holder)
        }
    }

}