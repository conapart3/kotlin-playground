package com.conal.cordarpcrunner


internal object ClientRpcExample {
    /*@JvmStatic
    fun main(args: Array<String>) {
        require(args.size == 3) { "Usage: TemplateClient <node address> <username> <password>" }
        val nodeAddress: NetworkHostAndPort = NetworkHostAndPort.parse(args[0])
        val username = args[1]
        val password = args[2]
        val client = CordaRPCClient(nodeAddress)
        val connection: CordaRPCConnection = client.start(username, password)
        val cordaRPCOperations: CordaRPCOps = connection.getProxy()
        logger.info(cordaRPCOperations.currentNodeTime().toString())
        connection.notifyServerAndClose()
    }*/
}