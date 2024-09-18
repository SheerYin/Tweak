package io.github.yin.tweak.cache

object InventoryStateCache {

    // 暂时用来判断是否在 inventory 内打开触发音效
    // true 是 沉默
    val silence: MutableMap<String, Boolean> = hashMapOf()

}