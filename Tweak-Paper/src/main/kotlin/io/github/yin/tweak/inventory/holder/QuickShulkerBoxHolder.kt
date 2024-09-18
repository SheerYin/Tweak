package io.github.yin.tweak.inventory.holder

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.util.*

class QuickShulkerBoxHolder(val uuid: UUID, var save: Boolean = false) : InventoryHolder {

    lateinit var top: Inventory

    override fun getInventory(): Inventory {
        return top
    }

}