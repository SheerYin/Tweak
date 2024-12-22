package io.github.yin.tweak.command.brigadier.dynamic

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.yin.tweak.Tweak
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

object ExperienceBook {

    private val experienceKey = NamespacedKey.minecraft("experience")

    private val depositSound = Sound.sound(Key.key("minecraft:block.anvil.use"), Sound.Source.BLOCK, 1.0f, 1.0f)
    private val withdrawSound = Sound.sound(Key.key("minecraft:entity.player.levelup"), Sound.Source.PLAYER, 1.0f, 1.0f)

    fun dynamic(permission: String): LiteralArgumentBuilder<CommandSourceStack> {

        val mainParameter = "experiencebook"

        return Commands.literal(mainParameter)
            .requires { stack -> val sender = stack.sender; return@requires sender !is Player || sender.hasPermission("${permission}.${mainParameter}") }
            .then(
                Commands.literal("deposit")
                .then(
                    Commands.argument("amount", IntegerArgumentType.integer(1))
                        .suggests { context, builder ->
                            val sender = context.source.sender
                            val self = sender as? Player ?: return@suggests builder.buildFuture()

                            val other = builder.remainingLowerCase
                            val experience = calculateTotalExperience(self.level).toString()
                            if (experience.lowercase().startsWith(other)) {
                                builder.suggest(experience)
                            }
                            return@suggests builder.buildFuture()
                        }
                        .executes { context ->
                            val sender = context.source.sender

                            if (sender !is Player) {
                                sender.sendMessage(Tweak.getPrefixComponent().append(Component.text(" 此命令仅限玩家执行")).build())
                                return@executes 1
                            }
                            val self: Player = sender

                            val itemStack = self.inventory.itemInMainHand
                            if (itemStack.type == Material.BOOK && itemStack.amount == 1) {
                                val itemMeta = itemStack.itemMeta
                                val experience = context.getArgument("amount", Integer::class.java).toInt()

                                self.exp

                                val newExperience = calculateTotalExperience(self.level) - experience
                                if (newExperience >= 0) {
                                    self.exp = 0F
                                    self.level = 0
                                    self.totalExperience = 0

                                    self.giveExp(newExperience, false)

                                    val itemExperience = itemMeta.persistentDataContainer.get(experienceKey, PersistentDataType.INTEGER) ?: 0
                                    val result = itemExperience + experience

                                    itemMeta.persistentDataContainer.set(experienceKey, PersistentDataType.INTEGER, result)
                                    itemMeta.lore(listOf(Component.text("已储存 $result 经验", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)))
                                    itemStack.itemMeta = itemMeta


                                    self.playSound(depositSound)
                                    self.sendMessage(Tweak.getPrefixComponent().append(Component.text(" 存入 $experience 经验到经验书")).build())
                                } else {
                                    self.sendMessage(Tweak.getPrefixComponent().append(Component.text(" 你没有足够的经验")).build())
                                }
                            } else {
                                self.sendMessage(Tweak.getPrefixComponent().append(Component.text(" 手上不是书，或书太多")).build())
                            }

                            return@executes 1
                        }
                )
            )
            .then(
                Commands.literal("withdraw")
                .then(
                    Commands.argument("amount", IntegerArgumentType.integer(1))
                        .executes { context ->
                            val sender = context.source.sender
                            if (sender !is Player) {
                                sender.sendMessage(Tweak.getPrefixComponent().append(Component.text(" 此命令仅限玩家执行")).build())
                                return@executes 1
                            }
                            val self: Player = sender

                            val itemStack = self.inventory.itemInMainHand
                            if (itemStack.type == Material.BOOK && itemStack.amount == 1) {
                                val itemMeta = itemStack.itemMeta
                                val itemExperience = itemMeta.persistentDataContainer.get(experienceKey, PersistentDataType.INTEGER)

                                if (itemExperience == null) {
                                    self.sendMessage(Tweak.getPrefixComponent().append(Component.text(" 这本书没有存入经验")).build())
                                } else {
                                    val experience = context.getArgument("amount", Integer::class.java).toInt()
                                    val result = itemExperience - experience
                                    if (result >= 0) {
                                        self.giveExp(experience, false)

                                        if (result == 0) {
                                            itemMeta.persistentDataContainer.remove(experienceKey)
                                            itemMeta.lore(null)
                                            itemStack.itemMeta = itemMeta
                                        } else {
                                            itemMeta.persistentDataContainer.set(experienceKey, PersistentDataType.INTEGER, result)
                                            itemMeta.lore(listOf(Component.text("已储存 $result 经验", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)))
                                            itemStack.itemMeta = itemMeta
                                        }

                                        // self.playSound(self.location, withdrawSound, 1.0f, 1.0f)
                                        // 取出经验时会自动升级
                                        self.sendMessage(Tweak.getPrefixComponent().append(Component.text(" 从经验书取出 $experience 经验")).build())
                                    } else {
                                        self.sendMessage(Tweak.getPrefixComponent().append(Component.text(" 这本书取不出这么多经验 $experience")).build())
                                    }
                                }
                            } else {
                                self.sendMessage(Tweak.getPrefixComponent().append(Component.text(" 手上不是书，或书太多")).build())
                            }

                            return@executes 1
                        }
                )
            )
    }

    fun calculateTotalExperience(level: Int): Int {
        var totalExperience = 0
        for (index in 0 until level) {
            totalExperience += when (index) {
                in 0..15 -> 2 * index + 7
                in 16..30 -> 5 * index - 38
                else -> 9 * index - 158
            }
        }
        return totalExperience
    }

    fun calculateNextLevelExperience(level: Int): Int {
        return when (level) {
            in 0..15 -> 2 * level + 7
            in 16..30 -> 5 * level - 38
            else -> 9 * level - 158
        }
    }


}