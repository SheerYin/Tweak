package io.github.yin.tweak.listener

import io.github.yin.tweak.cache.InventoryStatusCache
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        //val playerUniqueId = player.uniqueId
        val playerName = player.name
        InventoryStatusCache.map.remove(playerName)
    }

}