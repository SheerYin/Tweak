package io.github.yin.tweak.support

import org.bukkit.inventory.ItemStack
import java.util.*

object ItemStackConversion {

    fun serializeText(itemStack: ItemStack): String {
        return Base64.getEncoder().encodeToString(itemStack.serializeAsBytes())
    }

    fun deserializeText(text: String): ItemStack {
        return ItemStack.deserializeBytes(Base64.getDecoder().decode(text))
    }

}