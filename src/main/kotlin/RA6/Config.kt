package org.example.RA6

class ConfigBlock {
    private val subConfigs = mutableListOf<String>()
    operator fun invoke(block: ConfigBlock.() -> Unit) {
        this.block() // The class will behave like a function
        // This doesn't mean that the class won't have to be instantiated, but that the instance will work as a function
    }

    fun server(block: ServerBlock.() -> Unit) {
        val serverConfig = ServerBlock().apply(block)
        subConfigs.add("Server: ${serverConfig.host}:${serverConfig.port}")
    }

    fun database(block: DatabaseBlock.() -> Unit) {
        val databaseConfig = DatabaseBlock().apply(block)
        subConfigs.add("Database: ${databaseConfig.url} as ${databaseConfig.user}")
    }
}

class ServerBlock() {
    var host: String = ""
    var port: String = ""
}

class DatabaseBlock() {
    var url: String = ""
    var user: String = ""
}

fun main() {
    val config = ConfigBlock()

    config {
        server {
            host = "kddk"
            port = "3000"
        }
        database {
            url = "http://dummyUrl"
            user = "root"
        }
    }
}