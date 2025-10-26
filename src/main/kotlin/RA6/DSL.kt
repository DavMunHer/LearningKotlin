package org.example.RA6

@DslMarker
annotation class MenuDsl

sealed interface MenuNode {
    val name: String
}

data class Menu(
    override val name: String,
    val sections: List<Section>
) : MenuNode

data class Section(
    override val name: String,
    val entries: List<MenuNode>, // can be Section or MenuItem
    val description: String? = null
) : MenuNode

data class MenuItem(
    override val name: String,
    val disabled: Boolean = false,
    val shortcut: String? = null,
    val description: String? = null
) : MenuNode

// --- Builders ---
@MenuDsl
class MenuBuilder(private val name: String) {
    private val _sections = mutableListOf<Section>()
    fun section(title: String, block: SectionBuilder.() -> Unit) {
        val builder = SectionBuilder(title)
        builder.block()
        _sections += builder.build()
    }

    // optional convenience: top-level items (if you want). Here we keep top-level as sections only.
    fun build(): Menu = Menu(name, _sections.toList())
}

@MenuDsl
class SectionBuilder(private val name: String) {
    private val _entries = mutableListOf<MenuNode>()
    private var _description: String? = null

    // Only expose methods that make sense inside a section: item and nested section
    fun description(text: String) {
        _description = text
    }

    fun item(label: String, block: (MenuItemBuilder.() -> Unit)? = null) {
        val b = MenuItemBuilder(label)
        block?.let { b.it() }
        _entries += b.build()
    }

    // allow subsections (nested)
    fun section(title: String, block: SectionBuilder.() -> Unit) {
        val nested = SectionBuilder(title)
        nested.block()
        _entries += nested.build()
    }

    fun build(): Section = Section(name, _entries.toList(), _description)
}

@MenuDsl
class MenuItemBuilder(private val label: String) {
    private var _disabled: Boolean = false
    private var _shortcut: String? = null
    private var _description: String? = null

    fun disabled(value: Boolean = true) { _disabled = value }
    fun shortcut(shortcut: String) { _shortcut = shortcut }
    fun description(text: String) { _description = text }

    fun build(): MenuItem = MenuItem(
        name = label,
        disabled = _disabled,
        shortcut = _shortcut,
        description = _description
    )
}

// --- DSL entry point ---
fun menu(name: String = "root", block: MenuBuilder.() -> Unit): Menu {
    val b = MenuBuilder(name)
    b.block()
    return b.build()
}

// --- Representaciones: texto y JSON (simple) ---
fun MenuNode.toText(indent: Int = 0): String {
    val pad = "  ".repeat(indent)
    return when (this) {
        is Menu -> buildString {
            appendLine("${pad}Menu: ${this@toText.name}")
            sections.forEach { append(it.toText(indent + 1)) }
        }
        is Section -> buildString {
            appendLine("${pad}Section: $name${if (description != null) " — $description" else ""}")
            entries.forEach { append(it.toText(indent + 1)) }
        }
        is MenuItem -> buildString {
            val extras = buildList<String> {
                if (disabled) add("disabled")
                shortcut?.let { add("shortcut=$it") }
                description?.let { add("desc=${it}") }
            }.joinToString(", ")
            appendLine("${pad}Item: $name${if (extras.isNotEmpty()) " ($extras)" else ""}")
        }
        else -> "$pad<unknown>\n"
    }
}

private fun escapeJson(s: String) =
    s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n")

fun MenuNode.toJson(pretty: Boolean = true, indent: Int = 0): String {
    val pad = if (pretty) "  ".repeat(indent) else ""
    val nl = if (pretty) "\n" else ""
    return when (this) {
        is Menu -> buildString {
            append("${pad}{${nl}")
            if (pretty) append("${pad}  \"type\": \"menu\",$nl")
            append("${pad}  \"name\": \"${escapeJson(name)}\",$nl")
            append("${pad}  \"sections\": [${nl}")
            sections.forEachIndexed { idx, s ->
                append(s.toJson(pretty, indent + 2))
                if (idx != sections.lastIndex) append(",${nl}")
                else append(nl)
            }
            append("${pad}  ]${nl}")
            append("${pad}}")
        }
        is Section -> buildString {
            append("${pad}{${nl}")
            if (pretty) append("${pad}  \"type\": \"section\",$nl")
            append("${pad}  \"name\": \"${escapeJson(name)}\"")
            if (description != null) append(",$nl${pad}  \"description\": \"${escapeJson(description)}\"")
            append(",$nl${pad}  \"entries\": [${nl}")
            entries.forEachIndexed { idx, e ->
                append(e.toJson(pretty, indent + 2))
                if (idx != entries.lastIndex) append(",${nl}")
                else append(nl)
            }
            append("${pad}  ]${nl}")
            append("${pad}}")
        }
        is MenuItem -> buildString {
            append("${pad}{${nl}")
            if (pretty) append("${pad}  \"type\": \"item\",$nl")
            append("${pad}  \"name\": \"${escapeJson(name)}\"")
            if (disabled) append(",$nl${pad}  \"disabled\": true")
            if (shortcut != null) append(",$nl${pad}  \"shortcut\": \"${escapeJson(shortcut)}\"")
            if (description != null) append(",$nl${pad}  \"description\": \"${escapeJson(description)}\"")
            append("${nl}${pad}}")
        }
        else -> "\"unknown\""
    }
}


fun main() {
    val m = menu("Main Menu") {
        section("File") {
            description("File operations")
            item("New") {
                shortcut("Ctrl+N")
            }
            item("Open") {
                shortcut("Ctrl+O")
            }
            section("Recent") {
                item("project1") { description("Último proyecto abierto") }
                item("project2") { disabled() }
            }
            item("Exit") { shortcut("Ctrl+Q") }
        }

        section("Edit") {
            item("Undo") { shortcut("Ctrl+Z") }
            item("Redo") { shortcut("Ctrl+Y") }
            item("Cut") { shortcut("Ctrl+X") }
            item("Copy") { shortcut("Ctrl+C") }
            item("Paste") { shortcut("Ctrl+V") }
        }
    }

    println(m.toText())
    println("---- JSON ----")
    println(m.toJson())

}
