package io.github.yin.tweak.service

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.cache.InventoryStateCache
import io.github.yin.tweak.support.MessageReplace
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

object QuickEnderChestService {

    val enderChest = Material.ENDER_CHEST

    private const val cooldown = 5

    private val openSound = Sound.BLOCK_ENDER_CHEST_OPEN
    private val closeSound = Sound.BLOCK_ENDER_CHEST_CLOSE

    fun open(topInventory: Inventory, player: Player) {
        val currentCooldown = player.getCooldown(enderChest)
        if (currentCooldown > 0) {
            return
        }

        val inventoryType = topInventory.type
        if (inventoryType == InventoryType.ENDER_CHEST) {
            return
        } else if (inventoryType != InventoryType.CRAFTING) {
            val playerName = player.name
            InventoryStateCache.silence[playerName] = true
        }

        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            val inventoryView = player.openInventory(player.enderChest)!!
            inventoryView.title = MessageReplace.componentText(Component.translatable("container.enderchest"))
            // "container.enderchest"

            player.playSound(player.location, openSound, 1F, 1F)
            player.setCooldown(enderChest, cooldown)
        })
    }

    fun interact(block: Block, player: Player) {
        if (block.type == enderChest) {
            val playerName = player.name
            InventoryStateCache.silence[playerName] = true
        }
    }

    fun close(player: Player) {
        val playerName = player.name
        if (InventoryStateCache.silence[playerName] != true) {
            player.playSound(player.location, closeSound, 1F, 1F)
        }
        InventoryStateCache.silence[playerName] = false
    }
}