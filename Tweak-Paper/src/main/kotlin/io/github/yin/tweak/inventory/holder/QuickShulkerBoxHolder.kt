package io.github.yin.tweak.inventory.holder

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.service.QuickShulkerBoxService
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.*

class QuickShulkerBoxHolder(val uuid: UUID, var save: Boolean = false) : InventoryHolder {

    lateinit var top: Inventory

    override fun getInventory(): Inventory {
        return top
    }

    fun openTop(player: Player, itemStack: ItemStack) {
        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            player.openInventory(top)
            player.playSound(player.location, QuickShulkerBoxService.openSound, 1.0F, 1.0F)
            player.setCooldown(itemStack.type, QuickShulkerBoxService.cooldown)
        })
    }

    fun closeTop(player: Player) {
        player.playSound(player.location, QuickShulkerBoxService.closeSound, 1.0F, 1.0F)
    }

}