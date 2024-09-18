package io.github.yin.tweak.service

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.cache.InventoryStateCache
import io.github.yin.tweak.inventory.holder.QuickShulkerBoxHolder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.block.ShulkerBox
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*

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

    private val namespacedKey = NamespacedKey(Tweak.instance, "shulker")
    private val inventoryType = InventoryType.SHULKER_BOX

    private fun load(itemStack: ItemStack, title: Component): QuickShulkerBoxHolder {
        val itemMeta = itemStack.itemMeta
        val uuid = UUID.randomUUID()
        itemMeta.persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, uuid.toString())
        itemStack.itemMeta = itemMeta

        val shulkerBox = ((itemMeta as BlockStateMeta).blockState) as ShulkerBox
        val inventory = shulkerBox.inventory

        val holder = QuickShulkerBoxHolder(uuid)
        holder.top = Bukkit.createInventory(holder, inventoryType, title).apply { contents = inventory.contents }

        return holder
    }

    private fun save(itemStack: ItemStack, holder: QuickShulkerBoxHolder): Boolean {
        val itemMeta = itemStack.itemMeta

        val target = itemMeta.persistentDataContainer.get(namespacedKey, PersistentDataType.STRING) ?: return false
        if (holder.uuid.toString() == target) {
            val blockStateMeta = itemMeta as BlockStateMeta
            val shulkerBox = blockStateMeta.blockState as ShulkerBox
            shulkerBox.inventory.contents = holder.inventory.contents
            blockStateMeta.blockState = shulkerBox

            blockStateMeta.persistentDataContainer.remove(namespacedKey)

            itemStack.itemMeta = blockStateMeta

            holder.save = true
            return true
        }
        return false
    }

    fun check(itemStack: ItemStack, holder: QuickShulkerBoxHolder): Boolean {
        val itemMeta = itemStack.itemMeta
        val target = itemMeta.persistentDataContainer.get(namespacedKey, PersistentDataType.STRING) ?: return false
        return target == holder.uuid.toString()
    }

    private const val cooldown = 5

    private val openSound = Sound.BLOCK_SHULKER_BOX_OPEN
    private val closeSound = Sound.BLOCK_SHULKER_BOX_CLOSE

    fun open(inventoryView: InventoryView, topInventory: Inventory, current: ItemStack, title: Component) {
        val player = inventoryView.player as Player
        val playerName = player.name
        val currentCooldown = player.getCooldown(current.type)

        if (currentCooldown > 0) {
            return
        }

        val holder = topInventory.holder
        if (holder is QuickShulkerBoxHolder) {
            if (check(current, holder)) {
                return
            } else {
                val cursor = inventoryView.cursor
                var saveSuccess = false
                if (cursor.type in shulkerBoxes) {
                    // 如果保存成功
                    saveSuccess = save(cursor, holder)
                }
                // 保存成功后不再尝试遍历玩家 inventory
                if (!saveSuccess) {
                    for (stack in player.inventory.contents) {
                        if (stack != null && stack.type in shulkerBoxes) {
                            if (save(stack, holder)) {
                                break // 保存成功提前结束循环
                            }
                        }
                    }
                }
            }
        }

        if (topInventory.type != InventoryType.CRAFTING) {
            InventoryStateCache.silence[playerName] = true
        }

        val itemMeta = current.itemMeta
        val quickShulkerBoxHolder = if (itemMeta.hasDisplayName()) {
            load(current, itemMeta.displayName()!!)
        } else {
            load(current, title)
        }

        Bukkit.getScheduler().runTask(Tweak.instance, Runnable {
            player.openInventory(quickShulkerBoxHolder.inventory)
            player.playSound(player.location, openSound, 1F, 1F)
            player.setCooldown(current.type, cooldown)
        })
    }

    fun close(inventoryView: InventoryView, holder: QuickShulkerBoxHolder) {
        val player = inventoryView.player as Player
        val playerName = player.name
        if (InventoryStateCache.silence[playerName] != true) {
            player.playSound(player.location, closeSound, 1F, 1F)
        }
        InventoryStateCache.silence[playerName] = false

        if (holder.save) {
            return
        } else {
            val cursor = inventoryView.cursor
            if (cursor.type in shulkerBoxes) {
                if (save(cursor, holder)) {
                    return // 保存成功提前返回
                }
            }
            for (stack in player.inventory.contents) {
                if (stack != null && stack.type in shulkerBoxes) {
                    if (save(stack, holder)) {
                        return // 保存成功提前返回
                    }
                }
            }
        }
    }


}