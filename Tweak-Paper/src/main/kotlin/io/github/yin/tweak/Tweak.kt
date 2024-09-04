package io.github.yin.tweak

import io.github.yin.tweak.command.brigadier.Literal
import io.github.yin.tweak.listener.*
import io.github.yin.tweak.support.MessageReplace
import org.bukkit.craftbukkit.CraftServer
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

        registerCommand()
    }

    override fun onDisable() {
        server.consoleSender.sendMessage(MessageReplace.deserialize(pluginPrefix + "插件开始卸载 " + pluginVersion))

        unregisterCommand()
    }


    private fun registerCommand() {
        val craftServer = server as CraftServer
        val dispatcher = craftServer.handle.server.commands.dispatcher

        Literal.literal(dispatcher)
    }

    private fun unregisterCommand() {
        val commandMap = server.commandMap
        val bukkitBrigForwardingMap = commandMap.knownCommands // 这是自定义 map 不支持迭代器
        val removes: MutableList<String> = arrayListOf() // 用于记录需要删除的键

        for ((key, value) in bukkitBrigForwardingMap) {
            if (key.startsWith(lowercaseName)) { // 取消注册命令，以及别名和命名空间
                value.unregister(commandMap)
                removes.add(key)
            }
        }

        // 遍历结束后，删除记录下来的键
        for (key in removes) {
            bukkitBrigForwardingMap.remove(key) // 删除相应的键
        }
    }

}







