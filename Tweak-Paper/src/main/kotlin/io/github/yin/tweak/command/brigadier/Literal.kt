package io.github.yin.tweak.command.brigadier

import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.yin.tweak.command.brigadier.dynamic.ExperienceBook
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.entity.Player

object Literal {

    val aliases: List<String> = emptyList()
    fun node(name: String): LiteralCommandNode<CommandSourceStack> {
        val permission = "$name.command"

        return Commands.literal(name)
            .requires { stack -> val sender = stack.sender; return@requires sender !is Player || sender.hasPermission(permission) }

            .then(ExperienceBook.dynamic(permission))

            .build()
    }


}