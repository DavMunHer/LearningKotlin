package org.example.RA2.inheritance

/*
What to create:
1. Abstract class Figure with abstract property area: Double and method
describe(): String (optional with default implementation using area ).
2. Subclasses Rectangle and Circle that extend Figure .
• Rectangle with base and height and primary constructor.
• Circle with radius. Add a secondary constructor that accepts diameter and
delegates to the primary one.
3. Figure should use init for some common validation (e.g. dimensions > 0).
4. Use open/final where appropriate (consider whether you want anyone to extend Rectangle).
 */

abstract class Figure {
    abstract val area: Double

    open fun describe() {
        println("The area of this figure is $area")
    }
}


final class Rectangle(val base: Double, val height: Double) : Figure() {

    init {
        require(base > 0 && height > 0) { "The dimensions must be greater than 0!" }
    }

    override val area: Double
        get() = this.base * this.height


    override fun describe() {
        println("The area of this rectange is $area and its base is $base and its height $height")
    }


}

final class Circle(val radius: Double): Figure() {

    constructor(diameter: Int) : this(diameter / 2.0)

    init {
        require(radius > 0) { "The dimensions must be greater than 0!" }
    }

    override val area: Double
        get() = Math.PI * this.radius * this.radius


    override fun describe() {
        println("The area of this circle is ${this.area} and its radius is ${this.radius}. Diameter = ${this.radius * 2}")
    }
}



fun main() {
    val circle = Circle(5.0)

    circle.describe()
}

