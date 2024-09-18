package io.github.yin.tweak.listener

import io.github.yin.tweak.service.CaptureService
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

object ProjectileHit : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onProjectileHit(event: ProjectileHitEvent) {
        val projectile = event.entity
        val hit = event.hitEntity ?: return
        CaptureService.capture(projectile, hit)
    }

}