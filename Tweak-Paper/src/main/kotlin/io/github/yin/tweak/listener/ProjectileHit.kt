package io.github.yin.tweak.listener

import io.github.yin.tweak.service.CaptureService
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

object ProjectileHit : Listener {

    @EventHandler
    fun onProjectileHit(event: ProjectileHitEvent) {
        val projectile = event.entity
        val player = projectile.shooter as? Player ?: return

        val hit = event.hitEntity ?: return

        val livingEntity = hit as? LivingEntity ?: return

        // 如果有自定义名称
        if (livingEntity.customName() != null) {
            return
        }

        if (projectile is Snowball) {
            if (!player.hasPermission("tweak.capture")) {
                return
            }
            CaptureService.capture(player, livingEntity)
        }
    }

}