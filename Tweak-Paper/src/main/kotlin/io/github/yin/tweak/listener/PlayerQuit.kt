package io.github.yin.tweak.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuit : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player

//        PlayerCooldownCache.inventory.remove(player.name)
//        PlayerCooldownCache.interact.remove(player.name)
//
//        Bukkit.broadcast(Component.text("inventory: " + PlayerCooldownCache.inventory))
//        Bukkit.broadcast(Component.text("interact: " + PlayerCooldownCache.interact))
    }

}