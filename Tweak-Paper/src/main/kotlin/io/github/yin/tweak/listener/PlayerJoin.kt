package io.github.yin.tweak.listener

import io.github.yin.tweak.cache.InventoryStatusCache
import io.github.yin.tweak.model.InventoryStatus
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoin : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val playerUniqueId = player.uniqueId
        val playerName = player.name
        InventoryStatusCache.map[player.name] = InventoryStatus(playerUniqueId, player.name)
    }

}