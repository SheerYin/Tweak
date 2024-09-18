package io.github.yin.tweak.service

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.cache.InventoryStatusCache
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView

object QuickEnderChest {

    val enderChest = Material.ENDER_CHEST
    val cooldown = 5

    val openSound = Sound.BLOCK_ENDER_CHEST_OPEN
    val closeSound = Sound.BLOCK_ENDER_CHEST_CLOSE

    fun open(inventoryView: InventoryView, topInventory: Inventory) {
        val player = inventoryView.player as Player
        val currentCooldown = player.getCooldown(enderChest)
        if (currentCooldown > 0) {
            return
        }
        if (topInventory.type == InventoryType.ENDER_CHEST) {
            return
        }
        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            player.openInventory(player.enderChest)
            // "container.enderchest"
            player.playSound(player.location, openSound, 1F, 1F)
            player.setCooldown(enderChest, cooldown)
        })
    }

    fun interact(block: Block, player: Player) {
        if (block.type == enderChest) {
            val playerName = player.name
            val inventoryStatus = InventoryStatusCache.map[playerName] ?: return
            inventoryStatus.enderChest = true
        }
    }

    fun close(player: Player) {
        val playerName = player.name
        val inventoryStatus = InventoryStatusCache.map[playerName] ?: return
        if (inventoryStatus.enderChest) {
            inventoryStatus.enderChest = false
        } else {
            player.playSound(player.location, closeSound, 1F, 1F)
        }
    }
}