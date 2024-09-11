package io.github.yin.tweak.command.brigadier.dynamic

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.yin.tweak.Tweak
import io.github.yin.tweak.support.MessageReplace
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

object ExperienceBook {

    val experienceKey = NamespacedKey.minecraft("experience")

    fun dynamic(permission: String): LiteralArgumentBuilder<CommandSourceStack> {

        return Commands.literal("experiencebook")
            .requires { stack -> val sender = stack.sender; return@requires sender !is Player || sender.hasPermission("${permission}.experiencebook") }
            .then(Commands.literal("deposit")
                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                    .suggests { context, builder ->
                        val sender = context.source.sender
                        val self = sender as? Player ?: return@suggests builder.buildFuture()

                        val other = builder.remainingLowerCase
                        val experience = self.totalExperience.toString()
                        if (experience.lowercase().startsWith(other)) {
                            builder.suggest(experience)
                        }
                        return@suggests builder.buildFuture()
                    }
                    .executes { context ->
                        val sender = context.source.sender
                        val self = sender as? Player
                        if (self == null) {
                            sender.sendMessage(MessageReplace.deserialize("${Tweak.pluginPrefix} 此命令仅限玩家执行"))
                            return@executes 1
                        }

                        val itemStack = self.inventory.itemInMainHand
                        if (itemStack.type == Material.BOOK && itemStack.amount == 1) {
                            val itemMeta = itemStack.itemMeta
                            val experience = context.getArgument("amount", Integer::class.java).toInt()

                            val newExperience = self.totalExperience - experience
                            if (newExperience >= 0) {
                                self.exp = 0F
                                self.level = 0
                                self.totalExperience = 0

                                self.giveExp(newExperience)

                                val itemExperience = itemMeta.persistentDataContainer.get(experienceKey, PersistentDataType.INTEGER) ?: 0
                                val result = itemExperience + experience

                                itemMeta.persistentDataContainer.set(experienceKey, PersistentDataType.INTEGER, result)
                                itemMeta.lore(listOf(Component.text("已储存 $result 经验").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)))
                                itemStack.itemMeta = itemMeta

                                self.sendMessage(MessageReplace.deserialize("${Tweak.pluginPrefix} 存入 $experience 经验到经验书"))
                            } else {
                                self.sendMessage(MessageReplace.deserialize("${Tweak.pluginPrefix} 你没有足够的经验"))
                            }
                        } else {
                            self.sendMessage(MessageReplace.deserialize("${Tweak.pluginPrefix} 手上不是书，或书太多"))
                        }

                        return@executes 1
                    }
                )
            )
            .then(Commands.literal("withdraw")
                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                    .executes { context ->
                        val sender = context.source.sender
                        val self = sender as? Player
                        if (self == null) {
                            sender.sendMessage(MessageReplace.deserialize("${Tweak.pluginPrefix} 此命令仅限玩家执行"))
                            return@executes 1
                        }

                        val itemStack = self.inventory.itemInMainHand
                        if (itemStack.type == Material.BOOK && itemStack.amount == 1) {
                            val itemMeta = itemStack.itemMeta
                            val itemExperience = itemMeta.persistentDataContainer.get(experienceKey, PersistentDataType.INTEGER)

                            if (itemExperience == null) {
                                self.sendMessage(MessageReplace.deserialize("${Tweak.pluginPrefix} 这本书没有存入经验"))
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
                                        itemMeta.lore(listOf(Component.text("已储存 $result 经验").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)))
                                        itemStack.itemMeta = itemMeta
                                    }

                                    self.sendMessage(MessageReplace.deserialize("${Tweak.pluginPrefix} 从经验书取出 $experience 经验"))
                                } else {
                                    self.sendMessage(MessageReplace.deserialize("${Tweak.pluginPrefix} 这本书取不出这么多经验 $experience"))
                                }
                            }
                        } else {
                            self.sendMessage(MessageReplace.deserialize("${Tweak.pluginPrefix} 手上不是书，或书太多"))
                        }

                        return@executes 1
                    }
                )
            )
    }




}