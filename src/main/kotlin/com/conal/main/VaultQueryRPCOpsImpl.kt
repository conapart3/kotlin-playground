package com.conal.main

class VaultQueryRPCOpsImpl : VaultQueryRPCOps {
//    override fun <T : ContractState, R : Any> queryVaultByNamedQuery(str: String, postProcessor: VaultQueryPostProcessorFunction<T, R>): List<R> {
//
//    }

    override fun queryVaultByNamedQuery(request: RpcVaultNamedQueryRequest): List<Any?> {
        val seq: Sequence<ContractState> = sequenceOf(GridState("1", "1"), GridState("2", "2"), GridState("3", "3"))

        return request.postProcessor.postProcess(seq as Sequence<Nothing>).toList()
    }
}