package io.github.yin.tweak.inventory.holder

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.util.*

class ShulkerViewHolder(val uuid: UUID, shulker: Inventory, title: String, var save: Boolean = false) :
    InventoryHolder {

    private val view = Bukkit.createInventory(this, InventoryType.SHULKER_BOX, title).apply {
        contents = shulker.contents
    }

    override fun getInventory(): Inventory {
        return view
    }

}