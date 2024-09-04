package io.github.yin.tweak.listener

import io.github.yin.tweak.common.Enumeration
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Projectile
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import kotlin.random.Random

object ProjectileHit : Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onProjectileHit(event: ProjectileHitEvent) {
        val projectile = event.entity
        val hit = event.hitEntity ?: return
        capture(projectile, hit)
    }

    private val sound = Sound.ENTITY_ITEM_PICKUP
    private fun capture(projectile: Projectile, hit: Entity) {
        if (projectile is Snowball && Random.nextDouble() < 0.1) {
            val livingEntity = hit as? LivingEntity ?: return
            // 如果有自定义名称始终显示，那么表示这是自定义怪物
            if (livingEntity.customName() != null || livingEntity.isCustomNameVisible) {
                return
            }
            Enumeration.entityEggs[livingEntity.type]?.let { itemStack ->
                val location = livingEntity.location
                val world = location.world!!
                world.dropItemNaturally(location, itemStack)
                world.playSound(location, sound, 1.0f, 1.0f)
                livingEntity.remove()
            }
        }
    }


}