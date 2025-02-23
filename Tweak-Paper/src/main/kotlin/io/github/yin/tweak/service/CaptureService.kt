package io.github.yin.tweak.service

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object CaptureService {

    val entityEggs = hashMapOf(
        EntityType.ARMADILLO to ItemStack(Material.ARMADILLO_SPAWN_EGG),
        EntityType.ALLAY to ItemStack(Material.ALLAY_SPAWN_EGG),
        EntityType.AXOLOTL to ItemStack(Material.AXOLOTL_SPAWN_EGG),
        EntityType.BAT to ItemStack(Material.BAT_SPAWN_EGG),
        EntityType.BEE to ItemStack(Material.BEE_SPAWN_EGG),
        EntityType.BLAZE to ItemStack(Material.BLAZE_SPAWN_EGG),
        EntityType.BOGGED to ItemStack(Material.BOGGED_SPAWN_EGG),
        EntityType.BREEZE to ItemStack(Material.BREEZE_SPAWN_EGG),
        EntityType.CAT to ItemStack(Material.CAT_SPAWN_EGG),
        EntityType.CAMEL to ItemStack(Material.CAMEL_SPAWN_EGG),
        EntityType.CAVE_SPIDER to ItemStack(Material.CAVE_SPIDER_SPAWN_EGG),
        EntityType.CHICKEN to ItemStack(Material.CHICKEN_SPAWN_EGG),
        EntityType.COD to ItemStack(Material.COD_SPAWN_EGG),
        EntityType.COW to ItemStack(Material.COW_SPAWN_EGG),
        EntityType.CREEPER to ItemStack(Material.CREEPER_SPAWN_EGG),
        EntityType.DOLPHIN to ItemStack(Material.DOLPHIN_SPAWN_EGG),
        EntityType.DONKEY to ItemStack(Material.DONKEY_SPAWN_EGG),
        EntityType.DROWNED to ItemStack(Material.DROWNED_SPAWN_EGG),
        EntityType.ELDER_GUARDIAN to ItemStack(Material.ELDER_GUARDIAN_SPAWN_EGG),
//        EntityType.ENDER_DRAGON to ItemStack(Material.ENDER_DRAGON_SPAWN_EGG),
        EntityType.ENDERMAN to ItemStack(Material.ENDERMAN_SPAWN_EGG),
        EntityType.ENDERMITE to ItemStack(Material.ENDERMITE_SPAWN_EGG),
        EntityType.EVOKER to ItemStack(Material.EVOKER_SPAWN_EGG),
        EntityType.FOX to ItemStack(Material.FOX_SPAWN_EGG),
        EntityType.FROG to ItemStack(Material.FROG_SPAWN_EGG),
        EntityType.GHAST to ItemStack(Material.GHAST_SPAWN_EGG),
        EntityType.GLOW_SQUID to ItemStack(Material.GLOW_SQUID_SPAWN_EGG),
        EntityType.GOAT to ItemStack(Material.GOAT_SPAWN_EGG),
        EntityType.GUARDIAN to ItemStack(Material.GUARDIAN_SPAWN_EGG),
        EntityType.HOGLIN to ItemStack(Material.HOGLIN_SPAWN_EGG),
        EntityType.HORSE to ItemStack(Material.HORSE_SPAWN_EGG),
        EntityType.HUSK to ItemStack(Material.HUSK_SPAWN_EGG),
        EntityType.IRON_GOLEM to ItemStack(Material.IRON_GOLEM_SPAWN_EGG),
        EntityType.LLAMA to ItemStack(Material.LLAMA_SPAWN_EGG),
        EntityType.MAGMA_CUBE to ItemStack(Material.MAGMA_CUBE_SPAWN_EGG),
        EntityType.MOOSHROOM to ItemStack(Material.MOOSHROOM_SPAWN_EGG),
        EntityType.MULE to ItemStack(Material.MULE_SPAWN_EGG),
        EntityType.OCELOT to ItemStack(Material.OCELOT_SPAWN_EGG),
        EntityType.PANDA to ItemStack(Material.PANDA_SPAWN_EGG),
        EntityType.PARROT to ItemStack(Material.PARROT_SPAWN_EGG),
        EntityType.PHANTOM to ItemStack(Material.PHANTOM_SPAWN_EGG),
        EntityType.PIG to ItemStack(Material.PIG_SPAWN_EGG),
        EntityType.PIGLIN to ItemStack(Material.PIGLIN_SPAWN_EGG),
        EntityType.PIGLIN_BRUTE to ItemStack(Material.PIGLIN_BRUTE_SPAWN_EGG),
        EntityType.PILLAGER to ItemStack(Material.PILLAGER_SPAWN_EGG),
        EntityType.POLAR_BEAR to ItemStack(Material.POLAR_BEAR_SPAWN_EGG),
        EntityType.PUFFERFISH to ItemStack(Material.PUFFERFISH_SPAWN_EGG),
        EntityType.RABBIT to ItemStack(Material.RABBIT_SPAWN_EGG),
        EntityType.RAVAGER to ItemStack(Material.RAVAGER_SPAWN_EGG),
        EntityType.SALMON to ItemStack(Material.SALMON_SPAWN_EGG),
        EntityType.SHEEP to ItemStack(Material.SHEEP_SPAWN_EGG),
        EntityType.SHULKER to ItemStack(Material.SHULKER_SPAWN_EGG),
        EntityType.SILVERFISH to ItemStack(Material.SILVERFISH_SPAWN_EGG),
        EntityType.SKELETON to ItemStack(Material.SKELETON_SPAWN_EGG),
        EntityType.SKELETON_HORSE to ItemStack(Material.SKELETON_HORSE_SPAWN_EGG),
        EntityType.SLIME to ItemStack(Material.SLIME_SPAWN_EGG),
        EntityType.SNIFFER to ItemStack(Material.SNIFFER_SPAWN_EGG),
        EntityType.SNOW_GOLEM to ItemStack(Material.SNOW_GOLEM_SPAWN_EGG),
        EntityType.SPIDER to ItemStack(Material.SPIDER_SPAWN_EGG),
        EntityType.SQUID to ItemStack(Material.SQUID_SPAWN_EGG),
        EntityType.STRAY to ItemStack(Material.STRAY_SPAWN_EGG),
        EntityType.STRIDER to ItemStack(Material.STRIDER_SPAWN_EGG),
        EntityType.TADPOLE to ItemStack(Material.TADPOLE_SPAWN_EGG),
        EntityType.TRADER_LLAMA to ItemStack(Material.TRADER_LLAMA_SPAWN_EGG),
        EntityType.TROPICAL_FISH to ItemStack(Material.TROPICAL_FISH_SPAWN_EGG),
        EntityType.TURTLE to ItemStack(Material.TURTLE_SPAWN_EGG),
        EntityType.VEX to ItemStack(Material.VEX_SPAWN_EGG),
        EntityType.VILLAGER to ItemStack(Material.VILLAGER_SPAWN_EGG),
        EntityType.VINDICATOR to ItemStack(Material.VINDICATOR_SPAWN_EGG),
        EntityType.WANDERING_TRADER to ItemStack(Material.WANDERING_TRADER_SPAWN_EGG),
        EntityType.WARDEN to ItemStack(Material.WARDEN_SPAWN_EGG),
        EntityType.WITCH to ItemStack(Material.WITCH_SPAWN_EGG),
//        EntityType.WITHER to ItemStack(Material.WITHER_SPAWN_EGG),
        EntityType.WITHER_SKELETON to ItemStack(Material.WITHER_SKELETON_SPAWN_EGG),
        EntityType.WOLF to ItemStack(Material.WOLF_SPAWN_EGG),
        EntityType.ZOGLIN to ItemStack(Material.ZOGLIN_SPAWN_EGG),
        EntityType.ZOMBIE to ItemStack(Material.ZOMBIE_SPAWN_EGG),
        EntityType.ZOMBIE_HORSE to ItemStack(Material.ZOMBIE_HORSE_SPAWN_EGG),
        EntityType.ZOMBIE_VILLAGER to ItemStack(Material.ZOMBIE_VILLAGER_SPAWN_EGG),
        EntityType.ZOMBIFIED_PIGLIN to ItemStack(Material.ZOMBIFIED_PIGLIN_SPAWN_EGG),
    )

    val spawnEggs = hashSetOf(
        Material.ARMADILLO_SPAWN_EGG,
        Material.ALLAY_SPAWN_EGG,
        Material.AXOLOTL_SPAWN_EGG,
        Material.BAT_SPAWN_EGG,
        Material.BEE_SPAWN_EGG,
        Material.BLAZE_SPAWN_EGG,
        Material.BOGGED_SPAWN_EGG,
        Material.BREEZE_SPAWN_EGG,
        Material.CAT_SPAWN_EGG,
        Material.CAMEL_SPAWN_EGG,
        Material.CAVE_SPIDER_SPAWN_EGG,
        Material.CHICKEN_SPAWN_EGG,
        Material.COD_SPAWN_EGG,
        Material.COW_SPAWN_EGG,
        Material.CREEPER_SPAWN_EGG,
        Material.DOLPHIN_SPAWN_EGG,
        Material.DONKEY_SPAWN_EGG,
        Material.DROWNED_SPAWN_EGG,
        Material.ELDER_GUARDIAN_SPAWN_EGG,
        Material.ENDER_DRAGON_SPAWN_EGG,
        Material.ENDERMAN_SPAWN_EGG,
        Material.ENDERMITE_SPAWN_EGG,
        Material.EVOKER_SPAWN_EGG,
        Material.FOX_SPAWN_EGG,
        Material.FROG_SPAWN_EGG,
        Material.GHAST_SPAWN_EGG,
        Material.GLOW_SQUID_SPAWN_EGG,
        Material.GOAT_SPAWN_EGG,
        Material.GUARDIAN_SPAWN_EGG,
        Material.HOGLIN_SPAWN_EGG,
        Material.HORSE_SPAWN_EGG,
        Material.HUSK_SPAWN_EGG,
        Material.IRON_GOLEM_SPAWN_EGG,
        Material.LLAMA_SPAWN_EGG,
        Material.MAGMA_CUBE_SPAWN_EGG,
        Material.MOOSHROOM_SPAWN_EGG,
        Material.MULE_SPAWN_EGG,
        Material.OCELOT_SPAWN_EGG,
        Material.PANDA_SPAWN_EGG,
        Material.PARROT_SPAWN_EGG,
        Material.PHANTOM_SPAWN_EGG,
        Material.PIG_SPAWN_EGG,
        Material.PIGLIN_SPAWN_EGG,
        Material.PIGLIN_BRUTE_SPAWN_EGG,
        Material.PILLAGER_SPAWN_EGG,
        Material.POLAR_BEAR_SPAWN_EGG,
        Material.PUFFERFISH_SPAWN_EGG,
        Material.RABBIT_SPAWN_EGG,
        Material.RAVAGER_SPAWN_EGG,
        Material.SALMON_SPAWN_EGG,
        Material.SHEEP_SPAWN_EGG,
        Material.SHULKER_SPAWN_EGG,
        Material.SILVERFISH_SPAWN_EGG,
        Material.SKELETON_SPAWN_EGG,
        Material.SKELETON_HORSE_SPAWN_EGG,
        Material.SLIME_SPAWN_EGG,
        Material.SNIFFER_SPAWN_EGG,
        Material.SNOW_GOLEM_SPAWN_EGG,
        Material.SPIDER_SPAWN_EGG,
        Material.SQUID_SPAWN_EGG,
        Material.STRAY_SPAWN_EGG,
        Material.STRIDER_SPAWN_EGG,
        Material.TADPOLE_SPAWN_EGG,
        Material.TRADER_LLAMA_SPAWN_EGG,
        Material.TROPICAL_FISH_SPAWN_EGG,
        Material.TURTLE_SPAWN_EGG,
        Material.VEX_SPAWN_EGG,
        Material.VILLAGER_SPAWN_EGG,
        Material.VINDICATOR_SPAWN_EGG,
        Material.WANDERING_TRADER_SPAWN_EGG,
        Material.WARDEN_SPAWN_EGG,
        Material.WITCH_SPAWN_EGG,
        Material.WITHER_SPAWN_EGG,
        Material.WITHER_SKELETON_SPAWN_EGG,
        Material.WOLF_SPAWN_EGG,
        Material.ZOGLIN_SPAWN_EGG,
        Material.ZOMBIE_SPAWN_EGG,
        Material.ZOMBIE_HORSE_SPAWN_EGG,
        Material.ZOMBIE_VILLAGER_SPAWN_EGG,
        Material.ZOMBIFIED_PIGLIN_SPAWN_EGG
    )

    private val sound = Sound.ENTITY_ITEM_PICKUP

    fun capture(player: Player, livingEntity: LivingEntity) {
        val entityType = livingEntity.type
        if (!player.hasPermission("tweak.capture." + entityType.name.lowercase())) {
            return
        }

        if (Random.nextDouble() < 0.1) {
            entityEggs[entityType]?.let { itemStack ->
                val location = livingEntity.location
                val world = location.world!!
                world.dropItemNaturally(location, itemStack)
                world.playSound(location, sound, 1.0f, 1.0f)
                livingEntity.remove()
            }
        }
    }

}