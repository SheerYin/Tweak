package io.github.yin.tweak.service

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.cache.InventorySlotLockCache
import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.ShulkerBox
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta

object QuickShulkerBoxService {

    val shulkerBoxes = hashSetOf(
        Material.SHULKER_BOX,
        Material.WHITE_SHULKER_BOX,
        Material.ORANGE_SHULKER_BOX,
        Material.MAGENTA_SHULKER_BOX,
        Material.LIGHT_BLUE_SHULKER_BOX,
        Material.YELLOW_SHULKER_BOX,
        Material.LIME_SHULKER_BOX,
        Material.PINK_SHULKER_BOX,
        Material.GRAY_SHULKER_BOX,
        Material.LIGHT_GRAY_SHULKER_BOX,
        Material.CYAN_SHULKER_BOX,
        Material.PURPLE_SHULKER_BOX,
        Material.BLUE_SHULKER_BOX,
        Material.BROWN_SHULKER_BOX,
        Material.GREEN_SHULKER_BOX,
        Material.RED_SHULKER_BOX,
        Material.BLACK_SHULKER_BOX,
    )

//    val shulkerBoxColors = hashMapOf(
//        Material.SHULKER_BOX to Component.translatable("container.shulker_box"),
//        Material.WHITE_SHULKER_BOX to Component.translatable("container.white_shulker_box").color(TextColor.fromHexString("#FFFFFF")), //MessageReplace.deserialize("<color:#FFFFFF>潜影盒"),
//        Material.ORANGE_SHULKER_BOX to MessageReplace.deserialize("<color:#FF7F00>潜影盒"),
//        Material.MAGENTA_SHULKER_BOX to MessageReplace.deserialize("<color:#FF00B7>潜影盒"),
//        Material.LIGHT_BLUE_SHULKER_BOX to MessageReplace.deserialize("<color:#A0D3E8>潜影盒"),
//        Material.YELLOW_SHULKER_BOX to MessageReplace.deserialize("<color:#FFFF00>潜影盒"),
//        Material.LIME_SHULKER_BOX to MessageReplace.deserialize("<color:#00FF00>潜影盒"),
//        Material.PINK_SHULKER_BOX to MessageReplace.deserialize("<color:#FFB6C1>潜影盒"),
//        Material.GRAY_SHULKER_BOX to MessageReplace.deserialize("<color:#808080>潜影盒"),
//        Material.LIGHT_GRAY_SHULKER_BOX to MessageReplace.deserialize("<color:#D3D3D3>潜影盒"),
//        Material.CYAN_SHULKER_BOX to MessageReplace.deserialize("<color:#00FFFF>潜影盒"),
//        Material.PURPLE_SHULKER_BOX to MessageReplace.deserialize("<color:#800080>潜影盒"),
//        Material.BLUE_SHULKER_BOX to MessageReplace.deserialize("<color:#0000FF>潜影盒"),
//        Material.BROWN_SHULKER_BOX to MessageReplace.deserialize("<color:#8B4513>潜影盒"),
//        Material.GREEN_SHULKER_BOX to MessageReplace.deserialize("<color:#008000>潜影盒"),
//        Material.RED_SHULKER_BOX to MessageReplace.deserialize("<color:#FF0000>潜影盒"),
//        Material.BLACK_SHULKER_BOX to MessageReplace.deserialize("<color:#000000>潜影盒")
//    )

    val shulkerBoxColors = hashMapOf(
        Material.SHULKER_BOX to Component.translatable("container.shulkerBox"), // 默认潜影盒
        Material.WHITE_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#FFFFFF")),
        Material.ORANGE_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#FF7F00")),
        Material.MAGENTA_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#FF00B7")),
        Material.LIGHT_BLUE_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#A0D3E8")),
        Material.YELLOW_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#FFFF00")),
        Material.LIME_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#00FF00")),
        Material.PINK_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#FFB6C1")),
        Material.GRAY_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#808080")),
        Material.LIGHT_GRAY_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#D3D3D3")),
        Material.CYAN_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#00FFFF")),
        Material.PURPLE_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#800080")),
        Material.BLUE_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#0000FF")),
        Material.BROWN_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#8B4513")),
        Material.GREEN_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#008000")),
        Material.RED_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#FF0000")),
        Material.BLACK_SHULKER_BOX to Component.translatable("container.shulkerBox")
            .color(TextColor.fromHexString("#000000"))
    )

    private val inventoryType = InventoryType.SHULKER_BOX

    private fun load(itemStack: ItemStack, title: Component): QuickShulkerBoxHolder {
        val itemMeta = itemStack.itemMeta

        val shulkerBox = ((itemMeta as BlockStateMeta).blockState) as ShulkerBox
        val inventory = shulkerBox.inventory

        val holder = QuickShulkerBoxHolder()
        holder.top = Bukkit.createInventory(holder, inventoryType, title).apply { contents = inventory.contents }

        return holder
    }

