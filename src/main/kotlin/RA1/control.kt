package org.example.RA1

/*
Basic usage of control structures (mainly if and when)
 */


fun getResultFromMark(): String {
    print("Input your mark: ")

    val mark = readln().toDouble()

    var result: String;

    if (mark < 5) {
        result = "Insuficiente"
    } else if (mark in 5.0..<6.0) {
        result = "Suficiente"
    } else if (mark in 6.0..<7.0) {
        result = "Bien"
    } else if (mark in 7.0..<9.0) {
        result = "Notable"
    } else {
        result = "Sobresaliente"
    }
    return result
}

fun getWeekDay(number: Int): String {
     val resultString = when (number) {
        1 -> "Monday"
        2 -> "Twesday"
        3 -> "Wednesday"
        4 -> "Thursday"
        5 -> "Friday"
        6 -> "Saturday"
        7 -> "Sunday"
        else -> "None. Your inputted day does not exist. Must be between 1 to 7"
     }
    return resultString
}



fun main() {
//    val result = getResultFromMark()
//    println(result)

    val result = getWeekDay(2)
    println(result)
}