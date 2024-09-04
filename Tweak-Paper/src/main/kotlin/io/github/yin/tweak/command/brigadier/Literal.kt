package io.github.yin.tweak.command.brigadier

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.yin.tweak.Tweak
import io.github.yin.tweak.command.brigadier.dynamic.ExperienceBook
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import org.bukkit.entity.Player

object Literal {

    fun literal(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val name = Tweak.lowercaseName
        val permission = "$name.command"
        val aliases = emptyList<String>()

        val builder = Commands.literal(name)
            .requires { stack -> val sender = stack.sender; return@requires sender !is Player || sender.hasPermission(permission) }

            .then(ExperienceBook.dynamic(permission))



        register(dispatcher, name, aliases, builder)
    }

    private fun register(dispatcher: CommandDispatcher<CommandSourceStack>, name: String, aliases: List<String>, builder: LiteralArgumentBuilder<CommandSourceStack>) {
        // 注册命令
        val node = dispatcher.register(builder)

        // 注册命名空间
        val namespace = "${name}:"
        dispatcher.register(Commands.literal(namespace + name).redirect(node))

        // 注册别名
        for (alias in aliases) {
            dispatcher.register(Commands.literal(namespace + alias).redirect(node))
        }
    }

}