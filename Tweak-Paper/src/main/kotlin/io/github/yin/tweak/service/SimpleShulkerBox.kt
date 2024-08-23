package io.github.yin.tweak.service

import io.github.yin.tweak.Tweak
import io.github.yin.tweak.inventory.holder.ViewHolder
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.block.ShulkerBox
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*

object SimpleShulkerBox {
    private val namespacedKey = NamespacedKey(Tweak.instance, "shulker")

    fun getInventory(itemStack: ItemStack, title: Component): ViewHolder {
        val itemMeta = itemStack.itemMeta!!
        val uuid = UUID.randomUUID()
        itemMeta.persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, uuid.toString())
        itemStack.itemMeta = itemMeta

        val shulkerBox = ((itemMeta as BlockStateMeta).blockState) as ShulkerBox
        val inventory = shulkerBox.inventory

        return ViewHolder(uuid, inventory, title)
    }

    fun saveInventory(itemStack: ItemStack, holder: ViewHolder): Boolean {
        val itemMeta = itemStack.itemMeta!!

        val target = itemMeta.persistentDataContainer.get(namespacedKey, PersistentDataType.STRING) ?: return false
        if (holder.uuid.toString() == target) {
            val blockStateMeta = itemMeta as BlockStateMeta
            val shulkerBox = blockStateMeta.blockState as ShulkerBox
            shulkerBox.inventory.contents = holder.inventory.contents
            blockStateMeta.blockState = shulkerBox

            blockStateMeta.persistentDataContainer.remove(namespacedKey)

            itemStack.itemMeta = blockStateMeta

            holder.save = false
            return true
        }
        return false
    }

    val soundOpen = Sound.BLOCK_SHULKER_BOX_OPEN
    val soundClose = Sound.BLOCK_SHULKER_BOX_CLOSE

}