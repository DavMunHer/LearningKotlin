package org.example.RA3

/*
Define a data class
User(val id: Int, val name: String, val active: Boolean, val score: Int) .
• Implement pure functions (without side effects) that:
◦ Filter active users.
◦ Increase each user's score according to a function passed as a
parameter.
◦ Sort by score and return the top N in a new list.
• Avoid mutations: never change existing objects; return new objects/
collections.
 */

data class User(val id: Int, val name: String, val active: Boolean, val score: Int)

fun filterActiveUsers(users: List<User>): List<User> {
    return users.filter { it.active }
}

typealias increaseScoreFunction = (score: Int) -> Int

fun increaseScore(users: List<User>, increaserFunction: increaseScoreFunction): List<User> {
    return users.map { user -> user.copy(
        id = user.id,
        name = user.name,
        active = user.active,
        score = increaserFunction(user.score)
    ) }
}

fun getBestScoreUsers(users: List<User>, n: Int): List<User> {
    return users.sortedByDescending { user -> user.score }.take(n)
}

fun main() {
    val user1 = User(id = 1, name = "Yoloo", active = true, score = 7)
    val user2 = User(id = 2, name = "Juan", active = false, score = 4)
    val user3 = User(id = 3, name = "John Doe", active = true, score = 9)
    val userList: List<User> = listOf(user1, user2, user3)

    println("The original list without functions applied: $userList\n")

    val activeUsers = filterActiveUsers(userList)
    println("Active users list: $activeUsers\n")

     fun myOwnIncreaserFunc(currentScore: Int) = currentScore + 1

    val increasedScoreUsersList = increaseScore(userList, ::myOwnIncreaserFunc)
    println("Increased score list: $increasedScoreUsersList\n")

    val bestScoreUsers = getBestScoreUsers(userList, 2)
    println("The best two score users: $bestScoreUsers\n")

    println("Original list: $userList")
}