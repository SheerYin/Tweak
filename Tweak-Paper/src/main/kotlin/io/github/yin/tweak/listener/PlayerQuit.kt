package io.github.yin.tweak.listener

import io.github.yin.tweak.cache.PlayerCooldownCache
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player

        PlayerCooldownCache.inventory.remove(player.name)
        PlayerCooldownCache.interact.remove(player.name)

        Bukkit.broadcast(Component.text("inventory: " + PlayerCooldownCache.inventory))
        Bukkit.broadcast(Component.text("interact: " + PlayerCooldownCache.interact))
    }

}