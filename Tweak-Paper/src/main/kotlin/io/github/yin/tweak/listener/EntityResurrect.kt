package io.github.yin.tweak.listener

import io.github.yin.tweak.Tweak
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityResurrectEvent

object EntityResurrect : Listener {

    private val totem = Material.TOTEM_OF_UNDYING

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onEntityResurrect(event: EntityResurrectEvent) {
        val entity = event.entity
        if (entity is Player) {
            val inventory = entity.inventory
            val hand = inventory.itemInMainHand
            val offHand = inventory.itemInOffHand

            if (hand.type == totem || offHand.type == totem) {
                val cooldown = entity.getCooldown(totem)
                if (cooldown > 0) {
                    event.isCancelled = true
                    return
                } else {
                    Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
                        entity.setCooldown(totem, 100)
                    })
                }
            }
        }
    }

}