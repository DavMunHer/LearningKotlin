package org.example.RA4

/*
What to submit
• A generic Container<T> class
• At least 3 generic utility functions that operate on Container<T>.
• A Main.kt file with usage examples (automatic tests are not required, but examples showing the cases are).
 */

class Container <T> {
    private val containerItems: MutableList<T> = mutableListOf()

    fun add(item: T) {
        containerItems.add(item)
    }

    fun delete(item:T): Boolean {
        return containerItems.remove(item)
    }

    fun isEmpty(): Boolean {
        return containerItems.size == 0
    }

    fun getAll(): List<T> {
        return containerItems.toList()
    }

}

fun <T> filter(cont: Container<T>, pred: (T) -> Boolean): Container<T> {
    val filteredList = cont.getAll().filter { item -> pred(item) }
    val filteredContainer = Container<T>()
    filteredList.forEach {
        filteredContainer.add(it)
    }
    return filteredContainer
}

fun <T, R> mapValues(cont: Container<T>, transform: (T) -> R): Container<R> {
    val transformedList = cont.getAll().map(transform)
    val transformedContainer = Container<R>()
    transformedList.forEach {
        transformedContainer.add(it)
    }
    return transformedContainer
}

fun <T> count(cont: Container<T>, pred: (T) -> Boolean): Int { return filter(cont, pred).getAll().size }


fun main() {
    val myFirstContainer = Container<String>()
    val firstValues = listOf<String>("Wazaa", "Whatever", "ThirdString", "VeryCreativeValue" )
    firstValues.forEach { myFirstContainer.add(it) }

    fun filterWazaa(name: String) = name == "Wazaa"

    println("Original list: ${myFirstContainer.getAll()}" )

    val filteredContainer = filter(myFirstContainer, ::filterWazaa)
    println("String filtered list: ${filteredContainer.getAll()}")

    val wazaaCount = count(myFirstContainer, ::filterWazaa)
    println("Wazaa coincidences: $wazaaCount")

    fun transformString(value: String) = value.length

    val transformedList = mapValues(myFirstContainer, ::transformString)

    println("Transformed list: ${transformedList.getAll()}")

}