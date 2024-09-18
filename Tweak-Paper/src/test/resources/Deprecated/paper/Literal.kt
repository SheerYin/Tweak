package io.github.yin.tweak.command.paper

import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.yin.tweak.command.paper.dynamic.ExperienceBook
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.entity.Player

object Literal {

    // 不使用 lowercaseName 是因为 Paper Loader 比主类先加载
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

            .then(ExperienceBook.dynamic(mainPermission))
            .build()
    }


}