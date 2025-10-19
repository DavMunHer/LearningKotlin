package org.example.RA3

import java.util.function.Function
import kotlin.reflect.typeOf

/*
Requirements
• Create function types/aliases for operations with Int or Double.
• Implement a collection (map) that associates operation names (‘sum’, ‘mul’,
etc.) with functions.
• Implement a function applyOperation(name, a, b) that searches for the operation by
name and applies it.
• Implement a function createCompositeOperation(op1, op2) that returns a new
function that applies op1 and then op2 (simple composition).
 */

typealias myFunctsType = (num1: Double, num2: Double) -> Double

fun sum(num1: Double, num2: Double) = num1 + num2

fun subs(num1: Double, num2: Double) = num1 - num2

fun mult(num1: Double, num2: Double) = num1 * num2

fun div(num1: Double, num2: Double) = num1 / num2

fun MutableList<Map<String, myFunctsType>>.applyOp(name: String, num1: Double, num2: Double): Double? {
    val map = this.find { it.get(name) != null }
    val func = map?.get(name)
    return func?.invoke(num1, num2)
}

fun createComposedOp(op1: myFunctsType, op2: myFunctsType): (num1: Double, num2: Double, secondOpNum: Double) -> Double {
    return fun (n1: Double, n2: Double, secondOpNum: Double): Double {
        val res1 = op1(n1, n2)
        return op2(res1, secondOpNum)
    }

}


fun main() {
    val funCollection = mutableListOf<Map<String, myFunctsType>>()
    funCollection.addAll(
        listOf(
            mapOf("sum" to ::sum),
            mapOf("subs" to ::subs),
            mapOf("mult" to ::mult),
            mapOf("div" to ::div)
        )
    )
    val result = funCollection.applyOp("mult", 1.0, 2.0)
    println(result)

    val composedFunc = createComposedOp(::sum, ::mult)

    val finalRes = composedFunc(1.0, 3.0, 2.0)
    println(finalRes)
}
