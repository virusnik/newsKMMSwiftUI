package sample

expect class Sample() {
    fun checkMe(): Int
}

expect object Platform {
    val name: String
}

fun hello(): String = "HI from ${Platform.name}"

class Proxy {
    fun proxyHello() = hello()
}

fun main() {
    println(hello())
}
