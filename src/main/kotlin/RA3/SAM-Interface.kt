package org.example.RA3

/*
Requirements
• Define a Validator<T> interface function with a method
isValid(value: T): Boolean .
• Implement validators for: basic email, numerical range, and minimum string length.
• Create a filterValid(items, validator) function that returns valid items.
• Show how lambdas are used to instantiate these validators.
 */

fun interface Validator<T> {
    fun isValid(value: T): Boolean
}

val basicEmailValidator = Validator<String> { email -> email.contains('@') && email.contains('.') }

val numericalRangeValidator = Validator<Int> { num -> num in 1..100 }

val stringLengthValidator = Validator<String> { string -> string.length > 5 }

fun <T> filterValid(items: List<T>, validator: Validator<T>): List<T> {
    return items.filter { item -> validator.isValid(item) }
}


fun main() {
    val listOfEmails = listOf("myemail@domain.com", "wrongemail@whatever", "anotherwrongemail.com")
    val filteredEmails = filterValid(listOfEmails, basicEmailValidator)

    println("Filtered Emails : $filteredEmails")

    val listOfNumbers  = listOf(1, 200, 50, 89, 99)
    val filteredNumbers = filterValid(listOfNumbers, numericalRangeValidator)

    println("Filtered numbers: $filteredNumbers")

    val stringList = listOf("aeiou", "largeString", "123")
    val filteredList = filterValid(stringList, stringLengthValidator)
    println("String length filtered list: $filteredList")
}