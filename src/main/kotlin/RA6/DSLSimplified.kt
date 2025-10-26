package org.example.RA6

@DslMarker
annotation class MenuDsl1

// Clases base
data class Menu1(val sections: List<Section1>)
data class Section1(val name: String, val items: List<MenuItem1>)
data class MenuItem1(val label: String)

// Builders
@MenuDsl1
class MenuBuilder1 {
    private val sections = mutableListOf<Section1>()

    fun section(name: String, block: SectionBuilder1.() -> Unit) {
        val builder = SectionBuilder1(name)
        builder.block()
        sections += builder.build()
    }

    fun build(): Menu1 = Menu1(sections)
}

@MenuDsl1
class SectionBuilder1(private val name: String) {
    private val items = mutableListOf<MenuItem1>()

    fun item(label: String) {
        items += MenuItem1(label)
    }

    fun build(): Section1 = Section1(name, items)
}

// Función de entrada DSL
fun menu1(block: MenuBuilder1.() -> Unit): Menu1 {
    val builder = MenuBuilder1()
    builder.block()
    return builder.build()
}


fun main() {
    val menu = menu1 {
        section("Archivo") {
            item("Nuevo")
            item("Abrir")
            item("Salir")
        }

        section("Editar") {
            item("Copiar")
            item("Pegar")
        }
    }

    println(menu)
}