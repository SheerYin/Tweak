package io.github.yin.tweak.support

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

object MessageReplace {

    /**
     * 用给定的参数替换文本中的占位符，没有副作用。
     *
     * @param text 包含占位符的原始文本。占位符的格式为 {index}，其中 index 是参数在参数列表中的位置。
     * @param parameters 用于替换占位符的参数。参数的位置决定了它将替换哪个占位符。
     * @return 替换了占位符的文本。
     */
    fun replace(text: String, vararg parameters: String): String {
        var result = text
        parameters.forEachIndexed { index, parameter ->
            result = result.replace("{$index}", parameter)
        }
        return result
    }

    /**
     * 用给定的参数替换列表中每个文本的占位符，没有副作用。
     *
     * @param list 包含占位符的文本列表。
     * @param parameters 用于替换占位符的参数。
     * @return 替换了占位符的文本列表。
     */
    fun replaceList(list: List<String>, vararg parameters: String): List<String> {
        return list.map { replace(it, *parameters) }
    }

    private val default = MiniMessage.miniMessage()
    fun deserialize(text: String): Component {
        return default.deserialize(text)
    }

    fun legacySection(text: String): TextComponent {
        return LegacyComponentSerializer.legacySection().deserialize(text)
    }

    fun legacyAmpersand(text: String): TextComponent {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text)
    }

    fun legacyBlend(text: String): Component {
        return default.deserialize(blend(text))
    }

    private val colorMap: Map<Char, String> = hashMapOf(
        '0' to "<black>",
        '1' to "<dark_blue>",
        '2' to "<dark_green>",
        '3' to "<dark_aqua>",
        '4' to "<dark_red>",
        '5' to "<dark_purple>",
        '6' to "<gold>",
        '7' to "<gray>",
        '8' to "<dark_gray>",
        '9' to "<blue>",
        'a' to "<green>",
        'b' to "<aqua>",
        'c' to "<red>",
        'd' to "<light_purple>",
        'e' to "<yellow>",
        'f' to "<white>",

        'k' to "<obfuscated>",
        'l' to "<bold>",
        'm' to "<strikethrough>",
        'n' to "<underlined>",
        'o' to "<italic>",
        'r' to "<reset>",

        's' to "<!obfuscated>",
        't' to "<!bold>",
        'u' to "<!strikethrough>",
        'v' to "<!underlined>",
        'w' to "<!italic>"
    )
    private val special: Set<Char> = hashSetOf('&', '§')
    private val keys = colorMap.keys
    private fun blend(text: String): String {
        return buildString {
            var index = 0
            while (index < text.length) {
                if (text[index] in special && index + 1 < text.length && text[index + 1].lowercaseChar() in keys) {
                    val tag = colorMap[text[index + 1].lowercaseChar()] ?: ""
                    append(tag)
                    index += 2
                } else {
                    append(text[index])
                    index++
                }
            }
        }
    }

    fun componentText(component: Component): String {
        return PlainTextComponentSerializer.plainText().serialize(component)
    }

}