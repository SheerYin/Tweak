package io.github.yin.tweak.cache

import java.util.concurrent.ConcurrentHashMap

object PlayerCooldownCache {

    val inventory: MutableMap<String, Long> = ConcurrentHashMap()
    fun updateInventoryCooldown(playerName: String, cooldownTime: Long, next: Int): Boolean {
        var result = false
        inventory.compute(playerName) { _, value ->
            if (value == null || value < cooldownTime) {
                result = true
                cooldownTime + next
            } else {
                value
            }
        }
        return result
    }

    val interact: MutableMap<String, Long> = ConcurrentHashMap()
    fun updateInteractCooldown(playerName: String, cooldownTime: Long, next: Int): Boolean {
        var result = false
        interact.compute(playerName) { _, value ->
            if (value == null || value < cooldownTime) {
                result = true
                cooldownTime + next
            } else {
                value
            }
        }
        return result
    }

}