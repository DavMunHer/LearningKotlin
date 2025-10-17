package org.example.RA2.inheritance


interface Logger {
    fun log(msg: String)
}


class ConsoleLogger : Logger {
    override fun log(msg: String) {
        println(msg)
    }

}


class FileLogger : Logger {
    override fun log(msg: String) {
        println("Writing text in a file... $msg")
    }
}

class Service(val logger: Logger): Logger by logger


class ServiceWithLogs(val logger: Logger): Logger by logger {
    fun process() {
        this.log("Processing...")
        for (k in 0..10000000000000) {

        }
        this.log("Processed!")
    }
}


fun main() {
    val consoleLogger = ConsoleLogger()
    val fileLogger = FileLogger()

    val service = Service(consoleLogger)
    service.log("Yoloo")

    val service2 = ServiceWithLogs(fileLogger)
    service2.process()
}