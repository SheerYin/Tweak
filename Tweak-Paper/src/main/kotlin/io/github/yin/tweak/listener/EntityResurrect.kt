package io.github.yin.tweak.listener

import io.github.yin.tweak.service.TotemService
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityResurrectEvent

object EntityResurrect : Listener {

    @EventHandler
    fun onEntityResurrect(event: EntityResurrectEvent) {
        val entity = event.entity
        if (entity is Player) {

            if (entity.hasPermission("tweak.totem.cooldown.bypass")) {
                return
            }

            val inventory = entity.inventory
            val hand = inventory.itemInMainHand
            val offHand = inventory.itemInOffHand

            TotemService.handleTotem(event, entity, hand, offHand)
        }
    }

}