    private fun save(holder: QuickShulkerBoxHolder, itemStack: ItemStack) {
        val itemMeta = itemStack.itemMeta

        val blockStateMeta = itemMeta as BlockStateMeta
        val shulkerBox = blockStateMeta.blockState as ShulkerBox
        shulkerBox.inventory.contents = holder.inventory.contents
        blockStateMeta.blockState = shulkerBox

        itemStack.itemMeta = blockStateMeta

        holder.save = true
    }

    private const val cooldown = 5

    private val openSound = Sound.sound(Key.key("minecraft:block.shulker_box.open"), Sound.Source.BLOCK, 1.0f, 1.0f)

    fun inventoryOpen(current: ItemStack, title: Component, slot: Int, player: Player) {
        val currentCooldown = player.getCooldown(current.type)
        if (currentCooldown > 0) {
            return
        }

        val playerName = player.name
        InventorySlotLockCache.map[playerName] = slot

        val displayName = current.itemMeta.displayName()
        val quickShulkerBoxHolder = if (displayName == null) {
            load(current, title)
        } else {
            load(current, displayName)
        }
        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            player.openInventory(quickShulkerBoxHolder.inventory)
            player.setCooldown(current.type, cooldown)
            player.playSound(openSound)
        })
    }

    fun holderOpen(inventoryView: InventoryView, holder: QuickShulkerBoxHolder, current: ItemStack, title: Component, slot: Int, player: Player) {
        val currentCooldown = player.getCooldown(current.type)
        if (currentCooldown > 0) {
            return
        }

        val playerName = player.name
        val index = InventorySlotLockCache.map.put(playerName, slot) ?: run { Bukkit.broadcast(Tweak.getPrefixComponent().append(Component.text(" 发生错误")).build()); return }
        if (slot == index) {
            return
        } else {
            val itemStack = inventoryView.bottomInventory.getItem(index) ?: run { Bukkit.broadcast(Tweak.getPrefixComponent().append(Component.text(" 发生错误")).build()); return }
            save(holder, itemStack)
        }

        val displayName = current.itemMeta.displayName()
        val quickShulkerBoxHolder = if (displayName == null) {
            load(current, title)
        } else {
            load(current, displayName)
        }
        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            player.openInventory(quickShulkerBoxHolder.inventory)
            player.setCooldown(current.type, cooldown)
            player.playSound(openSound)
        })
    }

    fun close(inventoryView: InventoryView, holder: QuickShulkerBoxHolder) {
        if (holder.save) {
            return
        } else {
            val player = inventoryView.player
            val playerName = player.name
            val index = InventorySlotLockCache.map.remove(playerName) ?: run { Bukkit.broadcast(Tweak.getPrefixComponent().append(Component.text(" 发生错误")).build()); return }
            val itemStack = inventoryView.bottomInventory.getItem(index) ?: run { Bukkit.broadcast(Tweak.getPrefixComponent().append(Component.text(" 发生错误")).build()); return }
            save(holder, itemStack)
        }


//        val player = inventoryView.player as Player
//        val playerName = player.name
//        if (InventoryStateCache.silence[playerName] != true) {
//            player.playSound(player.location, closeSound, 1F, 1F)
//        }
//        InventoryStateCache.silence[playerName] = false
//
//        if (holder.save) {
//            return
//        } else {
//            val itemStack = inventoryView.bottomInventory.getItem(holder.index)
//            if (itemStack == null) {
//                Bukkit.broadcast(Component.text(" 保存时 bottomInventory 找不到索引所指物品，保存失败。造成物品欺诈漏洞"))
//                return
//            }
//            save(holder, itemStack)
//        }
    }


}


//fun open(inventoryView: InventoryView, topInventory: Inventory, current: ItemStack, title: Component, slot: Int) {
//    val player = inventoryView.player
//    val playerName = player.name
//    val currentCooldown = player.getCooldown(current.type)
//
//    if (currentCooldown > 0) {
//        return
//    }
//
//    val holder = topInventory.holder
//    if (holder is QuickShulkerBoxHolder) {
//        if (slot == holder.index) {
//            return
//        } else {
//            val itemStack = inventoryView.bottomInventory.getItem(holder.index)
//            if (itemStack == null) {
//                Bukkit.broadcast(Component.text(" 保存时 bottomInventory 找不到索引所指物品，保存失败。造成物品欺诈漏洞"))
//                return
//            }
//            save(holder, itemStack)
//        }
//    }
//
//    if (topInventory.type != InventoryType.CRAFTING) {
//        InventoryStateCache.silence[playerName] = true
//    }
//
//    val itemMeta = current.itemMeta
//    val quickShulkerBoxHolder = if (itemMeta.hasDisplayName()) {
//        load(current, itemMeta.displayName()!!, slot)
//    } else {
//        load(current, title, slot)
//    }
//
//    Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
//        player.openInventory(quickShulkerBoxHolder.inventory)
//        player.playSound(player.location, openSound, 1F, 1F)
//        player.setCooldown(current.type, cooldown)
//    })
//}
