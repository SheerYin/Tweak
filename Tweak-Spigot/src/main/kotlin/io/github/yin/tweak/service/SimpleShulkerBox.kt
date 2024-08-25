package io.github.yin.tweak.service

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.inventory.holder.ShulkerViewHolder
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.block.ShulkerBox
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*

object SimpleShulkerBox {
    private val namespacedKey = NamespacedKey(Tweak.instance, "shulker")

    fun getInventory(itemStack: ItemStack, title: String): ShulkerViewHolder {
        val itemMeta = itemStack.itemMeta!!
        val uuid = UUID.randomUUID()
        itemMeta.persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, uuid.toString())
        itemStack.itemMeta = itemMeta

        val shulkerBox = ((itemMeta as BlockStateMeta).blockState) as ShulkerBox
        val inventory = shulkerBox.inventory

        return ShulkerViewHolder(uuid, inventory, title)
    }

    fun saveInventory(itemStack: ItemStack, holder: ShulkerViewHolder): Boolean {
        val itemMeta = itemStack.itemMeta!!

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

    fun check(itemStack: ItemStack, holder: ShulkerViewHolder): Boolean {
        val itemMeta = itemStack.itemMeta!!
        val target = itemMeta.persistentDataContainer.get(namespacedKey, PersistentDataType.STRING) ?: return false
        return target == holder.uuid.toString()
    }

    val soundOpen = Sound.BLOCK_SHULKER_BOX_OPEN
    val soundClose = Sound.BLOCK_SHULKER_BOX_CLOSE

}