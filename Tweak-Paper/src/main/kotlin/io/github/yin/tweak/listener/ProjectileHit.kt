package io.github.yin.tweak.listener

import io.github.yin.tweak.service.CaptureService
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

object ProjectileHit : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onProjectileHit(event: ProjectileHitEvent) {
        val projectile = event.entity
        val hit = event.hitEntity ?: return

        val livingEntity = hit as? LivingEntity ?: return

        // 如果有自定义名称
        if (livingEntity.customName() != null) {
            return
        }

        if (projectile is Snowball) {
            CaptureService.capture(livingEntity)
        }
    }

}