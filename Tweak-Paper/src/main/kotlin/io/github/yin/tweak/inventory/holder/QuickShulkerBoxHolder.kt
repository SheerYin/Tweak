package io.github.yin.tweak.inventory.holder

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class QuickShulkerBoxHolder(val index: Int, var save: Boolean = false) : InventoryHolder {

    lateinit var top: Inventory

    override fun getInventory(): Inventory {
        return top
    }

}