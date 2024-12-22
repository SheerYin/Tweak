package io.github.yin.tweak

import io.github.yin.tweak.command.brigadier.Literal
import io.github.yin.tweak.listener.*
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Tweak : JavaPlugin() {

    companion object {
        lateinit var instance: Tweak

        lateinit var pluginName: String
        lateinit var lowercaseName: String
        lateinit var pluginVersion: String
        lateinit var pluginAuthors: List<String>
        lateinit var pluginPrefix: String

        fun getPrefixComponent(): TextComponent.Builder {
            return Component.text()
                .color(NamedTextColor.WHITE)
                .append(Component.text("["))
                .append(Component.text(pluginPrefix, NamedTextColor.GREEN))
                .append(Component.text("]"))
        }
    }

    override fun onEnable() {
        instance = this
        pluginName = pluginMeta.name
        lowercaseName = pluginName.lowercase()
        pluginVersion = pluginMeta.version
        pluginAuthors = pluginMeta.authors
        pluginPrefix = pluginMeta.loggerPrefix!!

        server.consoleSender.sendMessage(getPrefixComponent().append(Component.text(" 插件开始加载 $pluginVersion")).build())

        server.pluginManager.registerEvents(EntityResurrect, this)
        server.pluginManager.registerEvents(InventoryClick, this)
        server.pluginManager.registerEvents(InventoryClose, this)
        server.pluginManager.registerEvents(PlayerDeath, this)
        server.pluginManager.registerEvents(PlayerInteract, this)
        server.pluginManager.registerEvents(PlayerQuit, this)
        server.pluginManager.registerEvents(ProjectileHit, this)

        registerCommand()
    }

    override fun onDisable() {
        server.consoleSender.sendMessage(getPrefixComponent().append(Component.text(" 插件开始卸载 $pluginVersion")).build())
    }

    private fun registerCommand() {
        val manager = lifecycleManager
        manager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().register(Literal.node(lowercaseName), Literal.aliases)
        }
    }

}







