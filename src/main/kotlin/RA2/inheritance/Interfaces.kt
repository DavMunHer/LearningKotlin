package org.example.RA2.inheritance

interface A {
    fun greeting() {
        println("Hello from A!!")
    }
}

interface B {
    fun greeting() {
        println("Hello from B!!")
    }

    abstract fun compulsoryToImplement()
}

class C : A, B {

    override fun greeting() {
        super<A>.greeting()
        super<B>.greeting()
    }

    override fun compulsoryToImplement() {
        println("Implementing!!")
    }

}

fun main() {
    C().greeting()
}