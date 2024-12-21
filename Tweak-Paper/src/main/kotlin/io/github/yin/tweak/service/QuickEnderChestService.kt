package io.github.yin.tweak.service

import io.github.yin.tweak.Tweak
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

object QuickEnderChestService {

    val enderChest = Material.ENDER_CHEST

    private const val cooldown = 5

    private val openSound = Sound.sound(Key.key("minecraft:block.ender_chest.open"), Sound.Source.BLOCK, 1.0f, 1.0f)

    fun inventoryOpen(topInventory: Inventory, player: Player) {
        val currentCooldown = player.getCooldown(enderChest)
        if (currentCooldown > 0) {
            return
        }

        val inventoryType = topInventory.type
        if (inventoryType == InventoryType.ENDER_CHEST) {
            return
        }

        player.setCooldown(enderChest, cooldown)
        player.playSound(openSound)

        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            player.openInventory(player.enderChest)
            // inventoryView.title = MessageReplace.componentText(Component.translatable("container.enderchest"))
            // "container.enderchest"
        })
    }
}