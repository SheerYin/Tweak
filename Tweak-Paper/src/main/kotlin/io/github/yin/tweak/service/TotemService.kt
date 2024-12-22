package io.github.yin.tweak.service

import io.github.yin.tweak.Tweak
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.inventory.ItemStack

object TotemService {

    private val totem = Material.TOTEM_OF_UNDYING
    private const val cooldown = 100

    fun handleTotem(event: EntityResurrectEvent, player: Player, hand: ItemStack, offHand: ItemStack) {
        if (hand.type == totem || offHand.type == totem) {
            val currentCooldown = player.getCooldown(totem)
            if (currentCooldown > 0) {
                event.isCancelled = true
                return
            } else {
                Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
                    player.setCooldown(totem, cooldown)
                })
            }
        }
    }
}