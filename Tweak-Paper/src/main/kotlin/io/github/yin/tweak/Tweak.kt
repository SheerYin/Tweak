package io.github.yin.tweak

import io.github.yin.tweak.listener.*
import io.github.yin.tweak.support.MessageReplace
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
        pluginName = pluginMeta.name
        lowercaseName = pluginName.lowercase()
        pluginVersion = pluginMeta.version
        pluginAuthors = pluginMeta.authors
        pluginPrefix = "<white>[<green>${pluginMeta.loggerPrefix}<white>] "

        server.consoleSender.sendMessage(MessageReplace.deserialize(pluginPrefix + "插件开始加载 " + pluginVersion))

        server.pluginManager.registerEvents(EntityResurrect, this)
        server.pluginManager.registerEvents(InventoryClick, this)
        server.pluginManager.registerEvents(InventoryClose, this)
        server.pluginManager.registerEvents(PlayerDeath, this)
        server.pluginManager.registerEvents(PlayerDropItem, this)
        server.pluginManager.registerEvents(PlayerInteract, this)
        server.pluginManager.registerEvents(ProjectileHit, this)

    }

    override fun onDisable() {
        server.consoleSender.sendMessage(MessageReplace.deserialize(pluginPrefix + "插件开始卸载 " + pluginVersion))
    }


}







