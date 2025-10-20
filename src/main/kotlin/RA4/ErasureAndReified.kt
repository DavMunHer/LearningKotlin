package org.example.RA4

/*
What to submit
• A generic function that deliberately attempts to check types at runtime
of a collection (example of the problem).
• An inline + reified version that can perform this check.
• A short document (README.md or comments) explaining why the first fails
and why the second works.
 */

// When running the code, T has already been erased and therefore, its type cannot be checked
fun <T> problematicTypeChecker(lista: List<*>): Boolean {
//    return lista.all { it is T }
    return false
}


// When using reified, we can recreate the type even when running the code, which is useful for type checking
inline fun <reified T> isCollectionOfType(collection: Collection<*>): Boolean {
    return collection.all { it is T }
}


fun main() {
    val myStringListExample = listOf("Yoooo, Whad'up my fellas??")

    val isType = problematicTypeChecker<String>(myStringListExample)
    println(isType)

    val stringExample = listOf("Bruh")
    val isType2 = isCollectionOfType<String>(stringExample)
    println(isType2)


}