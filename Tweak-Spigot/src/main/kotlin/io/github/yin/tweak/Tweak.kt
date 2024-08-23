package io.github.yin.tweak

import io.github.yin.tweak.listener.*
import org.bukkit.plugin.java.JavaPlugin

class Tweak : JavaPlugin() {

    companion object {
        lateinit var instance: Tweak

        lateinit var pluginName: String
        lateinit var lowercaseName: String
        lateinit var pluginVersion: String
        lateinit var pluginAuthors: List<String>
        lateinit var pluginPrefix: String
    }

    override fun onEnable() {
        instance = this
        pluginName = description.name
        lowercaseName = pluginName.lowercase()
        pluginVersion = description.version
        pluginAuthors = description.authors
        pluginPrefix = "§f[§a${description.prefix}§f] "

        server.consoleSender.sendMessage(pluginPrefix + "插件开始加载 " + pluginVersion)

        server.pluginManager.registerEvents(InventoryClick, this)
        server.pluginManager.registerEvents(InventoryClose, this)
        server.pluginManager.registerEvents(PlayerDeath, this)
        server.pluginManager.registerEvents(PlayerDropItem, this)
        server.pluginManager.registerEvents(PlayerInteract, this)
    }

    override fun onDisable() {
        server.consoleSender.sendMessage(pluginPrefix + "插件开始卸载 " + pluginVersion)
    }
}