package io.github.yin.tweak.listener

import io.github.yin.tweak.service.TotemService
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityResurrectEvent

object EntityResurrect : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onEntityResurrect(event: EntityResurrectEvent) {
        val entity = event.entity
        if (entity is Player) {
            val inventory = entity.inventory
            val hand = inventory.itemInMainHand
            val offHand = inventory.itemInOffHand

            TotemService.handleTotem(event, entity, hand, offHand)
        }
    }

}