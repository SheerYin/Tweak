package io.github.yin.tweak.command

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

object LiteCommand {

    private const val mainCommand = "tweak"
    val alias = emptyList<String>()
    private const val mainPermission = "$mainCommand.command"

    fun node(): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal(mainCommand)
            .requires { stack ->
                val sender = stack.sender; return@requires sender !is Player || sender.hasPermission(
                mainPermission
            )
            }
            .then(Commands.argument("parameter", StringArgumentType.greedyString())
                .suggests { context, builder ->
                    val sender = context.source.sender
                    val remaining = builder.remaining
                    val arguments = builder.remainingLowerCase.split(" ")
                    val suggestionsBuilder = builder.createOffset(builder.start + remaining.lastIndexOf(" ") + 1)

                    return@suggests suggest(context, sender, remaining, arguments, suggestionsBuilder).buildFuture()
                }
                .executes { context ->
                    val sender = context.source.sender
                    execute(context, sender, context.getArgument("parameter", String::class.java))
                    return@executes 1
                }
            ).build()
    }

    private fun suggest(
        context: CommandContext<CommandSourceStack>,
        sender: CommandSender,
        remaining: String,
        arguments: List<String>,
        suggestionsBuilder: SuggestionsBuilder
    ): SuggestionsBuilder {
        when (arguments.size) {
            1 -> {
                for (element in listOf("test", "show")) {
                    if (element.contains(arguments[0])) {
                        suggestionsBuilder.suggest(element)
                    }
                }
            }
        }
        return suggestionsBuilder
    }

    private fun execute(context: CommandContext<CommandSourceStack>, sender: CommandSender, parameter: String) {
        val arguments = parameter.split(" ")

        if (arguments.size == 2) {
            when {
                suggestion(sender, arguments[0], "test") -> {
                    val player = sender as? Player ?: return

                    val itemStack = player.inventory.itemInMainHand

                    val s = PlainTextComponentSerializer.plainText().serialize(itemStack.displayName().hoverEvent(null))
                    player.sendMessage(s)
                    player.sendMessage("")
                    player.sendMessage(itemStack.displayName().hoverEvent(null))
                    player.sendMessage("")
                    player.sendMessage(Component.translatable(itemStack))
                    player.sendMessage("")
                    val s2 = PlainTextComponentSerializer.plainText().serialize(itemStack.itemMeta.displayName()!!)
                    player.sendMessage(s2)
                    player.sendMessage("")
                    player.sendMessage(itemStack.itemMeta.displayName()!!)
                }

                suggestion(sender, arguments[0], "show") -> {
//                    val player = sender as? Player ?: return
//
//                    val target = Bukkit.getPlayerExact(arguments[1]) ?: return
//
//                    target.showEntity(Tweak.instance, player)
//                    player.sendPlainMessage("${target.name} show ${player.name}")
                }


            }
        }
    }

//    private fun prune(argument: String, suggest: String): String? {
//        if (argument.isEmpty()) {
//            return suggest
//        } else if (suggest.contains(argument)) {
//            return suggest
//        }
//        return null
//    }

    private fun suggestion(sender: CommandSender, argument: String, vararg suggest: String): Boolean {
        val lowerCaseArgument = argument.lowercase(Locale.getDefault())
        if (lowerCaseArgument in suggest) {
            return permissionMessage(sender, "$mainPermission.$lowerCaseArgument")
        }
        return false
    }

    private fun permissionMessage(sender: CommandSender, permission: String): Boolean {
        if (sender !is Player || sender.hasPermission(permission)) {
            return true
        }
        sender.sendPlainMessage("您没有 $permission 权限")
        return false
    }


}