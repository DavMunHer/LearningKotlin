package org.example.RA2

/*
What to create (files/classes):
1. User data class with at least id: String, name: String, email: String,
age: Int.
2. A companion object in User that provides a factory method to create users
from name and email (e.g. generate id internally).
3. Email property with setter that validates the basic format (e.g. contains @) —
if it does not pass validation, it should not assign the new value.
4. Age property with private set (read-only externally, mutable within the class).
5. Primary constructor + init block that validates initial conditions (e.g. age >= 0 ),
or throws an exception if they are not met.
 */

data class User(val id: String, val name: String, val age: Int) {
    var email: String = ""
        public set(new) {
            if (new.contains('@')) {
                field = new;
            }
        }

    init {
        require(age > 0) { "Age must be greater than 0!" }
    }


    companion object {
        fun create(name: String, email: String, age: Int): User {
            val generatedId = java.util.UUID.randomUUID().toString()
            return User(generatedId, name, age).apply {
                this.email = email
            };
        }
    }
}