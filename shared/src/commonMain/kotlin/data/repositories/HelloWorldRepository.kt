package data.repositories

interface HelloWorldRepository {
    fun getHelloWorldMessage(): String
}

class DefaultHelloWorldRepository : HelloWorldRepository {
    override fun getHelloWorldMessage(): String = "Hello from Repository"
}
